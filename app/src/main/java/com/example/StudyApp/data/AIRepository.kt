package com.example.StudyApp.data

import android.util.Log
import com.example.StudyApp.BuildConfig
import com.google.ai.client.generativeai.GenerativeModel
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AIRepository {
    private val model by lazy {
        GenerativeModel(
            modelName = "gemini-2.0-flash",
            apiKey = BuildConfig.GOOGLE_API_KEY
        )
    }

    suspend fun generateFlashcardsFromText(text: String, deckId: Long): List<Flashcard> = withContext(Dispatchers.IO) {
        if (text.isBlank()) return@withContext emptyList()

        val systemPrompt = buildPrompt(text)

        val responseText = try {
            val response = model.generateContent(systemPrompt)
            response.text ?: ""
        } catch (e: Exception) {
            throw RuntimeException("Falha ao gerar conteúdo com IA: ${e.message}", e)
        }

        val items = parseFlashcardsJson(responseText)
        return@withContext items.map { item ->
            Flashcard(
                deckId = deckId,
                type = FlashcardType.FRONT_BACK,
                front = item.front.trim(),
                back = item.back.trim()
            )
        }
    }

    private fun buildPrompt(userText: String): String {
        return """
            Leia o texto abaixo e gere flashcards do tipo Frente e Verso em formato JSON.
            Regras:
            - Retorne APENAS um array JSON válido, sem comentários nem texto adicional.
            - Cada item deve ter exatamente os campos: "front" (pergunta) e "back" (resposta).
            - Use linguagem concisa e clara; não inclua markdown.
            - Gere 5 a 15 flashcards, dependendo da densidade de informações.

            Exemplo de saída válida:
            [
              {"front": "O que é fotossíntese?", "back": "Processo em que plantas convertem luz em energia química."},
              {"front": "Qual a fórmula da água?", "back": "H2O."}
            ]

            Texto base:
            ${userText.trim()}
        """.trimIndent()
    }

    private data class FlashcardItem(val front: String, val back: String)

    private fun parseFlashcardsJson(raw: String): List<FlashcardItem> {
        if (raw.isBlank()) return emptyList()

        val jsonSlice = extractJsonArray(raw)
        if (jsonSlice.isBlank()) return emptyList()

        return try {
            val parsed: JsonElement = JsonParser.parseString(jsonSlice)
            if (!parsed.isJsonArray) return emptyList()
            val array: JsonArray = parsed.asJsonArray
            val gson = Gson()
            array.mapNotNull { el ->
                runCatching {
                    val obj = el.asJsonObject
                    val front = obj.get("front")?.asString ?: return@runCatching null
                    val back = obj.get("back")?.asString ?: return@runCatching null
                    FlashcardItem(front, back)
                }.getOrNull()
            }.filter { it.front.isNotBlank() && it.back.isNotBlank() }
        } catch (_: Exception) {
            emptyList()
        }
    }

    private fun extractJsonArray(text: String): String {
        val start = text.indexOf('[')
        val end = text.lastIndexOf(']')
        if (start == -1 || end == -1 || end <= start) return ""
        return text.substring(start, end + 1)
    }

    suspend fun verifyAnswer(question: String, correctAnswer: String, userAnswer: String): Boolean {
        val prompt = """
        Você é um tutor assistente de um aplicativo de flashcards. Sua tarefa é avaliar se a resposta de um estudante está correta, focando no significado principal e não na exatidão literal.

        - A Pergunta foi: "$question"
        - A Resposta Correta esperada é: "$correctAnswer"
        - A Resposta do Estudante foi: "$userAnswer"

        Avalie a "Resposta do Estudante". Ela contém a informação essencial da "Resposta Correta"? Seja flexível com sinônimos, ordem das palavras e pequenas omissões que não alterem o sentido principal. Por exemplo, se a resposta correta for "Lisboa", e o usuário responder "a capital de Portugal", a resposta está correta.

        Responda apenas com a palavra "true" se a resposta do estudante for semanticamente correta, ou "false" caso contrário. Não explique sua decisão.
    """.trimIndent()

        try {
            val response = model.generateContent(prompt)
            val result = response.text?.trim().equals("true", ignoreCase = true)
            Log.d("AI_ASSISTANT", "Verificação Semântica: Resposta da IA foi '${response.text?.trim()}', resultado final: $result")
            return result
        } catch (e: Exception) {
            Log.e("AI_ASSISTANT", "Erro na verificação de resposta.", e)
            return false // Em caso de erro, assume-se que está errado.
        }
    }
}



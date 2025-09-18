package com.example.StudyApp.data

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

    suspend fun verifyAnswer(question: String, correctAnswer: String, userAnswer: String): Boolean = withContext(Dispatchers.IO) {
        val prompt = """
            Você é um verificador semântico de respostas. Compare a resposta do usuário com a resposta correta com base no significado, não na forma literal.
            Instruções:
            - Considere a pergunta para contexto.
            - Ignore erros de digitação, variações gramaticais e sinônimos.
            - Foque no significado semântico e na exatidão factual.
            - Responda APENAS com a palavra "true" ou "false" (tudo minúsculo), sem qualquer texto adicional, explicação, pontuação ou formatação.

            Pergunta:
            ${'$'}{question.trim()}

            Resposta correta (gabarito):
            ${'$'}{correctAnswer.trim()}

            Resposta do usuário:
            ${'$'}{userAnswer.trim()}
        """.trimIndent()

        val responseText = try {
            val response = model.generateContent(prompt)
            response.text?.trim()?.lowercase() ?: ""
        } catch (_: Exception) {
            ""
        }

        return@withContext responseText == "true"
    }
}



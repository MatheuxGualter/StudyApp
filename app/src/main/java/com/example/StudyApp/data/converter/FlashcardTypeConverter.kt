package com.example.StudyApp.data.converter

import androidx.room.TypeConverter
import com.example.StudyApp.data.FlashcardType

class FlashcardTypeConverter {
    @TypeConverter
    fun fromFlashcardType(value: FlashcardType): String {
        return value.name
    }

    @TypeConverter
    fun toFlashcardType(value: String): FlashcardType {
        return FlashcardType.valueOf(value)
    }
} 
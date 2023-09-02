package com.jetpack.compose.unscrumblegame

import com.jetpack.compose.unscrumblegame.data.allWords

fun shuffleWord(word: String): String {
    val tempWord = word.toCharArray()
    tempWord.shuffle()
    while (String(tempWord) == word) {
        tempWord.shuffle()
    }
    return String(tempWord)
}

fun pickRandomWordFrom(wordsSet: Set<String> = allWords): String = wordsSet.random()
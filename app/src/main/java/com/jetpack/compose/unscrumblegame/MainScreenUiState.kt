package com.jetpack.compose.unscrumblegame

import com.jetpack.compose.unscrumblegame.data.allWords

data class MainScreenUiState(
    val gameLayoutUiState: GameLayoutUiState = GameLayoutUiState(),
    val score: Int = 0
)

data class GameLayoutUiState(
    val currentWordNumber: Int = 0,
    val maxWordsNumber: Int = 10,
    val unscrambledWord: String = shuffleWord(pickRandomWordFrom(allWords)),
    val unscrambledWordAnswer: String = "",
    val isWrongAnswer: Boolean = false,
    val isGameOver: Boolean = false
)



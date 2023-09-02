package com.jetpack.compose.unscrumblegame


import android.util.Log
import androidx.lifecycle.ViewModel
import com.jetpack.compose.unscrumblegame.data.allWords
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.Locale

class MainScreenViewModel: ViewModel() {

    private val _state = MutableStateFlow(MainScreenUiState())
    val state: StateFlow<MainScreenUiState> = _state.asStateFlow()
    private val usedWords: MutableSet<String> = mutableSetOf()

    init {
        resetGame()
    }
    fun resetGame() {
        usedWords.clear()
        _state.value = MainScreenUiState(
            GameLayoutUiState(unscrambledWord = pickRandomWordAndShuffle())
        )
        Log.d("boody","reset game is called, current usedWords: $usedWords")
    }
    fun submitAnswer() {
        if(isLastRound()) {
            gameOver()
        } else {
            checkAnswer()
        }
    }
    fun skip() {
        if(isLastRound()) {
            gameOver()
        } else {
            // get the next word and increase words count
            increaseWordNumber()
            getNextUnscrambledWord()
            updateAnswer("")
            }
        }

    // to be used for textField in ui
    fun updateAnswer(answer: String) {
        _state.value = _state.value.copy(
            gameLayoutUiState = _state.value.gameLayoutUiState.copy(
                unscrambledWordAnswer = answer
            )
        )
    }


    // HELPER FUNCTIONS
    // ------------------
    /**
     * do actions depending on whether the answer is correct or not
     * depends on: isCorrectAnswer() method
     *
    * */
    private fun checkAnswer() {
        // if the answer is correct
        // increase the score and get another word and increase the number of current word and clear the answer
        if (isCorrectAnswer(_state.value.gameLayoutUiState.unscrambledWordAnswer, usedWords.last())) {
            _state.update {
                it.copy(score = it.score + 10,
                    gameLayoutUiState = it.gameLayoutUiState.copy(
                        isWrongAnswer = false
                    ))
            }
        } else {
            _state.update {
                it.copy(gameLayoutUiState = it.gameLayoutUiState.copy(
                    isWrongAnswer = true
                ))
            }
        }
        increaseWordNumber()
        getNextUnscrambledWord()
        // clear the answer
        updateAnswer("")
    }
    private fun isCorrectAnswer(answer: String, correctAnswer: String = usedWords.last()): Boolean {
        return (correctAnswer == answer.lowercase(Locale.ROOT).trim())
    }
    private fun increaseWordNumber() {
        _state.update {
            it.copy(gameLayoutUiState = it.gameLayoutUiState.copy(
                currentWordNumber = it.gameLayoutUiState.currentWordNumber + 1
            ))
        }
    }

    /**
     * updates the word and add it to used word thanks to pickRandomWordAndShuffle() method
     * */
    private fun getNextUnscrambledWord() {
        _state.update {
            it.copy(
                gameLayoutUiState = it.gameLayoutUiState.copy(
                    unscrambledWord = pickRandomWordAndShuffle()
                )
            )
        }
    }

    private fun isLastRound(): Boolean {
        return (usedWords.size == 10 + 1)
    }
    private fun gameOver() {
        _state.update {
            it.copy(
                gameLayoutUiState = it.gameLayoutUiState.copy(
                    isGameOver = true
                )
            )
        }
    }
    private fun pickRandomWordAndShuffle(): String {
        val word = pickRandomWordFrom(allWords)
        return if (usedWords.contains(word)) {
            pickRandomWordAndShuffle()
        } else {
            addWordToUsedList(word)
            shuffleWord(word)
        }
    }
    private fun addWordToUsedList(word: String) = usedWords.add(word)
}
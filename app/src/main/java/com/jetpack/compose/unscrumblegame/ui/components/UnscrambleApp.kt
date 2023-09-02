package com.jetpack.compose.unscrumblegame.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jetpack.compose.unscrumblegame.GameLayoutUiState
import com.jetpack.compose.unscrumblegame.MainScreenViewModel
import com.jetpack.compose.unscrumblegame.R

@Composable
fun UnscrambleApp() {
    MainScreen()
}

    /**
     * column contains
     *  game title
     *  game box *layout*
     *  submit button
     *  skip button
     *  score box
     **/
@Composable
fun MainScreen(
    viewModel: MainScreenViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
        val state by viewModel.state.collectAsState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.padding(16.dp)
    ) {
        GameTitle()
        GameLayout(
            gameLayoutUiState = state.gameLayoutUiState,
            modifier = Modifier.padding(vertical = 16.dp),
            onAnswerChanged = { viewModel.updateAnswer(it) }
        )
        SubmitButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                viewModel.submitAnswer()
            }
        )
        SkipButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                viewModel.skip()
            }
        )
        Spacer(modifier = Modifier.height(24.dp))
        ScoreBox(score = state.score)
    }

    if(state.gameLayoutUiState.isGameOver) {
        FinalScoreDialog(
            score = state.score,
            onPlayAgain = {
                viewModel.resetGame()
            }
        )
    }

}
@Composable
fun GameTitle() {
    Text(
        text = stringResource(R.string.unscramble),
        textAlign = TextAlign.Center,
        fontSize = 24.sp
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameLayout(
    modifier: Modifier = Modifier,
    gameLayoutUiState: GameLayoutUiState = GameLayoutUiState(),
    onAnswerChanged: (String) -> Unit = {}
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // current word / max words
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = stringResource(
                    R.string.current_word_number_on_max_words_number,
                    gameLayoutUiState.currentWordNumber,
                    gameLayoutUiState.maxWordsNumber
                ),
                    fontSize = 24.sp,
                    modifier = Modifier
                        .padding(top = 8.dp, end = 8.dp)
                        .background(Color.Cyan)
                        .padding(4.dp)
                        .clip(RoundedCornerShape(4.dp))
                )
            }
            //////////////////
            // current word
            Text(
                text = gameLayoutUiState.unscrambledWord,
                textAlign = TextAlign.Center,
                fontSize = 40.sp
            )
            ////////////////
            // instruction
            Text(
                text = stringResource(R.string.unscramble_the_word_using_all_the_letters),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            ///////////////
            // answer text field

            OutlinedTextField(
                value = gameLayoutUiState.unscrambledWordAnswer,
                onValueChange = {
                    onAnswerChanged(it)
                },
                label = {
                    Text(text = stringResource(R.string.enter_your_word))
                },
                singleLine = true
            )

            // error handling
            if(gameLayoutUiState.isWrongAnswer) {
                Text(
                    text = "Wrong Answer!",
                    color = MaterialTheme.colorScheme.error
                )
            }


        }

    }
}

@Composable
fun SubmitButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Button(
        onClick = onClick,
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.submit),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun SkipButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Text(text = stringResource(R.string.skip),
            textAlign = TextAlign.Center
        )
    }
}



@Composable
fun ScoreBox(
    modifier: Modifier = Modifier,
    score: Int = 0
) {
    Text(
        text = stringResource(R.string.score, score),
        fontSize = 32.sp,
        fontWeight = FontWeight.Bold,
        modifier = modifier
            .background(Color.LightGray)
    )
}

@Preview(showSystemUi = true)
@Composable
fun MainScreenPreview() {
    MainScreen()
}

package com.jetpack.compose.unscrumblegame.ui.components

import android.app.Activity
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.jetpack.compose.unscrumblegame.R

@Composable
fun FinalScoreDialog(
    modifier: Modifier = Modifier,
    onPlayAgain: () -> Unit = { },
    score: Int = 0
) {
    val activity = LocalContext.current as Activity

    AlertDialog(
        onDismissRequest = { /*TODO*/ },
        title = {
            Text(text = stringResource(R.string.congratulations))
        },
        text = {
            Text(text = stringResource(R.string.you_scored, score))
        },
        dismissButton = {
            TextButton(onClick = {
                activity.finish()
            }) {
                Text(text = stringResource(R.string.exit))
            }
        },
        confirmButton = {
            TextButton(onClick = onPlayAgain
            ) {
                Text(text = stringResource(R.string.play_again))
            }
        },
        modifier = modifier
    )

}
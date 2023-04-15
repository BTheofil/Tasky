package hu.tb.tasky.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import hu.tb.tasky.R

@Composable
fun ButtonsSelection(
    OnSaveClicked: () -> Unit,
    OnCancelClicked: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 32.dp),
        horizontalArrangement = Arrangement.SpaceAround,
    ) {
        OutlinedButton(
            onClick = OnCancelClicked
        ) {
            Text(text = stringResource(id = R.string.cancel))
        }
        Button(
            onClick = OnSaveClicked
        ) {
            Text(text = stringResource(id = R.string.save))
        }
    }
}
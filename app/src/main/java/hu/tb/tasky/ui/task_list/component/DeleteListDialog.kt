package hu.tb.tasky.ui.task_list.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import hu.tb.tasky.R

@Composable
fun SurelyDeleteDialog(
    onDismissRequest: () -> Unit,
    onPositiveBtnClick: () -> Unit,
    onNegativeBtnClick: () -> Unit,
    valueText: String,
    onValueChange: (String) -> Unit,
    isDialogError: Boolean,
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Card(
            shape = RoundedCornerShape(8.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextField(
                    value = valueText,
                    onValueChange = onValueChange,
                    singleLine = true,
                    label = { Text(text = stringResource(R.string.new_list_name)) },
                    isError = isDialogError
                )
                Row(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    OutlinedButton(onClick = onNegativeBtnClick) {
                        Text(text = stringResource(id = R.string.cancel))
                    }
                    Button(
                        onClick = onPositiveBtnClick
                    ) {
                        Text(text = stringResource(id = R.string.save))
                    }
                }
            }
        }
    }
}
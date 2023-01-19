package hu.tb.tasky.ui

import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hu.tb.tasky.model.Task

@Composable
fun TaskItemContainer(taskItem: Task) {
    Card(
        elevation = CardDefaults.elevatedCardElevation(),

    ) {

    }
}

@Preview
@Composable
fun TaskItemContainerPreview(){

}
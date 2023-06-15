package hu.tb.tasky.ui.components

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import hu.tb.tasky.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CenterTopBar() {
    return CenterAlignedTopAppBar(
        title = {
            Text(
                stringResource(id = R.string.your_tasks),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
    )
}
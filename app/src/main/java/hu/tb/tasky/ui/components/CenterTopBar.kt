package hu.tb.tasky.ui.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import hu.tb.tasky.R
import androidx.compose.ui.text.style.TextOverflow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
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
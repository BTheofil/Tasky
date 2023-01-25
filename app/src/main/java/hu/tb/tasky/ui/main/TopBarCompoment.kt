package hu.tb.tasky.ui.main

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextOverflow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    return CenterAlignedTopAppBar(
        title = {
            Text(
                "Your Tasks",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
    )
}
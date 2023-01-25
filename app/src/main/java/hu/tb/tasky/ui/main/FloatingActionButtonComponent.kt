package hu.tb.tasky.ui.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import hu.tb.tasky.ui.route.RouteNames.ADD_EDIT_SCREEN

@Composable
fun FloatingActionButtonComponent(navController: NavController) {
    FloatingActionButton(
        onClick = {navController.navigate(ADD_EDIT_SCREEN)},
    ){
        Icon(Icons.Filled.Add, "Add new task")
    }
}
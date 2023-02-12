package hu.tb.tasky.ui.add_edit_task

import androidx.lifecycle.ViewModel
import hu.tb.tasky.model.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AddEditTaskViewModel: ViewModel() {

    private val _form = MutableStateFlow<Task?>(null)
    val form = _form.asStateFlow()
}
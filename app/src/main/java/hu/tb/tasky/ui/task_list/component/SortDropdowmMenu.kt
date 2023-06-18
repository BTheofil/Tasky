package hu.tb.tasky.ui.task_list.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import hu.tb.tasky.R
import hu.tb.tasky.data.repository.DataStoreProtoRepository
import hu.tb.tasky.domain.util.Order
import hu.tb.tasky.domain.util.OrderType

@Composable
fun SortDropdownMenu(
    isVisible: Boolean,
    onDismissRequest: () -> Unit,
    onSortClick: (oder: Order, orderType: OrderType) -> Unit,
    dataStoreProto: DataStoreProtoRepository,
) {
    var currentOderType by remember { mutableStateOf(OrderType.DESCENDING) }
    var currentOder by remember { mutableStateOf(Order.TIME) }
    LaunchedEffect(key1 = Unit) {
        dataStoreProto.appSettings.collect {
            currentOderType = it.sortType
            currentOder = it.sortOrder
        }
    }

    DropdownMenu(
        expanded = isVisible,
        onDismissRequest = onDismissRequest
    ) {
        DropdownMenuItem(
            text = {
                Text(text = stringResource(R.string.sort_title))
            },
            onClick = {
                onClickOrderDecide(currentOderType, Order.TITLE, onSortClick)
            },
            trailingIcon = {
                if (currentOder == Order.TITLE) {
                    Icon(Icons.Outlined.Check, contentDescription = "Selected menu icon")
                }
            }
        )
        DropdownMenuItem(
            text = {
                Text(text = stringResource(R.string.sort_time))
            },
            onClick = {
                onClickOrderDecide(currentOderType, Order.TIME, onSortClick)
            },
            trailingIcon = {
                if (currentOder == Order.TIME) {
                    Icon(Icons.Outlined.Check, contentDescription = "Selected menu icon")
                }
            }
        )
    }
}

private fun onClickOrderDecide(
    currentType: OrderType,
    order: Order,
    onSortClick: (oder: Order, orderType: OrderType) -> Unit
) {
    if (OrderType.ASCENDING == currentType){
        onSortClick(order, OrderType.DESCENDING)
    } else {
        onSortClick(order, OrderType.ASCENDING)
    }
}
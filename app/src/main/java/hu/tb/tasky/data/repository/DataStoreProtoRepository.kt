package hu.tb.tasky.data.repository

import android.content.Context
import androidx.datastore.dataStore
import hu.tb.tasky.domain.util.Order
import hu.tb.tasky.domain.util.OrderType

class DataStoreProtoRepository(
    private val context: Context
) {

    private val Context.dataStore by dataStore("app-settings.json", AppSettingsSerializer)

    val appSettings = context.dataStore.data

    suspend fun saveSort(order: Order, type: OrderType) {
        context.dataStore.updateData {
            it.copy(
                sortBy = order,
                sortTYPE = type
            )
        }
    }

    suspend fun setFirstTimeAppStartToFalse(){
        context.dataStore.updateData {
            it.copy(
                isFirstTimeAppStart = false
            )
        }
    }
}
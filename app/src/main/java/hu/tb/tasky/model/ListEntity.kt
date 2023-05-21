package hu.tb.tasky.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ListEntity(
    @PrimaryKey(autoGenerate = true)
    val listId: Int = 0,
    val name: String,
)

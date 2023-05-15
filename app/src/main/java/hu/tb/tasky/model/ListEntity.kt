package hu.tb.tasky.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ListEntity(
    @PrimaryKey val listId: Int? = null,
    val name: String,
)

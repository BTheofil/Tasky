package hu.tb.tasky.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime

@Entity
data class TaskEntity(
    @PrimaryKey val id: Int? = null,
    val title: String,
    val description: String,
    val expireDate: LocalDate?,
    val expireTime: LocalTime?,
    val initialChecked: Boolean,
)

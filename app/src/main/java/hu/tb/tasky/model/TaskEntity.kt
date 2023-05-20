package hu.tb.tasky.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime

@Parcelize
@Entity
data class TaskEntity(
    @PrimaryKey val taskId: Int? = null,
    val title: String,
    val description: String,
    val expireDate: LocalDate?,
    val expireTime: LocalTime?,
    val isTaskDone: Boolean,
    val listId: Int,
): Parcelable

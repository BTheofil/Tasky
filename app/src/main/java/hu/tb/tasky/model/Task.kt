package hu.tb.tasky.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime

@Parcelize
data class Task(
    val id: Int? = null,
    val title: String,
    val description: String,
    val expireDate: LocalDate?,
    val expireTime: LocalTime?,
    val initialChecked: Boolean,
) : Parcelable

package hu.tb.tasky.data.date_source

import androidx.room.TypeConverter
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime
import org.threeten.bp.format.DateTimeFormatter

class Converters {

    @TypeConverter
    fun stringToLocalDate(value: String): LocalDate? {
        if (value != "null") {
            return LocalDate.parse(
                value,
                DateTimeFormatter.ISO_LOCAL_DATE
            )
        }
        return null
    }

    @TypeConverter
    fun dateToString(date: LocalDate?): String = date.toString()

    @TypeConverter
    fun stringToLocalTime(value: String?): LocalTime? {
        if(value != "null"){
            return LocalTime.parse(
                value,
                DateTimeFormatter.ISO_LOCAL_TIME
            )
        }
        return null
    }

    @TypeConverter
    fun timeToLong(time: LocalTime?): String = time.toString()
}
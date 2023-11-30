package ch.heigvd.lab4.room.converters;

import androidx.room.TypeConverter;
import java.util.Date;
import java.util.Calendar;


/**
 * Converters for Room to convert Calendar to Long and vice-versa
 * @author Hugo DUCOMMUN, Rayane ANNEN
 */
class CalendarConverter {
    @TypeConverter
    fun toCalendar(dateLong: Long) = Calendar.getInstance().apply {
        time = Date(dateLong)
    }
    @TypeConverter
    fun fromCalendar(date: Calendar) = date.time.time
}

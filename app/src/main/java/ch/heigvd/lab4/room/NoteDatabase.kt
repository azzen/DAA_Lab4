package ch.heigvd.lab4.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import ch.heigvd.lab4.models.Note
import ch.heigvd.lab4.models.Schedule
import ch.heigvd.lab4.room.converters.CalendarConverter
import ch.heigvd.lab4.room.dao.NoteScheduleDao

@Database(entities = [Note::class, Schedule::class],
    version = 1,
    exportSchema = true)
@TypeConverters(CalendarConverter::class)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun noteScheduleDao() : NoteScheduleDao
    companion object {
        private var INSTANCE : NoteDatabase? = null
        fun getDatabase(context: Context) : NoteDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE = Room.databaseBuilder(context.applicationContext,
                    NoteDatabase::class.java, "notedatabase.db")
                    .fallbackToDestructiveMigration()
                    .addCallback(NoteDatabaseCallback())
                    .build()
                INSTANCE!!
            }
        }
    }
    private class NoteDatabaseCallback : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { db ->
                val isEmpty = db.noteScheduleDao().getCount().value == 0
                if(isEmpty) {
                    val id = db.noteScheduleDao().insertNote(Note.generateRandomNote())
                    Note.generateRandomSchedule()?.let {
                        it.ownerId = id
                        db.noteScheduleDao().insertSchedule(it)
                    }
                }
            }
        }
    }

}


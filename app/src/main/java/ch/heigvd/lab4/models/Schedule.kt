package ch.heigvd.lab4.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "Schedule")
data class Schedule(
    @PrimaryKey(autoGenerate = true) var scheduleId : Long?,
    var ownerId : Long,
    var date : Calendar
)
package org.d3if4075.Persegiku.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "persegi")
data class PersegiEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    var tgl: Long = System.currentTimeMillis(),
    var sisi: Float,
//    var keliling: Float,
//    var luas: Float
)

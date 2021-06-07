package com.example.notes.objects

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "note_table")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var title: String = "",
    var body: String = "",
    var date: String = "",
    var color: String = ""
    ): Parcelable
package com.example.notes.objects

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class CurrentDate {
    var currentDate: () -> String = {
        val currentDate = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("MMM d yyyy")

        currentDate.format(formatter)
    }
}
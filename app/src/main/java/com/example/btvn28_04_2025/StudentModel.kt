package com.example.btvn28_04_2025

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class StudentModel(
    var name: String,
    var mssv: String,
    var sdt: String,
    var email: String,
//    var settingsSource: Int
) : Parcelable
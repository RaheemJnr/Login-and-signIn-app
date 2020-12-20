package com.example.connect.network

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class User(
    //val Id: Int,
    val Name: String,
    val Email: String,
    val Password: String,
) : Parcelable

data class Result(
    val error: Boolean,
    val message: String,
    val user: User,
)




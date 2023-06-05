package com.example.submissionfundaawal.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserData(val name: String, val idUser: Int, val imgUrl: String, val jumlahFollower: String, val jumlahFollowing: String, val login: String) : Parcelable
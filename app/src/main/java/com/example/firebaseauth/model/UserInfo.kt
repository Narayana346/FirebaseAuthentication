package com.example.firebaseauth.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class UserInfo(val name: String?=null, val email: String?=null)

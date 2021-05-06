package com.tatsuki.core.api.response

import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.PropertyName

// Firestore のデータクラスには初期値を入れないとコンパイルエラーになるので注意
data class PlaceResponse(

    @get: PropertyName("name")
    val name: String = "東京",

    @get: PropertyName("location")
    val location: GeoPoint = GeoPoint(35.68, 139.77)
)

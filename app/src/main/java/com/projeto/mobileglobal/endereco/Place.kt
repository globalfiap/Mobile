package com.projeto.mobileglobal.endereco

import com.google.android.gms.maps.model.LatLng


data class Place(
    val name: String,
    val latLng: LatLng,
    val address: String,
    val type: String
)
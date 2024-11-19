package com.projeto.mobileglobal.menu

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.projeto.mobileglobal.BitmapHelper
import com.projeto.mobileglobal.MarkerInfoAdapter
import com.projeto.mobileglobal.R
import com.projeto.mobileglobal.endereco.Place
import com.projeto.mobileglobal.endereco.PlacesData

class EletropontoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eletroponto)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment
        mapFragment.getMapAsync { googleMap ->
            googleMap.setInfoWindowAdapter(MarkerInfoAdapter(this))
            addMarkers(googleMap)

            googleMap.setOnInfoWindowClickListener { marker ->
                val place = marker.tag as? Place ?: return@setOnInfoWindowClickListener
                openNavigationOptions(place.latLng)
            }

            googleMap.setOnMapLoadedCallback {
                val bounds = LatLngBounds.builder()
                PlacesData.places.forEach { bounds.include(it.latLng) }
                googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(), 100))
            }
        }
    }

    private fun addMarkers(googleMap: GoogleMap) {
        val customIcon = BitmapHelper.vectorToBitmap(
            this,
            R.drawable.baseline_battery_charging_full_24,
            ContextCompat.getColor(this, R.color.global)
        )

        PlacesData.places.forEach { place ->
            val marker = googleMap.addMarker(
                MarkerOptions()
                    .title(place.name)
                    .snippet(place.address)
                    .position(place.latLng)
                    .icon(customIcon)
            )
            marker?.tag = place
        }
    }

    private fun openNavigationOptions(latLng: LatLng) {
        val gmmIntentUri = Uri.parse("google.navigation:q=${latLng.latitude},${latLng.longitude}")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")

        val wazeUri = Uri.parse("https://waze.com/ul?ll=${latLng.latitude},${latLng.longitude}&navigate=yes")
        val wazeIntent = Intent(Intent.ACTION_VIEW, wazeUri)
        wazeIntent.setPackage("com.waze")

        val chooserIntent = Intent.createChooser(mapIntent, "Escolha um aplicativo de navegação")
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(wazeIntent))
        startActivity(chooserIntent)
    }
}

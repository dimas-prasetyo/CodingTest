package com.dimasbintang.codingtest

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class TempMapsActivity : AppCompatActivity(){
    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_temp_maps)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        /*val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)*/
    }
    /*override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val currentLocation = LatLng(-7.690459, 110.413717)
        mMap.addMarker(
            MarkerOptions()
            .position(currentLocation))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation))
        //val location: Location = mMap.myLocation

        mMap.setOnMapClickListener { latLng ->
            val markerOptions = MarkerOptions()
            markerOptions.position(latLng)
            mMap.clear()
            val location = CameraUpdateFactory.newLatLngZoom(
                latLng, 15f
            )
            mMap.addMarker(markerOptions)
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))
            Toast.makeText(this, "Kordinar: " + latLng, Toast.LENGTH_LONG).show()
        }
    }*/
}
package com.dimasbintang.codingtest.detail

import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.dimasbintang.codingtest.HomeActivity
import com.dimasbintang.codingtest.LokasiViewModel
import com.dimasbintang.codingtest.R
import com.dimasbintang.codingtest.data.Lokasi
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_detail.*
import java.util.*


class DetailActivity : AppCompatActivity(), OnMapReadyCallback {
    lateinit var listLokasiViewModel: LokasiViewModel
    lateinit var latitudeLoc: String
    lateinit var longtitudeLoc: String
    var lokasiStatus = true
    var lokasiId = -1
    private lateinit var mMap: GoogleMap
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }

        listLokasiViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(LokasiViewModel::class.java)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val inputType = intent.getStringExtra("inputType")

        val lokasi: Lokasi? = intent.getParcelableExtra("chosenLokasi")
        if (inputType.equals("edit")){
            /*val tempLokasiName = intent.getStringExtra("lokasiName")
            val tempLatitudeLoc = intent.getStringExtra("lokasiLat")
            val tempLongtitudeLoc = intent.getStringExtra("lokasiLong")
            val tempLokasiStatus = intent.getStringExtra("lokasiStatus")

            if (tempLokasiStatus.equals("true")){
                inputStatusIsActive()
            } else {
                inputStatusIsInactive()
            }*/


            if (lokasi?.status == true){
                inputStatusIsActive()
            } else {
                inputStatusIsInactive()
            }

            lokasiId = intent.getStringExtra("lokasiId").toString().toInt()

            input_name.setText(lokasi!!.name)

            latitudeLoc = lokasi!!.latitude
            longtitudeLoc = lokasi!!.longtitude

            getLocationDetail(latitudeLoc.toDouble(), longtitudeLoc.toDouble())
        } else {
            cv_btn_delete.visibility = View.GONE
            txt_title.setText("Add Location")

            fetchLocation()
        }

        btn_save.setOnClickListener {
            println("Masuk 0")
            if (inputType.equals("edit")){
                println("Masuk 1")
                if (input_name.input.equals("")){
                    input_name.setError()
                } else if (input_address.input.equals("")){
                    input_address.setError()
                } else if (input_city.input.equals("")){
                    input_city.setError()
                } else if (input_zip_code.input.equals("")){
                    input_zip_code.setError()
                } else {
                    println("Masuk 2")
                    val updateLokasi = Lokasi(input_name.input, latitudeLoc, longtitudeLoc, input_address.input, input_city.input, input_zip_code.input, lokasiStatus)
                    updateLokasi.id = lokasiId

                    listLokasiViewModel.updateLokasi(updateLokasi)

                    startActivity(Intent(applicationContext, HomeActivity::class.java))
                    this.finish()
                }
            } else{
                if (input_name.input.equals("")){
                    input_name.setError()
                } else if (input_address.input.equals("")){
                    input_address.setError()
                } else if (input_city.input.equals("")){
                    input_city.setError()
                } else if (input_zip_code.input.equals("")){
                    input_zip_code.setError()
                } else {
                    listLokasiViewModel.addLokasi(Lokasi(input_name.input, latitudeLoc, longtitudeLoc,  input_address.input, input_city.input, input_zip_code.input, lokasiStatus))

                    startActivity(Intent(applicationContext, HomeActivity::class.java))
                    this.finish()
                }
            }
        }

        btn_cancel.setOnClickListener {
            onBackPressed()
        }

        btn_close.setOnClickListener {
            onBackPressed()
        }

        btn_delete.setOnClickListener {
            val deleteLokasi = Lokasi("", "", "", "", "", "", false)
            deleteLokasi.id = lokasiId
            listLokasiViewModel.deleteLokasi(deleteLokasi)
            startActivity(Intent(applicationContext, HomeActivity::class.java))
            this.finish()
        }

        btn_refresh_map.setOnClickListener {
            showMaps()
        }

        txt_active.setOnClickListener {
            inputStatusIsActive()
        }

        txt_inactive.setOnClickListener {
            inputStatusIsInactive()
        }
    }

    private fun fetchLocation() {
        val task = fusedLocationProviderClient.lastLocation
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 101)

        }
        task.addOnSuccessListener {
            if (it != null){
                getLocationDetail(it.latitude, it.longitude)
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val currentLocation = LatLng(-7.690459, 110.413717)
        mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation))
    }

    private fun getLocationDetail(latitude: Double, longtitude: Double) {
        latitudeLoc = latitude.toString()
        longtitudeLoc = longtitude.toString()


        val geocoder: Geocoder
        val addresses: List<Address>
        geocoder = Geocoder(this, Locale.getDefault())

        try {
            addresses = geocoder.getFromLocation(latitude, longtitude, 1)
            val tempAddress: String = addresses[0].getAddressLine(0)

            val address = tempAddress.substring(tempAddress.indexOf(" ") + 1)
            address.trim { it <= ' ' }
            input_address.setText(address)

            val city: String = addresses[0].getSubAdminArea()
            input_city.setText(city)
            val postalCode: String = addresses[0].getPostalCode()
            input_zip_code.setText(postalCode)
        } catch (e: Exception){

        }
        Handler().postDelayed({
            showMaps()
        }, 1500)
    }

    private fun showMaps() {
        try {
            val currentLocation = LatLng(latitudeLoc.toDouble(), longtitudeLoc.toDouble())
            mMap.addMarker(MarkerOptions().position(currentLocation))
            mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation))

            mMap.setOnMapClickListener { latLng ->
                val markerOptions = MarkerOptions()
                markerOptions.position(latLng)
                mMap.clear()
                val location = CameraUpdateFactory.newLatLngZoom(
                    latLng, 15f
                )
                mMap.addMarker(markerOptions)
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))

                getLocationDetail(latLng.latitude, latLng.longitude)
            }
        } catch (e: Exception){

        }


    }

    private fun inputStatusIsActive() {
        lokasiStatus = true
        txt_active.setBackgroundColor(getResources().getColor(R.color.blue_app))
        txt_inactive.setBackgroundColor(getResources().getColor(R.color.light_gray))

        txt_active.setTextColor(getResources().getColor(R.color.white))
        txt_inactive.setTextColor(getResources().getColor(R.color.gray))

    }

    private fun inputStatusIsInactive() {
        lokasiStatus = false
        txt_active.setBackgroundColor(getResources().getColor(R.color.light_gray))
        txt_inactive.setBackgroundColor(getResources().getColor(R.color.blue_app))

        txt_active.setTextColor(getResources().getColor(R.color.gray))
        txt_inactive.setTextColor(getResources().getColor(R.color.white))
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(applicationContext, HomeActivity::class.java))
        this.finish()
    }
}
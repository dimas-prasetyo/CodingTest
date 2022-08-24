package com.dimasbintang.codingtest.detail

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
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

class DetailActivity : AppCompatActivity(), OnMapReadyCallback {
    lateinit var listLokasiViewModel: LokasiViewModel
    var latitudeLoc = "-7.690459"
    var longtitudeLoc = "110.413717"
    var lokasiStatus = true
    var lokasiId = -1
    private lateinit var mMap: GoogleMap
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var locationDetailViewModel: LocationDetailViewModel
    lateinit var detailLokasi: Lokasi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }

        listLokasiViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(LokasiViewModel::class.java)
        locationDetailViewModel = LocationDetailViewModel(this)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val inputType = intent.getStringExtra("inputType")

        val lokasi: Lokasi? = intent.getParcelableExtra("chosenLokasi")
        if (inputType.equals("edit")){
            if (lokasi?.status == true){
                inputStatusIsActive()
            } else {
                inputStatusIsInactive()
            }

            lokasiId = intent.getStringExtra("lokasiId").toString().toInt()
            input_name.setText(lokasi!!.name)
            latitudeLoc = lokasi!!.latitude
            longtitudeLoc = lokasi!!.longtitude

            val tempLatLng = LatLng(latitudeLoc.toDouble(), longtitudeLoc.toDouble())
            detailLokasi = locationDetailViewModel.getLocationDetail(tempLatLng)
            setDetailLocation(lokasi)

        } else {
            cv_btn_delete.visibility = View.GONE
            txt_title.setText("Add Location")
            fetchLocation()
        }

        btn_save.setOnClickListener {
            if (inputType.equals("edit")){
                if (input_name.input.equals("")){
                    input_name.setError()
                } else {
                    val updateLokasi = Lokasi(input_name.input, detailLokasi.latitude, detailLokasi.longtitude, input_address.input, input_city.input, input_zip_code.input, lokasiStatus)
                    updateLokasi.id = lokasiId

                    listLokasiViewModel.updateLokasi(updateLokasi)

                    startActivity(Intent(applicationContext, HomeActivity::class.java))
                    this.finish()
                }
            } else{
                if (input_name.input.equals("")){
                    input_name.setError()
                } else {
                    listLokasiViewModel.addLokasi(Lokasi(input_name.input, detailLokasi.latitude, detailLokasi.longtitude,  input_address.input, input_city.input, input_zip_code.input, lokasiStatus))

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
                val tempLatLng = LatLng(it.latitude, it.longitude)
                detailLokasi = locationDetailViewModel.getLocationDetail(tempLatLng)
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val markerOptions = MarkerOptions()
        val currentLocation = LatLng(latitudeLoc.toDouble(), longtitudeLoc.toDouble())
        markerOptions.position(currentLocation)

        mMap.addMarker(markerOptions)
        mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation))

        mMap.setOnMapClickListener { latLng ->
            mMap.clear()
            val location = CameraUpdateFactory.newLatLngZoom(latLng, 15f)
            markerOptions.position(latLng)
            mMap.addMarker(markerOptions)
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))

            detailLokasi = locationDetailViewModel.getLocationDetail(latLng)
            setDetailLocation(detailLokasi)
        }
    }

    private fun setDetailLocation(detailLokasi: Lokasi) {
        input_address.setText(detailLokasi.address)
        input_city.setText(detailLokasi.city)
        input_zip_code.setText(detailLokasi.zip_code)
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

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 101 && grantResults.isNotEmpty()){
            fetchLocation()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(applicationContext, HomeActivity::class.java))
        this.finish()
    }


}
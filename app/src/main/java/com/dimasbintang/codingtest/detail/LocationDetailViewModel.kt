package com.dimasbintang.codingtest.detail

import android.content.Context
import android.location.Address
import android.location.Geocoder
import androidx.lifecycle.ViewModel
import com.dimasbintang.codingtest.data.Lokasi
import com.google.android.gms.maps.model.LatLng
import java.util.*

class LocationDetailViewModel(val context: Context): ViewModel() {

    fun getLocationDetail(latlang: LatLng): Lokasi {
        val geocoder: Geocoder
        var addresses: List<Address> = emptyList()
        geocoder = Geocoder(context, Locale.getDefault())
        var address = ""
        var city = ""
        var postalCode = ""

        try {
            addresses = geocoder.getFromLocation(latlang.latitude, latlang.longitude, 1)
            val tempAddress: String = addresses[0].getAddressLine(0)
            address = tempAddress.substring(tempAddress.indexOf(" ") + 1)
            address.trim { it <= ' ' }
        } catch (e: Exception){

        }

        try { city = addresses[0].getSubAdminArea() } catch (e: Exception){ }

        try { postalCode = addresses[0].getPostalCode() } catch (e: Exception){ }

        val detailLokasi = Lokasi("", latlang.latitude.toString(), latlang.longitude.toString(),address,city, postalCode, true)
        return detailLokasi
    }
}
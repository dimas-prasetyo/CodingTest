package com.dimasbintang.codingtest.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.dimasbintang.codingtest.HomeActivity
import com.dimasbintang.codingtest.LokasiViewModel
import com.dimasbintang.codingtest.R
import com.dimasbintang.codingtest.data.Lokasi
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {
    lateinit var listLokasiViewModel: LokasiViewModel
    lateinit var latitudeLoc: String
    lateinit var longtitudeLoc: String
    var lokasiStatus = true
    var lokasiId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }

        listLokasiViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(LokasiViewModel::class.java)

        val inputType = intent.getStringExtra("inputType")
        if (inputType.equals("edit")){
            val tempLokasiName = intent.getStringExtra("lokasiName")
            val tempLatitudeLoc = intent.getStringExtra("lokasiLat")
            val tempLongtitudeLoc = intent.getStringExtra("lokasiLong")
            val tempLokasiStatus = intent.getStringExtra("lokasiStatus")

            if (tempLokasiStatus.equals("true")){
                inputStatusIsActive()
            } else {
                inputStatusIsInactive()
            }
            lokasiId = intent.getStringExtra("lokasiId").toString().toInt()

            input_name.setText(tempLokasiName)

            latitudeLoc = tempLatitudeLoc.toString()
            longtitudeLoc = tempLongtitudeLoc.toString()
        } else {
            cv_btn_delete.visibility = View.GONE
            txt_title.setText("Add Location")
        }

        btn_save.setOnClickListener {
            println("Tes input tipe: " + inputType)
            if (inputType.equals("edit")){
                if (input_name.input.equals("")){
                    input_name.setError()
                } else if (input_address.input.equals("")){
                    input_address.setError()
                } else if (input_city.input.equals("")){
                    input_city.setError()
                } else if (input_zip_code.input.equals("")){
                    input_zip_code.setError()
                } else {
                    val updateLokasi = Lokasi(input_name.input, "", "", input_address.input, input_city.input, input_zip_code.input, lokasiStatus)
                    updateLokasi.id = lokasiId
                    println("lokasi update: " + updateLokasi)
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
                    listLokasiViewModel.addLokasi(Lokasi(input_name.input, "", "",  input_address.input, input_city.input, input_zip_code.input, lokasiStatus))

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
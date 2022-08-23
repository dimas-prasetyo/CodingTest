package com.dimasbintang.codingtest.detail

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.dimasbintang.codingtest.HomeActivity
import com.dimasbintang.codingtest.LokasiViewModel
import com.dimasbintang.codingtest.R
import com.dimasbintang.codingtest.data.AppDatabase
import com.dimasbintang.codingtest.data.Lokasi
import kotlinx.android.synthetic.main.activity_temp_detail.*

class TempDetailActivity : AppCompatActivity() {
    lateinit var viewModel: LokasiViewModel
    lateinit var latitudeLoc: String
    lateinit var longtitudeLoc: String
    var lokasiStatus = true
    var lokasiId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_temp_detail)

        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(LokasiViewModel::class.java)

        val inputType = intent.getStringExtra("inputType")
        if (inputType.equals("edit")){
            val tempLokasiName = intent.getStringExtra("lokasiName")
            val tempLatitudeLoc = intent.getStringExtra("lokasiLat")
            val tempLongtitudeLoc = intent.getStringExtra("lokasiLong")

            lokasiId = intent.getStringExtra("lokasiId").toString().toInt()

            et_name.setText(tempLokasiName)
            latitudeLoc = tempLatitudeLoc.toString()
            longtitudeLoc = tempLongtitudeLoc.toString()
        } else {
            txt_title.setText("Add Location")
        }

        btn_save.setOnClickListener {
            println("Tes input tipe: " + inputType)
            if (inputType.equals("edit")){
                if (et_name.getText().toString().equals("")){
                    et_name.setError("Please Input Name Location")
                } else {
                    et_name.setError("Please Input Name Location")
                    val updateLokasi = Lokasi(et_name.getText().toString(), "", "", "", "", "", lokasiStatus)
                    updateLokasi.id = lokasiId
                    println("lokasi update: " + updateLokasi)
                    viewModel.updateLokasi(updateLokasi)
                }
            } else{
                if (et_name.getText().toString().equals("")){
                    et_name.setError("Please Input Name Location")
                } else {
                    viewModel.addLokasi(Lokasi(et_name.getText().toString(), "", "", "", "", "", lokasiStatus))
                }

            }
            startActivity(Intent(applicationContext, HomeActivity::class.java))
            this.finish()
        }

        btn_cancel.setOnClickListener {
            onBackPressed()
        }

        btn_delete.setOnClickListener {
            val deleteLokasi = Lokasi("", "", "", "", "", "", false)
            deleteLokasi.id = lokasiId
            viewModel.deleteLokasi(deleteLokasi)
            startActivity(Intent(applicationContext, HomeActivity::class.java))
            this.finish()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(applicationContext, HomeActivity::class.java))
        this.finish()
    }


}
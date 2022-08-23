package com.dimasbintang.codingtest.detail

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.dimasbintang.codingtest.HomeActivity
import com.dimasbintang.codingtest.LokasiViewModel
import com.dimasbintang.codingtest.R
import com.dimasbintang.codingtest.data.Lokasi
import kotlinx.android.synthetic.main.activity_temp_detail.*

class TempDetailActivity : AppCompatActivity() {
    lateinit var listLokasiViewModel: LokasiViewModel
    lateinit var latitudeLoc: String
    lateinit var longtitudeLoc: String
    var lokasiStatus = true
    var lokasiId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_temp_detail)

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

            et_name.setText(tempLokasiName)
            latitudeLoc = tempLatitudeLoc.toString()
            longtitudeLoc = tempLongtitudeLoc.toString()
        } else {
            cv_btn_edit.visibility = View.GONE
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
                    listLokasiViewModel.updateLokasi(updateLokasi)
                }
            } else{
                if (et_name.getText().toString().equals("")){
                    et_name.setError("Please Input Name Location")
                } else {
                    listLokasiViewModel.addLokasi(Lokasi(et_name.getText().toString(), "", "", "", "", "", lokasiStatus))
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
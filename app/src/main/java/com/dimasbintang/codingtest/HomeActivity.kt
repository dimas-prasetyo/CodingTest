package com.dimasbintang.codingtest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dimasbintang.codingtest.data.Lokasi
import com.dimasbintang.codingtest.detail.DetailActivity
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity(), LokasiClickInterface {
    lateinit var viewModel: LokasiViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        rv_lokasi.layoutManager = LinearLayoutManager(this)

        val lokasiListAdapter = LokasiRecyclerViewAdapter(this, this)
        rv_lokasi.adapter = lokasiListAdapter

        /*val tempLokasi = viewModel.allLokasi

        Toast.makeText(this, "Panjang: " + tempLokasi, Toast.LENGTH_LONG).show()*/

        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(LokasiViewModel::class.java)
        viewModel.allLokasi.observe(this, Observer { list ->
            list?.let {
                lokasiListAdapter.updateList(it)
            }
        })

        btn_add_lokasi.setOnClickListener {
            val intent = Intent(this, DetailActivity::class.java)
            startActivity(intent)
            this.finish()
        }

        category_all.setOnClickListener {
            val intent = Intent(this, TempMapsActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onLokasiClick(lokasi: Lokasi) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("inputType", "edit")
        intent.putExtra("lokasiId", lokasi.id.toString())
        intent.putExtra("lokasiName", lokasi.name)
        intent.putExtra("lokasiStatus", lokasi.status.toString())
        intent.putExtra("lokasiLat", lokasi.latitude)
        intent.putExtra("lokasiLong", lokasi.longtitude)
        startActivity(intent)
        this.finish()
    }
}
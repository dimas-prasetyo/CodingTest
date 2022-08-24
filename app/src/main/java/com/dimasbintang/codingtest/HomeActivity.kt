package com.dimasbintang.codingtest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dimasbintang.codingtest.data.Lokasi
import com.dimasbintang.codingtest.detail.DetailActivity
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity(), LokasiClickInterface {
    lateinit var viewModel: LokasiViewModel
    lateinit var lokasiListAdapter: LokasiRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Menonaktifkan mode dark mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        rv_lokasi.layoutManager = LinearLayoutManager(this)

        lokasiListAdapter = LokasiRecyclerViewAdapter(this, this)
        rv_lokasi.adapter = lokasiListAdapter
        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(LokasiViewModel::class.java)

        selectAllLokasi()
        checkCategorySize()

        btn_add_lokasi.setOnClickListener {
            val intent = Intent(this, DetailActivity::class.java)
            startActivity(intent)
            this.finish()
        }

        category_all.setOnClickListener {
            selectAllLokasi()
        }

        category_active.setOnClickListener {
            selectActiveLokasi()
        }

        category_inactive.setOnClickListener {
            selectInactiveLokasi()
        }
    }

    private fun checkCategorySize() {
        viewModel.allLokasi.observe(this, Observer { list -> list?.let { category_all.text = "All Status (" + list.size + ")" } })
        viewModel.activeLokasi.observe(this, Observer { list -> list?.let { category_active.text = "Active (" + list.size + ")" } })
        viewModel.inActiveLokasi.observe(this, Observer { list -> list?.let { category_inactive.text = "InActive (" + list.size + ")" } })
    }

    private fun selectAllLokasi() {
        viewModel.allLokasi.observe(this, Observer { list -> list?.let { lokasiListAdapter.updateList(it) } })
        category_all.setBackgroundColor(getResources().getColor(R.color.green_soft))
        category_active.setBackgroundColor(getResources().getColor(R.color.light_gray))
        category_inactive.setBackgroundColor(getResources().getColor(R.color.light_gray))
    }

    private fun selectActiveLokasi() {
        viewModel.activeLokasi.observe(this, Observer { list -> list?.let { lokasiListAdapter.updateList(it) }})
        category_all.setBackgroundColor(getResources().getColor(R.color.light_gray))
        category_active.setBackgroundColor(getResources().getColor(R.color.green_soft))
        category_inactive.setBackgroundColor(getResources().getColor(R.color.light_gray))
    }

    private fun selectInactiveLokasi() {
        viewModel.inActiveLokasi.observe(this, Observer { list -> list?.let { lokasiListAdapter.updateList(it) } })
        category_all.setBackgroundColor(getResources().getColor(R.color.light_gray))
        category_active.setBackgroundColor(getResources().getColor(R.color.light_gray))
        category_inactive.setBackgroundColor(getResources().getColor(R.color.green_soft))
    }

    override fun onLokasiClick(lokasi: Lokasi) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("inputType", "edit")
        intent.putExtra("lokasiId", lokasi.id.toString())
        intent.putExtra("chosenLokasi", lokasi)
        startActivity(intent)
        this.finish()
    }
}
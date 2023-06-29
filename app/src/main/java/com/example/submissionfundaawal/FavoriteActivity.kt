package com.example.submissionfundaawal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submissionfundaawal.adapter.FavoriteAdapter
import com.example.submissionfundaawal.databinding.ActivityFavoriteBinding
import com.example.submissionfundaawal.detail.DetailUser
import com.example.submissionfundaawal.detail.favorite.FavEntity
import com.example.submissionfundaawal.detail.favorite.FavFactory
import com.example.submissionfundaawal.model.MainViewModel

class FavoriteActivity : AppCompatActivity() {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var _binding: ActivityFavoriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        supportActionBar?.apply { title = "Favorite"
            setDisplayHomeAsUpEnabled(true)
        }

        val layManager = LinearLayoutManager(this)
        _binding.rvUser.layoutManager = layManager

        val itemsDecor = DividerItemDecoration(this, layManager.orientation)
        _binding.rvUser.addItemDecoration(itemsDecor)

        mainViewModel = ViewModelProvider(this,FavFactory.getInstance(this.application))[MainViewModel::class.java]
        mainViewModel.getAllFavs().observe(this) { data ->
            if (data != null) { getData(data) }
        }
    }

    private fun getData(data: List<FavEntity>) {
        val favAdapter = FavoriteAdapter(data as ArrayList<FavEntity>)
        _binding.rvUser.adapter = favAdapter

        favAdapter.setOnItemClickCallback(object : FavoriteAdapter.OnItemClickCallback {
            override fun onItemClicked(data: FavEntity) {
                startActivity(
                    Intent(
                        this@FavoriteActivity, DetailUser::class.java
                    ).putExtra(
                        DetailUser.GET_USER, data.login
                    )
                )
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }; return super.onOptionsItemSelected(item)
    }
}
package com.example.submissionfundaawal.detail

import android.os.Bundle
import android.view.View
import android.os.Handler
import com.bumptech.glide.Glide
import androidx.annotation.StringRes
import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import com.example.submissionfundaawal.R
import androidx.lifecycle.ViewModelProvider
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.example.submissionfundaawal.model.MainViewModel
import com.example.submissionfundaawal.databinding.ActivityDetailUserBinding
import com.example.submissionfundaawal.detail.favorite.FavEntity
import com.example.submissionfundaawal.detail.favorite.FavFactory
import com.example.submissionfundaawal.model.Detailusers

@Suppress("DEPRECATION", "NAME_SHADOWING")
class DetailUser : AppCompatActivity() {

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )

        const val GET_USER = "get user"
    }


    private var _binding: ActivityDetailUserBinding? = null
    private lateinit var searchUser: MainViewModel
    private lateinit var mainViewModel: MainViewModel

    private var favoriteChecker = false
    private var favoriteEntity: FavEntity? = null
    private val binding get() = _binding!!
    private var DetailUsers = Detailusers()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val headBar = supportActionBar
        headBar!!.title = "Detail User"

        mainViewModel = ViewModelProvider(this,FavFactory.getInstance(this.application))[MainViewModel::class.java]
        searchUser = ViewModelProvider(this, FavFactory.getInstance(this.application))[MainViewModel::class.java]

        val data = intent.getStringExtra(GET_USER)
        if (data != null) { getDetailUserApi(data) }

        val value = Bundle()
        value.putString("dataValue", "$data")
        val detailFragmentAdapter = DetailFragmentAdapter(this)
        detailFragmentAdapter.setBundle(value)
        binding.viewPager.adapter = detailFragmentAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f

        binding.layoutView.visibility = View.INVISIBLE
        binding.load.startShimmer()

        mainViewModel.detailUser.observe(this) {
            binding.apply {
                Glide.with(this@DetailUser)
                    .load(it.avatarUrl)
                    .circleCrop()
                    .into(profileImage)
                nama.text = it.name
                username.text = it.login
                followers.text = it.followers+" Followers"
                following.text = it.following+" Following"
            }
            Log.d("Favorite", "$it")
            DetailUsers = it
            favoriteEntity = FavEntity(it.id, it.login)
            mainViewModel.getAllFavs().observe(this) { favorite ->
                if (favorite != null) {
                    for (data in favorite) {
                        if (it.id == data.id) {
                            favoriteChecker = true
                            binding.favorite.setImageResource(R.drawable.ic_baseline_favorite_24)
                        }
                    }
                }
            }
        }
        binding.favorite.setOnClickListener{
            if (!favoriteChecker){
                favoriteChecker = true
                binding.favorite.setImageResource(R.drawable.ic_baseline_favorite_24)
                setDatabase(DetailUsers)
            } else {
                favoriteChecker = false
                binding.favorite.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                mainViewModel.delete(DetailUsers.id)
                Toast.makeText(this, "Remove From Favorite List", Toast.LENGTH_SHORT).show()
            }
        }

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }.apply {
            Handler().postDelayed({
                binding.layoutView.visibility = View.VISIBLE
                binding.load.stopShimmer()
                binding.load.visibility = View.INVISIBLE
            }, 1000)
        }

        headBar.setDisplayHomeAsUpEnabled(true)
        headBar.setDisplayHomeAsUpEnabled(true)

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE

    }

    private fun getDetailUserApi(data: String) {
        mainViewModel.getDetailListUser(data)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setDatabase(value: Detailusers) {
        favoriteEntity?.let {
            it.id = value.id
            it.login = value.login
            it.avatarUrl = value.avatarUrl
            mainViewModel.insert(it)
            Toast.makeText(this, "Favorite Added", Toast.LENGTH_SHORT).show()
        }
        Log.d("Favorite Set", "$favoriteEntity")
    }
}
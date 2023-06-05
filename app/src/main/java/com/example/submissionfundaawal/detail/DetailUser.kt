package com.example.submissionfundaawal.detail

import android.os.Bundle
import android.view.View
import android.os.Handler
import com.bumptech.glide.Glide
import androidx.activity.viewModels
import androidx.annotation.StringRes
import android.annotation.SuppressLint
import com.example.submissionfundaawal.R
import androidx.lifecycle.ViewModelProvider
import androidx.appcompat.app.AppCompatActivity
import com.example.submissionfundaawal.model.UserData
import com.google.android.material.tabs.TabLayoutMediator
import com.example.submissionfundaawal.model.MainViewModel
import com.example.submissionfundaawal.databinding.ActivityDetailUserBinding

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

    private val mainViewModel by viewModels<MainViewModel>()
    private val binding get() = _binding!!

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val headBar = supportActionBar
        headBar!!.title = "Detail User"

        searchUser = ViewModelProvider(this)[MainViewModel::class.java]

        val data = intent.getParcelableExtra<UserData>(GET_USER)!!
        getDetailUserApi(data)

        val value = Bundle()
        value.putString("dataValue", data.login)
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

    private fun getDetailUserApi(data: UserData) {
        mainViewModel.getDetailListUser(data.login)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
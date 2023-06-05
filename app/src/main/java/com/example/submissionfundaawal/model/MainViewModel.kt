package com.example.submissionfundaawal.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submissionfundaawal.api.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    companion object {
        private const val TAG = "MainViewModel"
        private const val DUMMY = "dicoding"
    }

    private val _userDatas = MutableLiveData<List<ItemsItem>>()
    val listUser: LiveData<List<ItemsItem>> = _userDatas

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _searchUserDatas = MutableLiveData<List<ItemsItem>>()
    val serachUser: LiveData<List<ItemsItem>> = _searchUserDatas

    private val _getDetaillUser = MutableLiveData<Detailusers>()
    val detailUser: LiveData<Detailusers> = _getDetaillUser

    private val _getUserFollowers = MutableLiveData<List<Follows>>()
    val userFollowers: LiveData<List<Follows>> = _getUserFollowers

    private val _getUserFollowing = MutableLiveData<List<Follows>>()
    val userFollowing: LiveData<List<Follows>> = _getUserFollowing

    init {
        findAllUser()
    }

    private fun findAllUser() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUser(DUMMY)
        client.enqueue(object : Callback<GithubResponse> {
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _userDatas.value = response.body()?.items
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }

            }

            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun setAllUser(query: String): LiveData<List<ItemsItem>> {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUser(query)
        client.enqueue(object : Callback<GithubResponse> {
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _searchUserDatas.value = response.body()?.items
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })

        return serachUser
    }


    fun getDetailListUser(login: String) {
        _isLoading.value = true

        val client = ApiConfig.getApiService().getDetailUser(login)
        client.enqueue(object : Callback<Detailusers> {
            override fun onResponse(
                call: Call<Detailusers>,
                response: Response<Detailusers>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _getDetaillUser.value = response.body()
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<Detailusers>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun findUserFollowers(value: String): LiveData<List<Follows>> {
        val client = ApiConfig.getApiService().getUserFollowers(value)
        client.enqueue(object : Callback<List<Follows>> {
            override fun onResponse(
                call: Call<List<Follows>>,
                response: Response<List<Follows>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _getUserFollowers.value = response.body()
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<Follows>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onGagal: ${t.message}")
            }
        })
        return userFollowers
    }

    fun findUserFollowing(value: String): LiveData<List<Follows>> {
        val client = ApiConfig.getApiService().getUserFollowing(value)
        client.enqueue(object : Callback<List<Follows>> {
            override fun onResponse(
                call: Call<List<Follows>>,
                response: Response<List<Follows>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _getUserFollowing.value = response.body()
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<Follows>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onGagal: ${t.message}")
            }
        })
        return userFollowing
    }
}
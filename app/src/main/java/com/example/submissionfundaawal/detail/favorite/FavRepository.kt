package com.example.submissionfundaawal.detail.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavRepository(application: Application) {
    private val mFavoriteDao: FavDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
    init {
        val db = FavDBase.getDBase(application)
        mFavoriteDao = db.FavUserDao()
    }

    fun getAllFavorites(): LiveData<List<FavEntity>> = mFavoriteDao.getAllUser()

    fun insert(user: FavEntity) {
        executorService.execute { mFavoriteDao.insertFav(user) }
    }

    fun delete(id: Int) {
        executorService.execute { mFavoriteDao.removeFav(id) }
    }

}
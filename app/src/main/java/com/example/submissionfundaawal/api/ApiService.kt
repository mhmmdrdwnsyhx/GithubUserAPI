package com.example.submissionfundaawal.api

import retrofit2.Call
import retrofit2.http.*
import com.example.submissionfundaawal.model.Follows
import com.example.submissionfundaawal.Utils.token_id
import com.example.submissionfundaawal.model.Detailusers
import com.example.submissionfundaawal.model.GithubResponse

interface ApiService {
	@GET("search/users")
	@Headers("Authorization: token $token_id")
	fun getUser(
		@Query("q") query: String
	): Call<GithubResponse>

	@GET("users/{username}")
	@Headers("Authorization: token $token_id")
	fun getDetailUser(
		@Path("username") username: String
	):Call<Detailusers>

	@GET("users/{username}/followers")
	@Headers("Authorization: token $token_id")
	fun getUserFollowers(
		@Path("username") username: String
	): Call<List<Follows>>

	@GET("users/{username}/following")
	@Headers("Authorization: token $token_id")
	fun getUserFollowing(
		@Path("username") username: String
	): Call<List<Follows>>
}




package com.example.connect.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

// destination url
private const val BASE_URL = "http://192.168.1.100/androidPractice/"

/**
 * Build the Moshi object that Retrofit will be using, making sure to add the Kotlin adapter for
 * full Kotlin compatibility.
 */
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

// retrofit builder to build network call
private val retrofitBuilder = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

/**
service that attach the end point to BASE URL and
return it as a List and save it into a data class[Result]
 */
interface RegisterApiService {
    @FormUrlEncoded
    @POST("register.php")
    fun regUser(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
    ):
            Call<Result>

    //the signIn call
    @FormUrlEncoded
    @POST("login.php")
    fun userLogin(
        @Field("email") email: String?,
        @Field("password") password: String?,
    ): Call<Result?>?
}

//object that expose our network services
object UserApiService {
    val retrofitService: RegisterApiService by lazy {
        retrofitBuilder.create(RegisterApiService::class.java)
    }
}
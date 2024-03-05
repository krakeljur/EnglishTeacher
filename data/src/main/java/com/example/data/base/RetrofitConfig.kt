package com.example.data.base

import com.example.common.Const
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Inject

class RetrofitConfig @Inject constructor() {

    val moshi = Moshi.Builder().build()

    val retrofit = Retrofit.Builder()
        .baseUrl(Const.BASE_URL)
        .client(OkHttpClient())
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
}
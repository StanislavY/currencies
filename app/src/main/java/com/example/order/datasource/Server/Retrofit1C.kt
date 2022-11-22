package com.example.order.datasource.Server


import com.google.gson.GsonBuilder
import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import kotlin.jvm.Throws

class Retrofit1C {
    private val baseUrl="https://currate.ru/api/"
    fun getRetrofit(): API {
        val retrofit1C =Retrofit.Builder()
            .baseUrl((baseUrl))
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))//.addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .client(createOkHttpClient())
            .build()
        return retrofit1C.create(API::class.java)

    }
    private fun createOkHttpClient():OkHttpClient{
        val httpClient=OkHttpClient.Builder()
       /* httpClient.addInterceptor(AuthInterceptor(BuildConfig.API_USERNAME,BuildConfig.API_PASSWORD))*/
     /*   httpClient.addInterceptor(interceptor)*/
      /*  httpClient.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))*/
        return httpClient.build()

    }
    inner class Interceptor1C:Interceptor{
        override fun intercept(chain: Interceptor.Chain): Response {
            return chain.proceed((chain.request()))
        }



    }
    inner class AuthInterceptor(user:String,password:String):Interceptor{
        private val credentials:String=Credentials.basic(user,password)
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            val request=chain.request()
            val authRequest=request.newBuilder()
                .header("Authorization",credentials).build()
            return chain.proceed((authRequest))
        }

    }


}
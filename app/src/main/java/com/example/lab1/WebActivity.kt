package com.example.lab1

//import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.HttpClient
//import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.methods.HttpGet
//import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.impl.client.DefaultHttpClient

import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

class loginModel(var zapros: String, var code: String) {
}

interface RetrofitAPI {
    @POST("testPost")
         fun createPost(@Body dataModal: loginModel): Call<loginModel>
}


class WebActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.web_activity)

    }

    fun openButton(view: View){
        val browser = findViewById<WebView>(R.id.wevView)
        browser.loadUrl("https://github.com/leraVavilina/android/blob/main/README.md")
        print(browser.originalUrl)
    }

    private fun postData(zapros: String, code: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://login1.requestcatcher.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val retrofitAPI = retrofit.create(RetrofitAPI::class.java)
        val modal = loginModel(zapros, code)
        val call: Call<loginModel> = retrofitAPI.createPost(modal)
        call.enqueue(object : Callback<loginModel> {
            override fun onResponse(call: Call<loginModel>, response: Response<loginModel?>) {
                val responseFromAPI: loginModel? = response.body()
                val responseString = ("""
    Response Code : ${response.code()}
    login : ${responseFromAPI?.zapros}
    password : ${responseFromAPI?.code}""")
                Log.d("response",responseString);
            }

            override fun onFailure(call: Call<loginModel?>?, t: Throwable) {
                Log.d("response error",t.message.toString());
            }
        })
    }

    fun changeButton(view: View){
        postData("Какой-то запрос","Какой-то его код");
    }


}
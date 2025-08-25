package com.example.cookieapp

import android.os.Bundle
import android.webkit.CookieManager
import android.webkit.WebView
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private lateinit var webView: WebView
    private lateinit var inputLink: EditText
    private lateinit var btnSetCookies: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webView = findViewById(R.id.webView)
        inputLink = findViewById(R.id.inputLink)
        btnSetCookies = findViewById(R.id.btnSetCookies)

        webView.settings.javaScriptEnabled = true

        btnSetCookies.setOnClickListener {
            val inputText = inputLink.text.toString()

            try {
                val obj = JSONObject(inputText)
                val jwtString = obj.getString("JWT")

                val jwtObj = JSONObject(jwtString)
                val accessToken = jwtObj.getString("access_token")
                val refreshToken = jwtObj.getString("refresh_token")

                val cookieManager = CookieManager.getInstance()
                cookieManager.setAcceptCookie(true)

                val url = "https://example.com"
                cookieManager.setCookie(url, "jwt-access_token=$accessToken; Path=/")
                cookieManager.setCookie(url, "jwt-refresh_token=$refreshToken; Path=/")

                webView.loadUrl(url)

                Toast.makeText(this, "کوکی‌ها ست شدند!", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(this, "خطا در پردازش JSON", Toast.LENGTH_LONG).show()
            }
        }
    }
}

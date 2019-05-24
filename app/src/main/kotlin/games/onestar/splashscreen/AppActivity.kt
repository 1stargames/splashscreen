package games.onestar.splashscreen

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.webkit.WebView

class SplashScreenActivity : AppCompatActivity() {

    companion object {
        // Time elapsed before launching next activity
        private const val NEXT_ACTIVITY_DELAY = 3000 // 3 sec

        private const val NEXT_ACTIVITY_INTENT_PREFIX = "games.onestar.START_"

        // Activity meta data key representing the newt activity
        private const val NEXT_ACTIVITY_KEY = "activity_key"
    }

    private val launchTaskRunner = Runnable {
        try {
            val applicationInfo = packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA)
            val bundle = applicationInfo.metaData
            val activityKey = bundle.getString(NEXT_ACTIVITY_KEY)
            val intent = Intent()
            intent.action = NEXT_ACTIVITY_INTENT_PREFIX + activityKey!!
            sendBroadcast(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val webView = findViewById<View>(R.id.webview) as WebView
        webView.settings.javaScriptEnabled = true
        webView.loadUrl("file:///android_asset/www/index.html")

        setScreen()

        val handler = Handler()
        handler.postDelayed(launchTaskRunner, NEXT_ACTIVITY_DELAY.toLong())
    }

    override fun onResume() {
        super.onResume()

        setScreen()

        val handler = Handler()
        handler.postDelayed(launchTaskRunner, NEXT_ACTIVITY_DELAY.toLong())
    }

    private fun setScreen() {
        var uiOptions = this.window.decorView.systemUiVisibility

        uiOptions = uiOptions xor View.SYSTEM_UI_FLAG_HIDE_NAVIGATION

        // Status bar hiding: Backwards compatible to Jellybean
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            uiOptions = uiOptions xor View.SYSTEM_UI_FLAG_FULLSCREEN
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            uiOptions = uiOptions xor View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        }

        val decorView = window.decorView
        decorView.systemUiVisibility = uiOptions
    }

}

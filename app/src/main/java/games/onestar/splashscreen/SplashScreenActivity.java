package games.onestar.splashscreen;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;

public class SplashScreenActivity extends AppCompatActivity {

    // Time elapsed before launching next activity
    private static final int NEXT_ACTIVITY_DELAY = 3000; // 3 sec

    private static final String NEXT_ACTIVITY_INTENT_PREFIX = "games.onestar.START_";

    // Activity meta data key representing the newt activity
    private static final String NEXT_ACTIVITY_KEY = "activity_key";

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        WebView webView;
        webView = (WebView) findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("file:///android_asset/www/index.html");

        setScreen();

        Handler handler = new Handler();
        handler.postDelayed(launchTaskRunner, NEXT_ACTIVITY_DELAY);
    }

    @Override
    protected void onResume() {
        super.onResume();

        setScreen();
        
        Handler handler = new Handler();
        handler.postDelayed(launchTaskRunner, NEXT_ACTIVITY_DELAY);
    }

    private void setScreen() {
        int uiOptions = this.getWindow().getDecorView().getSystemUiVisibility();

        uiOptions ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;

        // Status bar hiding: Backwards compatible to Jellybean
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            uiOptions ^= View.SYSTEM_UI_FLAG_FULLSCREEN;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            uiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        }

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(uiOptions);
    }

    private Runnable launchTaskRunner = new Runnable() {
        public void run() {
            try {
                ApplicationInfo applicationInfo = getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
                Bundle bundle = applicationInfo.metaData;
                String activityKey = bundle.getString(NEXT_ACTIVITY_KEY);
                Intent intent = new Intent();
                intent.setAction(NEXT_ACTIVITY_INTENT_PREFIX + activityKey);
                sendBroadcast(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

}

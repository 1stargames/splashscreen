package games.onestar.splashscreen;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.Random;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

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

        // Set background color
        int[] androidColors = getResources().getIntArray(R.array.rainbowpastelcolors);
        int randomAndroidColor = androidColors[new Random().nextInt(androidColors.length)];

        ConstraintLayout splashScreenLayout = (ConstraintLayout) findViewById(R.id.splashscreenlayout);
        splashScreenLayout.setBackgroundColor(randomAndroidColor);

        Intent intent = new Intent();
        intent.setAction("games.onestar.splashscreen.SPLASH_SCREEN_FINISHED");
        sendBroadcast(intent);
    }

}

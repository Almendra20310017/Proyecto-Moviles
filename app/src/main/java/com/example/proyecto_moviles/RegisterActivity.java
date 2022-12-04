package com.example.proyecto_moviles;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window = this.getWindow();
        View decor = getWindow().getDecorView();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.rgb(255, 255, 255));
        window.setNavigationBarColor(Color.rgb(255, 255, 255));

        if(Build.VERSION.SDK_INT >= 27) {
            decor.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR |
                            View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        setContentView(R.layout.activity_register);
    }
}
package com.example.proyecto_moviles;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.bluebd));
        this.getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.bluebd));
        //getActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().hide();

        setContentView(R.layout.activity_main);
    }

    public void entrarLogin(View view) {
        Intent login = new Intent(this, LoginActivity.class);
        startActivity(login);
    }

    public void registrarUsuario(View view) {
        Intent register = new Intent(this, RegisterActivity.class);
        startActivity(register);
    }
}
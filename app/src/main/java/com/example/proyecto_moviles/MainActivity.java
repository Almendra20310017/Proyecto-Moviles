package com.example.proyecto_moviles;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private AppCompatButton btnIniciarSesion, btnCrearCuenta;
    private List<Usuario> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.bluebd));
        this.getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.bluebd));

        setContentView(R.layout.activity_main);

        btnIniciarSesion = findViewById(R.id.btnIniciarSesion);
        btnCrearCuenta = findViewById(R.id.btnCrearCuenta);

        TimerTask tarea = new TimerTask() {
            @Override
            public void run() {
                Intent intent;

                if (nuevoUsuario()) {
                    intent = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };

        Timer tiempo = new Timer();
        tiempo.schedule(tarea, 50);
    }

    private boolean nuevoUsuario() {
        SharedPreferences preferencias = getSharedPreferences (
                "user.dat", MODE_PRIVATE
        );

        return preferencias.getBoolean("registrado", false);
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
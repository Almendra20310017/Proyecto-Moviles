package com.example.proyecto_moviles;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.proyecto_moviles.databinding.ActivityHomeBinding;

public class UserContactActivity extends AppCompatActivity {

    private TextView etqNombre, etqCorreo;

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

        setContentView(R.layout.activity_user_contact);

        Toolbar toolbar = findViewById(R.id.toolbarUser);
        setSupportActionBar(toolbar);

        getSupportActionBar().setIcon(R.drawable.ic_simple_blue_logo);

        etqNombre = findViewById(R.id.etqNombreUsuarioU);
        etqCorreo = findViewById(R.id.etqCorreoU);

        SharedPreferences preferencias = getSharedPreferences("user.dat", MODE_PRIVATE);

        etqNombre.setText(preferencias.getString("nombre", "SinNombre"));
        etqCorreo.setText(preferencias.getString("correo", "correo@ejemplo.com"));

    }

    public void cerrar(View view) {
        cerrarSesion();
    }

    public void cerrarSesion() {
        SharedPreferences preferencias = getSharedPreferences("user.dat", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();
        editor.clear();
        editor.apply();

        Intent logout = new Intent(this, MainActivity.class);
        logout.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK );
        startActivity(logout);
        finish();
    }
}
package com.example.proyecto_moviles;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStreamWriter;

public class RegisterActivity extends AppCompatActivity {

    private EditText txtNombreNC, txtCorreoNC, txtContraNC, txtRepetirContraNC, txtDineroNC;

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

        txtNombreNC = findViewById(R.id.txtAliasNC);
        txtCorreoNC = findViewById(R.id.txtNombreNC);
        txtContraNC = findViewById(R.id.txtCorreoNC);
        txtRepetirContraNC = findViewById(R.id.txtRepetirContraNC);
        txtDineroNC = findViewById(R.id.txtDineroNC);
    }

    public void registrarUsuario(View view) {

        String contra1 = txtContraNC.getText().toString();
        String contra2 = txtRepetirContraNC.getText().toString();

        if (!contra1.equals(contra2)) {
            Toast.makeText(this, "La contraseña no coincide.", Toast.LENGTH_SHORT).show();
            return;
        }

        String archivos[] = this.fileList();

        try {
            if (!existeArchivo(archivos, "usuarios_" + txtCorreoNC.getText() + ".txt")) {
                // Crear archivo de usuario
                OutputStreamWriter archivoInterno = new OutputStreamWriter(
                        openFileOutput("usuarios_" + txtCorreoNC.getText().toString() + ".txt",
                                Context.MODE_APPEND));

                float dinero = Float.parseFloat(txtDineroNC.getText().toString());

                if (dinero <= 999) {
                    Toast.makeText(this, "El dinero debe ser mayor a  $1,000.00", Toast.LENGTH_SHORT);
                }

                archivoInterno.write(
                        txtCorreoNC.getText().toString() + "|" +
                                txtNombreNC.getText().toString() + "|" +
                                txtContraNC.getText().toString() + "|" +
                                dinero + "|" + "false\n"
                );

                archivoInterno.flush();
                archivoInterno.close();

                // Crear lista de contactos
                archivoInterno = new OutputStreamWriter(
                        this.openFileOutput("contactos_" + txtCorreoNC.getText() + ".txt",
                                this.MODE_PRIVATE));

                archivoInterno.write("");

                archivoInterno.flush();
                archivoInterno.close();

                // Crear lista de transferencias
                archivoInterno = new OutputStreamWriter(
                        this.openFileOutput("transferencias_" + txtCorreoNC.getText() + ".txt",
                                this.MODE_PRIVATE));

                archivoInterno.write("");

                archivoInterno.flush();
                archivoInterno.close();

                Toast.makeText(this, "Registro guardado.", Toast.LENGTH_SHORT).show();

                Intent login = new Intent(this, LoginActivity.class);
                startActivity(login);
                finish();
            } else {
                Toast.makeText(this, "El usuario ya existe. Ingrese otro correo.",
                        Toast.LENGTH_SHORT).show();
            }

        } catch (IOException e) {
            Toast.makeText(this, "Error al escribr em el archivo.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private boolean existeArchivo(String[] archivos, String s) {
        for(int i = 0; i < archivos.length; i++)
            if (s.equals(archivos[i])) {
                return true;}

        return false;
    }
}
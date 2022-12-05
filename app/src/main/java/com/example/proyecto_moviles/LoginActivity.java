package com.example.proyecto_moviles;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private List<Usuario> users;
    private EditText txtCorreo, txtContra;
    private CheckBox chkGuardar;

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

        setContentView(R.layout.activity_login);

        txtCorreo = findViewById(R.id.txtCorreo);
        txtContra = findViewById(R.id.txtContra);
        chkGuardar = findViewById(R.id.chkGuardar);

    }

    public void entrarInicio(View view) {

        abrirArchivo();

        if (users.size() == 0) {
            Toast.makeText(this, "No hay usuarios en la aplicación. Agregue uno.", Toast.LENGTH_SHORT).show();
        } else {
            for(int i = 0; i < users.size(); i++) {
                Usuario usr = users.get(i);

                String correo = txtCorreo.getText().toString();
                String contra = txtContra.getText().toString();

                if (usr.getCorreo().equals(correo) && usr.getContrasenia().equals(contra)) {
                    Usuario usrA = new Usuario(usr.getCorreo(), usr.getNombre(), usr.getContrasenia(), usr.getBalance(), usr.isRegistrado());

                    usr.setRegistrado(true);

                    if (chkGuardar.isChecked())
                        guardarPreferencias(usr);

                    Intent inicio = new Intent(this, HomeActivity.class);
                    startActivity(inicio);
                    finish();

                    return;
                }
            }

            Toast.makeText(this, "El usuario y/o la contraseña no coinciden.", Toast.LENGTH_SHORT).show();
        }
    }

    public void abrirArchivo() {
        String archivos[] = fileList();

        users = new ArrayList<>();

        if (existeArchivo(archivos, "usuarios_"  + txtCorreo.getText().toString() +  ".txt")) {
            try {
                InputStreamReader archivoInterno = new InputStreamReader(
                        openFileInput("usuarios_"  + txtCorreo.getText().toString() + ".txt"));
                BufferedReader leerArchivo = new BufferedReader(archivoInterno);

                String linea = leerArchivo.readLine();

                String splitLines[];

                while(linea != null) {
                    splitLines = linea.split("\\s+");

                    users.add(new Usuario(splitLines[0], splitLines[1], splitLines[2], Double.parseDouble(splitLines[3]), Boolean.parseBoolean(splitLines[4])));
                    linea = leerArchivo.readLine();
                }

                leerArchivo.close();

            } catch (IOException e) {
                Toast.makeText(this, "Error al leer el archivo.",
                        Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, "No hay usuarios. Agregue uno.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private boolean existeArchivo(String[] archivos, String s) {
        for(int i = 0; i < archivos.length; i++)
            if (s.equals(archivos[i]))
                return true;

        return false;
    }

    private void guardarPreferencias(Usuario u) {
        SharedPreferences preferencias = getSharedPreferences("user.dat", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();

        editor.putString("correo", u.getCorreo());
        editor.putString("nombre", u.getNombre());
        editor.putString("contrasenia", u.getContrasenia());
        editor.putFloat("balance", (float) u.getBalance());
        editor.putBoolean("registrado", u.isRegistrado());
        editor.apply();
    }
}
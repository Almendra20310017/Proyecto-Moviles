package com.example.proyecto_moviles;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

public class AddContactActivity extends AppCompatActivity {

    private Spinner spTipoCuentaNC;
    private EditText txtAliasNC, txtNombreNC, txtNoCuentaNC;
    private List<ListaContactos> mData;
    private int position;

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
        setContentView(R.layout.activity_add_contact);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarGuardar);
        setSupportActionBar(toolbar);


        spTipoCuentaNC = findViewById(R.id.spTipoCunetaNC);
        txtAliasNC = findViewById(R.id.txtAliasNC);
        txtNombreNC = findViewById(R.id.txtNombreNC);
        txtNoCuentaNC = findViewById(R.id.txtNoCuentaNC);

        String [] menu = { "Personal", "Empresarial"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, menu);

        spTipoCuentaNC.setAdapter(adapter);

        mData = (List<ListaContactos>) getIntent().getSerializableExtra("contactos");
        position = (int) getIntent().getSerializableExtra("position");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.guardar_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.itmGuardar:
                if (position == -1)
                    registrarContacto();
                else {
                    modificarContacto();

                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void registrarContacto() {
        SharedPreferences preferencias = getSharedPreferences("user.dat", MODE_PRIVATE);

        String correo = preferencias.getString("correo", "correo@ejemplo.com");

        try {
            OutputStreamWriter archivoInterno = new OutputStreamWriter(
                    openFileOutput("contactos_"  + correo +  ".txt",
                            Context.MODE_APPEND));

            archivoInterno.write(
                    correo    + " " +
                            txtNombreNC.getText().toString()        + " " +
                            txtNoCuentaNC.getText().toString()        + " " +
                            txtAliasNC.getText().toString()        + " " +
                            spTipoCuentaNC.getSelectedItemPosition() + "\n"
            );

            archivoInterno.flush();
            archivoInterno.close();

            Toast.makeText(this, "Contacto guardado.", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            finish();
        } catch (IOException e) {
            Toast.makeText(this, "Error al escribr en el archivo.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void modificarContacto() {
        try {
            SharedPreferences preferencias = getSharedPreferences("user.dat", MODE_PRIVATE);

            String correo = preferencias.getString("correo", "correo@ejemplo.com");

            OutputStreamWriter archivoInterno = new OutputStreamWriter(
                    this.openFileOutput("contactos_"  + correo +  ".txt",
                            Context.MODE_PRIVATE));

            String strGuardar = "";
            mData.get(position).setCorreo(correo);
            mData.get(position).setNombre(txtNombreNC.getText().toString());
            mData.get(position).setCuenta(txtNoCuentaNC.getText().toString());
            mData.get(position).setAlias(txtAliasNC.getText().toString());
            mData.get(position).setTipoCuenta(spTipoCuentaNC.getSelectedItemPosition());

            for(int i = 0; i < mData.size(); i++) {
                strGuardar += mData.get(i).getCorreo()    + " " +
                        mData.get(i).getNombre()        + " " +
                        mData.get(i).getCuenta()       + " " +
                        mData.get(i).getAlias()       + " " +
                        mData.get(i).getTipoCuenta() + "\n";
            }


            archivoInterno.write(strGuardar);

            archivoInterno.flush();
            archivoInterno.close();

            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            finish();

            Toast.makeText(this, "Contacto modificado correctamente.", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, "Error al escribr en el archivo.",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
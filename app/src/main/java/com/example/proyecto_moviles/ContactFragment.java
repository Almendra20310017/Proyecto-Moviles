package com.example.proyecto_moviles;

import static java.sql.DriverManager.println;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ContactFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContactFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ContactFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ContactFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ContactFragment newInstance(String param1, String param2) {
        ContactFragment fragment = new ContactFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    List<ListaContactos> elements;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact, container, false);

        AppCompatButton button = (AppCompatButton) view.findViewById(R.id.btnAgregarC);

        elements = new ArrayList<>();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent contacto = new Intent(getActivity(), AddContactActivity.class);
                contacto.putExtra("position", -1);
                startActivityForResult(contacto, 0);
            }
        });

        //elements = new ArrayList<>();
        //elements.add(new ListaContactos("ruben.v.p@live.com", "Juan Ramírez Castañeda", "1236 7654 6548 89098654", "Juan", 0, false));
        //elements.add(new ListaContactos("ruben.v.p@live.com", "Alberta Palacios Dávila", "7656 5433 4567 89765676", "Alberta", 0, false));
        //elements.add(new ListaContactos("ruben.v.p@live.com", "Raúl Valdez Aguirre", "0113 41167 7787 54076546", "Mobiliaria S. A. de C. V.", 1, false));
        //elements.add(new ListaContactos("ruben.v.p@live.com", "Julian Pérez Ramírez", "7877 6755 8987 44543211", "Julian", 0, false));
        //elements.add(new ListaContactos("ruben.v.p@live.com", "Danel Campos Salazar", "8769 0112 9987 98787656", "Limpieza S. A. de C. V.", 1, false));

        abrirArchivo();

        ListaAdaptadorContactos listAdapter = new ListaAdaptadorContactos(elements, this.getContext());
        RecyclerView recyclerView = view.findViewById(R.id.rContactosT);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(listAdapter);

        return view;
    }

    public void abrirArchivo() {


        String archivos[] = getActivity().fileList();

        SharedPreferences preferencias = getActivity().getSharedPreferences("user.dat", getActivity().MODE_PRIVATE);

        String correo = preferencias.getString("correo", "Sin correo@ejemplo.com");

        // Comprobar si existe el archivo, de no se así se crea el archivo
        if (!existeArchivo(archivos, "contactos.txt")) {
            OutputStreamWriter archivoInternoC = null;
            try {
                archivoInternoC = new OutputStreamWriter(
                        getActivity().openFileOutput("contactos.txt",
                                getActivity().MODE_PRIVATE));

                archivoInternoC.write("");

                archivoInternoC.flush();
                archivoInternoC.close();

                Toast.makeText(getActivity(), "Lista de contactos creada.", Toast.LENGTH_SHORT).show();
            } catch (FileNotFoundException e) {
                Toast.makeText(getActivity(), "Error al leer el archivo.",
                        Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Toast.makeText(this.getContext(), "Hola", Toast.LENGTH_LONG);

        if (existeArchivo(archivos, "contactos.txt")) {
            System.out.println("Hola");
            try {
                InputStreamReader archivoInterno = new InputStreamReader(
                        getActivity().openFileInput("contactos.txt"));
                BufferedReader leerArchivo = new BufferedReader(archivoInterno);

                String linea = leerArchivo.readLine();

                String splitLines[];

                while(linea != null) {
                    splitLines = linea.split("\\s+");
                    //System.out.println(archivos[0] + " " + splitLines[1] + " " + splitLines[2] + " " + splitLines[3] + " " + splitLines[4]);
                    if (correo.equals(splitLines[0]))
                        elements.add(new ListaContactos(splitLines[0], splitLines[1], splitLines[2], splitLines[3], Integer.parseInt(splitLines[4]), false));

                    linea = leerArchivo.readLine();
                }

                leerArchivo.close();

            } catch (IOException e) {
                Toast.makeText(getActivity(), "Error al leer el archivo.",
                        Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        } else {
            Toast.makeText(getActivity(), "No hay usuarios. Agregue uno.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private boolean existeArchivo(String[] archivos, String s) {
        for(int i = 0; i < archivos.length; i++)
            if (s.equals(archivos[i])) {
                return true;}

        return false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        elements.clear();
        abrirArchivo();
    }
}
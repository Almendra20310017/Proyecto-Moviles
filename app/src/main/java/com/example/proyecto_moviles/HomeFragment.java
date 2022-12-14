package com.example.proyecto_moviles;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private List<ListaTransferencia> elements;
    private SharedPreferences preferencias;
    private String correo;
    private TextView etqBalance;

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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        etqBalance = view.findViewById(R.id.etqBalance);

        elements = new ArrayList<>();
        preferencias = getActivity().getSharedPreferences("user.dat", getActivity().MODE_PRIVATE);
        obtenerBalance();
        abrirArchivo();

        ListaAdaptadorTransferencia listAdapter = new ListaAdaptadorTransferencia(elements, this.getContext());
        RecyclerView recyclerView = view.findViewById(R.id.rTransferencias);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(listAdapter);

        return view;
    }

    public void obtenerBalance() {
        String archivos[] = getActivity().fileList();

        correo = preferencias.getString("correo", "correo@ejemplo.com");

        if (existeArchivo(archivos, "usuarios_" + correo + ".txt")) {
            try {
                InputStreamReader archivoInterno = new InputStreamReader(
                        getActivity().openFileInput("usuarios_" + correo + ".txt"));
                BufferedReader leerArchivo = new BufferedReader(archivoInterno);

                String linea = leerArchivo.readLine();

                String splitLines[];

                if(linea != null) {
                    splitLines = linea.split("\\|");
                    etqBalance.setText("$" + splitLines[3]);
                }


                leerArchivo.close();
                archivoInterno.close();

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

    public void abrirArchivo() {
        String archivos[] = getActivity().fileList();

        correo = preferencias.getString("correo", "correo@ejemplo.com");

        if (existeArchivo(archivos, "transferencias_" + correo + ".txt")) {
            try {
                InputStreamReader archivoInterno = new InputStreamReader(
                        getActivity().openFileInput("transferencias_" + correo + ".txt"));
                BufferedReader leerArchivo = new BufferedReader(archivoInterno);

                String linea = leerArchivo.readLine();

                String splitLines[];

                while(linea != null) {
                    splitLines = linea.split("\\|");
                    elements.add(new ListaTransferencia(splitLines[0], splitLines[1], splitLines[2], splitLines[3], splitLines[4]));
                    linea = leerArchivo.readLine();
                }

                leerArchivo.close();
                archivoInterno.close();

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
}
package com.example.proyecto_moviles;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

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
 * Use the {@link TransferFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TransferFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TransferFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TransferFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TransferFragment newInstance(String param1, String param2) {
        TransferFragment fragment = new TransferFragment();
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

    private ListaAdaptadorContactos listAdapter;
    private RecyclerView recyclerView;
    private SharedPreferences preferencias;
    private String correo;
    private EditText txtMontoT;
    private AppCompatButton btnTransferirT;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_transfer, container, false);
        elements = new ArrayList<>();

        abrirArchivo();

        listAdapter = new ListaAdaptadorContactos(elements, this.getContext());
        recyclerView = view.findViewById(R.id.rContactosT);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(listAdapter);

        txtMontoT = view.findViewById(R.id.txtMontoT);
        btnTransferirT = view.findViewById(R.id.btnTransferirT);

        btnTransferirT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                transferirDinero();
            }
        });

        return view;
    }

    public void transferirDinero() {
        try {
            String archivos[] = getActivity().fileList();
            String cuentaATransferir = elements.get(listAdapter.getSingleItem()).getCorreo();

            preferencias = getActivity().getSharedPreferences("user.dat", getActivity().MODE_PRIVATE);
            correo = preferencias.getString("correo", "correo@ejemplo.com");

            if (existeArchivo(archivos, "usuarios_" + cuentaATransferir + ".txt") && existeArchivo(archivos, "transferencias_" + cuentaATransferir + ".txt")) {

                ////    Quitamos dinero de la cuenta principal y revisamos que tiene el dinero suficiente para transferir
                float monto = Float.parseFloat(txtMontoT.getText().toString());

                InputStreamReader archivoCuentaPrincipalU = null;
                archivoCuentaPrincipalU = new InputStreamReader(
                        getActivity().openFileInput("usuarios_"  + correo + ".txt"));

                BufferedReader leerArchivoU = new BufferedReader(archivoCuentaPrincipalU);

                String lineaU = leerArchivoU.readLine();
                String splitUsuario[] = lineaU.split("\\|");

                if (monto > Float.parseFloat(splitUsuario[3])) {
                    Toast.makeText(getActivity(), "No puede transferir más del dinero que tiene.", Toast.LENGTH_SHORT);
                    archivoCuentaPrincipalU.close();
                    return;
                }

                float quitarDinero = Float.parseFloat(splitUsuario[3]) - monto;

                System.out.println(quitarDinero);

                archivoCuentaPrincipalU.close();
                leerArchivoU.close();

                //  Descontar el dinero

                OutputStreamWriter archivoCuentaPrincipal = null;
                archivoCuentaPrincipal = new OutputStreamWriter(
                        getActivity().openFileOutput("usuarios_" + correo + ".txt",
                                getActivity().MODE_PRIVATE));

                archivoCuentaPrincipal.write(
                        splitUsuario[0] + "|" + splitUsuario[1] + "|" + splitUsuario[2] + "|" + quitarDinero + "|" + splitUsuario[4]
                );

                archivoCuentaPrincipal.flush();
                archivoCuentaPrincipal.close();

                ////  Agrega dinero a la cuenta que se le quiere transferir ////
                InputStreamReader archivoInterno = null;

                archivoInterno = new InputStreamReader(
                        getActivity().openFileInput("usuarios_"  + cuentaATransferir + ".txt"));

                BufferedReader leerArchivo = new BufferedReader(archivoInterno);

                String linea = leerArchivo.readLine();
                String splitUsr[] = linea.split("\\|");

                System.out.print(Arrays.toString(splitUsr));

                float balanceCuentaATransferir = Float.parseFloat(splitUsr[3]) + monto;

                String nlinea = splitUsr[0] + "|" + splitUsr[1] + "|" + splitUsr[2] + "|" + balanceCuentaATransferir + "|" + splitUsr[4];

                leerArchivo.close();
                archivoInterno.close();

                //  Guardar dinero en la cuenta del otro usuario
                OutputStreamWriter archivoOtraCuenta = null;
                archivoOtraCuenta = new OutputStreamWriter(
                        getActivity().openFileOutput("usuarios_" + cuentaATransferir + ".txt",
                                getActivity().MODE_PRIVATE));


                archivoOtraCuenta.write(nlinea);

                archivoOtraCuenta.flush();
                archivoOtraCuenta.close();

                //// Crear mensaje de recibo en la otra cuenta
                archivoOtraCuenta = null;
                archivoOtraCuenta = new OutputStreamWriter(
                        getActivity().openFileOutput("transferencias_" + cuentaATransferir + ".txt",
                                getActivity().MODE_APPEND));

                String blue = "#F8F7FE";
                String green = "#F8FDF7";
                String trans = "trasnferir";
                String get = "recibir";

                String transferText = "Transferencia";
                String getText = "Recibiste dinero";

                String subTrans = "Transferiste al contacto ";

                archivoOtraCuenta.write(
                        green + "|" + getText + "|" + getText + splitUsr[1] + "|" + get + "|" + monto + "\n"
                );

                archivoOtraCuenta.flush();
                archivoOtraCuenta.close();

                ////    Crear mensaje de transferencia en el archivo de la cuenta principal
                archivoCuentaPrincipal = null;
                archivoCuentaPrincipal = new OutputStreamWriter(
                        getActivity().openFileOutput("transferencias_" + correo + ".txt",
                                getActivity().MODE_APPEND));

                archivoCuentaPrincipal.write(
                        blue + "|" + transferText + "|" + subTrans + splitUsr[1] + "|" + trans + "|" + monto + "\n"
                );

                archivoCuentaPrincipal.flush();
                archivoCuentaPrincipal.close();

            } else {
                Toast.makeText(getActivity(), "La cuenta a transferir no existe.", Toast.LENGTH_SHORT);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void abrirArchivo() {


        String archivos[] = getActivity().fileList();

        preferencias = getActivity().getSharedPreferences("user.dat", getActivity().MODE_PRIVATE);
        correo = preferencias.getString("correo", "correo@ejemplo.com");

        if (existeArchivo(archivos, "contactos_" + correo + ".txt")) {
            try {
                InputStreamReader archivoInterno = new InputStreamReader(
                        getActivity().openFileInput("contactos_" + correo + ".txt"));
                BufferedReader leerArchivo = new BufferedReader(archivoInterno);

                String linea = leerArchivo.readLine();

                String splitLines[];

                while(linea != null) {
                    splitLines = linea.split("\\|");

                    elements.add(new ListaContactos(splitLines[0], splitLines[1], splitLines[2], Integer.parseInt(splitLines[3]), false));

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
}
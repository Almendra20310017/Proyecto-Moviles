package com.example.proyecto_moviles;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_transfer, container, false);

        elements = new ArrayList<>();
        elements.add(new ListaContactos("Juan Ramírez Castañeda", "1236 7654 6548 89098654", "Juan", 0, false));
        elements.add(new ListaContactos("Alberta Palacios Dávila", "7656 5433 4567 89765676", "Alberta", 0, false));
        elements.add(new ListaContactos("Raúl Valdez Aguirre", "0113 41167 7787 54076546", "Mobiliaria S. A. de C. V.", 1, false));
        elements.add(new ListaContactos("Julian Pérez Ramírez", "7877 6755 8987 44543211", "Julian", 0, false));
        elements.add(new ListaContactos("Danel Campos Salazar", "8769 0112 9987 98787656", "Limpieza S. A. de C. V.", 1, false));

        ListaAdaptadorContactos listAdapter = new ListaAdaptadorContactos(elements, this.getContext());
        RecyclerView recyclerView = view.findViewById(R.id.rContactosT);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(listAdapter);

        return view;
    }
}
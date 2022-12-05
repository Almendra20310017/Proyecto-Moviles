package com.example.proyecto_moviles;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.util.List;

public class ListaAdaptadorContactos extends RecyclerView.Adapter<ListaAdaptadorContactos.ViewHolder> implements PopupMenu.OnMenuItemClickListener {
    private List<ListaContactos> mData;
    private LayoutInflater mInflater;
    private Context context;
    private int singleItem = -1;
    private ImageButton imageButton;
    private int popMenuPosition = -1;

    public ListaAdaptadorContactos(List<ListaContactos> itmList, Context context) {
        this.mInflater =LayoutInflater.from(context);
        this.context = context;
        this.mData = itmList;
    }

    @Override
    public int getItemCount() { return mData.size(); }

    @Override
    public ListaAdaptadorContactos.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.lista_contactos, parent, false);
        return new ListaAdaptadorContactos.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final ListaAdaptadorContactos.ViewHolder holder, final int position) {
        ListaContactos lstContactos = mData.get(position);

        CardView rowItem;

        rowItem = holder.itemView.findViewById(R.id.cardlist);

        if (singleItem == position) {
            rowItem.setBackgroundColor(context.getResources().getColor(R.color.blueLight));
        } else {
            rowItem.setBackgroundColor(context.getResources().getColor(R.color.white));
        }

        holder.bindData(mData.get(position));
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.itmPopupEditar:
                if (popMenuPosition != -1 && popMenuPosition <= mData.size()) {
                    // Accion de editar
                    Intent modificar = new Intent(context, AddContactActivity.class);
                    modificar.putExtra("contactos", (Serializable) mData);
                    modificar.putExtra("position", popMenuPosition);
                    context.startActivity(modificar);
                } else {
                    Toast.makeText(context, "No se pudo editar.", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.itmPopupEliminar:
                if (popMenuPosition != -1 && popMenuPosition <= mData.size()) {
                    mData.remove(popMenuPosition);
                    guardarContactos();
                    notifyItemRemoved(popMenuPosition);
                    notifyItemRangeChanged(popMenuPosition, mData.size());
                    popMenuPosition = -1;
                } else {
                    Toast.makeText(context, "No se pudo eliminar.", Toast.LENGTH_SHORT).show();
                }
                break;
        }

        return false;
    }

    public void updateRecycler(int position) {
        notifyItemChanged(position);
        notifyItemRangeChanged(position, mData.size());
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView iconContactType;

        CardView cardColor;

        TextView nombre, alias, cuenta;

        LinearLayout rowItem;

        ViewHolder(View itemView) {
            super(itemView);

            iconContactType = itemView.findViewById(R.id.icnContactType);
            alias = itemView.findViewById(R.id.txtAliasC);
            nombre = itemView.findViewById(R.id.txtNombreC);
            cuenta = itemView.findViewById(R.id.txtNoCuentaC);
            cardColor = itemView.findViewById(R.id.cardImageC);

            this.rowItem = itemView.findViewById(R.id.layoutContacts);

            rowItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setSingleSelection(getAdapterPosition());
                }
            });

            imageButton = itemView.findViewById(R.id.imageButton);

            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showPopupMenu(view, getAdapterPosition());
                }
            });
        }

        void bindData(final ListaContactos item) {
            String uri = "";
            String color = "";

            switch (item.getTipoCuenta()) {
                case 0:
                    uri = "@drawable/ic_lista_trasnferir";
                    color = "#EFF6FF";
                    break;
                case 1:
                    uri = "@drawable/ic_lista_trasnferir";
                    color = "#EFF6FF";
                    break;
                case 2:
                    uri = "@drawable/ic_lista_trasnferir";
                    color = "#EFF6FF";
                    break;
            }

            int imageResource = context.getResources().getIdentifier(uri, null, context.getPackageName());
            Drawable logo = context.getResources().getDrawable(imageResource);
            iconContactType.setImageDrawable(logo);

            cardColor.getBackground().setColorFilter(Color.parseColor(color), PorterDuff.Mode.SRC_ATOP);

            alias.setText(item.getAlias());
            nombre.setText(item.getNombre());
            cuenta.setText(item.getCuenta());
        }
    }

    public void setSingleSelection(int adapterPosition) {
        if (adapterPosition == RecyclerView.NO_POSITION) return;

        notifyItemChanged(singleItem);
        singleItem = adapterPosition;
        notifyItemChanged(singleItem);
    }

    private void showPopupMenu(View view, int position) {
        PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
        popupMenu.inflate(R.menu.popup_menu);
        popMenuPosition = position;
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.show();
    }

    public void guardarContactos() {
        try {
            SharedPreferences preferencias = context.getSharedPreferences("user.dat", Context.MODE_PRIVATE);

            String correo = preferencias.getString("correo", "correo@ejemplo.com");

            OutputStreamWriter archivoInterno = new OutputStreamWriter(
                    context.openFileOutput("contactos_" + correo + ".txt",
                            Context.MODE_PRIVATE));

            String strGuardar = "";

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

            Toast.makeText(context, "Contacto eliminado correctamente.", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(context, "Error al escribr en el archivo.",
                    Toast.LENGTH_SHORT).show();
        }
    }
}

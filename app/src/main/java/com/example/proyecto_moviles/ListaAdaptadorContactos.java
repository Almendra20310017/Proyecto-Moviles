package com.example.proyecto_moviles;

import android.content.Context;
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

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListaAdaptadorContactos extends RecyclerView.Adapter<ListaAdaptadorContactos.ViewHolder> implements PopupMenu.OnMenuItemClickListener {
    private List<ListaContactos> mData;
    private LayoutInflater mInflater;
    private Context context;
    private int singleItem = -1;
    private ImageButton imageButton;

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
                // Accion de editar
                break;

            case R.id.itmPopupEliminar:
                // Accion de eliminar
                break;
        }

        return false;
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
                    showPopupMenu(view);
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

    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
        popupMenu.inflate(R.menu.popup_menu);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.show();
    }
}

package com.example.proyecto_moviles;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListaAdaptadorTransferencia extends RecyclerView.Adapter<ListaAdaptadorTransferencia.ViewHolder> {
    private List<ListaTransferencia> mData;
    private LayoutInflater mInflater;
    private Context context;

    public ListaAdaptadorTransferencia(List<ListaTransferencia> itmList, Context context) {
        this.mInflater =LayoutInflater.from(context);
        this.context = context;
        this.mData = itmList;
    }

    @Override
    public int getItemCount() { return mData.size(); }

    @Override
    public ListaAdaptadorTransferencia.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.lista_trasnferencias, null);
        return new ListaAdaptadorTransferencia.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final ListaAdaptadorTransferencia.ViewHolder holder, final int position) {

        ListaTransferencia lstTransferencias = mData.get(position);
        holder.bindData(mData.get(position));
    }

    public void setItems(List<ListaTransferencia> items) { mData = items; }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView iconImage;

        CardView cardColor;

        TextView titulo, subtitulo, valor;

        ViewHolder(View itemView) {
            super(itemView);

            iconImage = itemView.findViewById(R.id.icnImage);
            titulo = itemView.findViewById(R.id.txtTituloT);
            subtitulo = itemView.findViewById(R.id.txtSubtituloT);
            valor = itemView.findViewById(R.id.txtValorT);
            cardColor = itemView.findViewById(R.id.cardImageT);

        }

        void bindData(final ListaTransferencia item) {
            String uri = "@drawable/ic_lista_" + item.getImg();
            int imageResource = context.getResources().getIdentifier(uri, null, context.getPackageName());
            Drawable logo = context.getResources().getDrawable(imageResource);
            iconImage.setImageDrawable(logo);

            cardColor.getBackground().setColorFilter(Color.parseColor(item.getColor()), PorterDuff.Mode.SRC_ATOP);

            titulo.setText(item.getTitulo());
            subtitulo.setText(item.getSubtitulo());
            valor.setText(item.getValor());
        }
    }

}

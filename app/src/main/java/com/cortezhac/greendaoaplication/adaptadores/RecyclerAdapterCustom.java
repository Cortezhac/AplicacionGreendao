package com.cortezhac.greendaoaplication.adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cortezhac.greendaoaplication.R;
import com.cortezhac.greendaoaplication.database.Articulos;

import java.util.ArrayList;

public class RecyclerAdapterCustom extends RecyclerView.Adapter<RecyclerAdapterCustom.ArticulosViewHolder> {
    ArrayList<Articulos> listaArticulos;

    //Asigna la lista al objeto
    public RecyclerAdapterCustom(ArrayList<Articulos> listaArticulos){
        this.listaArticulos = listaArticulos;
    }

    @NonNull
    @Override
    public ArticulosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Aqui se asigna la ubicacion del layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_layout_custom,null,false);
        return new ArticulosViewHolder(view); // retoner la vista ya asiganda
    }

    @Override
    public void onBindViewHolder(@NonNull ArticulosViewHolder holder, int position) {
        // Asigna lo que traiga el ArrayList al elemento personalizado
        holder.descripcion.setText(this.listaArticulos.get(position).getDescripcion().toString());
        holder.precio.setText(String.valueOf(this.listaArticulos.get(position).getPrecio()));
    }

    @Override
    public int getItemCount() {
        return this.listaArticulos.size();
    }


    public class ArticulosViewHolder extends RecyclerView.ViewHolder {
        TextView descripcion, precio;
        //Constructor que asigna el id al elemento
        public ArticulosViewHolder(@NonNull View itemView) {
            super(itemView);
            //Aqui se agregan los ids del layout personalizado
            this.descripcion = (TextView) itemView.findViewById(R.id.recyclerDescripcion);
            this.precio = (TextView) itemView.findViewById(R.id.recyclerPrecio);
        }
    }
}

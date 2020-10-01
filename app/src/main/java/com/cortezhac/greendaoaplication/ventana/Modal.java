package com.cortezhac.greendaoaplication.ventana;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.cortezhac.greendaoaplication.MainActivity;
import com.cortezhac.greendaoaplication.R;
import com.cortezhac.greendaoaplication.dao.ArticulosDAO;
import com.cortezhac.greendaoaplication.database.Articulos;

public class Modal {
    private Dialog myDialogo;
    private AlertDialog.Builder ventana;
    private ImageButton btnSalir;
    private EditText campoCodigo;
    private Button btnbuscarCodigo;

    public void search(final Context app){
        // Inicializar ventana
        myDialogo = new Dialog(app);
        myDialogo.setContentView(R.layout.buscar_codigo_emergente); // vista
        btnSalir = myDialogo.findViewById(R.id.FlotanteSalir);
        btnbuscarCodigo = myDialogo.findViewById(R.id.FlotantebtnCOnsultCodigo);
        campoCodigo = myDialogo.findViewById(R.id.flotanteCodigo);
        // listener de botones
        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialogo.dismiss();
            }
        });
        btnbuscarCodigo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(comprobarCampo(campoCodigo)){
                    Long id = Long.parseLong(campoCodigo.getText().toString());
                    ArticulosDAO gestor = new ArticulosDAO(myDialogo.getContext());// activar green dao
                    Articulos producto = gestor.getArticulo(id); // comsulta por id
                    if(producto != null){
                        Intent paquete = new Intent(app, MainActivity.class);
                        paquete.putExtra("senal", String.valueOf(1));
                        paquete.putExtra("codigo", String.valueOf(producto.getCodigo()));
                        paquete.putExtra("descripcion", producto.getDescripcion());
                        paquete.putExtra("precio", String.valueOf(producto.getPrecio()));
                        app.startActivity(paquete);
                        myDialogo.dismiss();
                    }else{
                        Toast.makeText(myDialogo.getContext(), "Registro no encontrado", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    campoCodigo.setError("Ingrese un valor");
                }
            }
        });// fin onclick listener
        myDialogo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));// color de fondo
        myDialogo.show();
    }

    public boolean comprobarCampo(EditText campo){
        boolean estado = true;
        if(campo.getText().toString().length() <= 0){
            campo.setError("Campo obligatorio");
            estado = false;
        }

        return estado;
    }
}

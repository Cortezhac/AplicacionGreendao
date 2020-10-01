package com.cortezhac.greendaoaplication.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.cortezhac.greendaoaplication.database.Articulos;

import java.util.ArrayList;

import greendao.ArticulosDao;
import greendao.DaoMaster;
import greendao.DaoSession;

public class ArticulosDAO {
    private DaoMaster master;
    private DaoMaster.DevOpenHelper helper;
    private DaoSession session;
    private SQLiteDatabase db;

    // Construtor que inicia la session que permite usar todaslas funciones
    public ArticulosDAO(Context contextoApp){
        this.helper = new DaoMaster.DevOpenHelper(contextoApp, "Administracion",null);
        this.db = helper.getWritableDatabase();
        this.master = new DaoMaster(db);
        this.session = master.newSession();
    }

    public ArrayList<Articulos> listarArticulos(){
        // Permite usar las funciones del CRUD de greendao
        ArticulosDao gestor = session.getArticulosDao();
        // trae todos los registros de la base de datos
        ArrayList<Articulos> datos = (ArrayList<Articulos>) gestor.loadAll();
        return datos;
    }

    public Articulos getArticulo(Long codigo){
        ArticulosDao gestor = session.getArticulosDao();

        Articulos resultado = null;
        try{
            // Consultar un valor   preprar el query   condicion where
            resultado = gestor.queryBuilder().where(ArticulosDao.Properties.Codigo.eq(codigo)).build().unique();

        }catch (Exception ex){
            Log.i("Error getArticulo: " , ex.toString());
        }
        return resultado;
    }

    public Articulos getArticulo(String descripcionProducto){
        ArticulosDao gestor = session.getArticulosDao();
        Articulos resultado = null;
        try{
            // gestor.contuirQuery.donde(AriculosDao.Propiedades.Descripcion.igual(descripcionProducto)).construir().unico();
            resultado = gestor.queryBuilder().where(ArticulosDao.Properties.Descripcion.eq(descripcionProducto)).build().unique();
        }catch (Exception ex){
            Log.i("Error getArticulo: " , ex.toString());
        }
        return resultado;
    }

    public boolean insertarDatos(Articulos datos){
        ArticulosDao gestor = session.getArticulosDao();
        long numero = 0;
        boolean estado = false;
        try{

            numero = gestor.insert(datos);
            Log.i("Todo bien " , String.valueOf(numero));
            //cuando arroka cero Todo anda mal
            if(numero > 0){
                estado = true;
            }
        }catch (Exception ex){
            Log.i("Error insertar datos " + numero , ex.toString());
        }
        return estado;
    }

    public void actualizarProducto(Articulos producto){
        ArticulosDao gestor = session.getArticulosDao();
        try{
            gestor.update(producto);
        }catch (Exception ex) {
            Log.i("Error actualizar ", ex.toString());
        }
    }

    public void eliminarProducto(Articulos producto){
        ArticulosDao gestor = session.getArticulosDao();
        try{
            gestor.delete(producto);
        }catch (Exception ex) {
            Log.i("Error delete ", ex.toString());
        }
    }
}

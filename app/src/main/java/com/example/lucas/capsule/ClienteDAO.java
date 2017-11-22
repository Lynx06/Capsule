package com.example.lucas.capsule;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lucas on 20/11/2017.
 */

// Classe responsável por fazer a tradução dos objetos para o banco de dados e vice-versa
public class ClienteDAO {
    private final String TABLE_CLIENTES = "Clientes";
    private DbGateway gw;

    // Construtor que pega a instância única de DbGateway e deixa guardada em uma variável local para uso posterior
    public ClienteDAO(Context ctx){
        gw = DbGateway.getInstance(ctx);
    }


    public boolean salvar(String nome, String sexo, String uf, boolean vip){
        return salvar(0, nome, sexo, uf, vip);
    }

    // Método que usa a DbGateway para pegar a conexão com o banco de dados e executar um insert ou Update
    public boolean salvar(int id, String nome, String sexo, String uf, boolean vip){
        ContentValues cv = new ContentValues();
        cv.put("Nome", nome);
        cv.put("Sexo", sexo);
        cv.put("UF", uf);
        cv.put("Vip", vip ? 1 : 0);
        if(id > 0)
            return gw.getDatabase().update(TABLE_CLIENTES, cv, "ID=?", new String[]{ id + "" }) > 0;
        else
            return gw.getDatabase().insert(TABLE_CLIENTES, null, cv) > 0;
    }

    // Métodos que retorna todos os Clientes do Banco
    public List<Cliente> retornarTodos(){
        List<Cliente> clientes = new ArrayList<>();
        Cursor cursor = gw.getDatabase().rawQuery("SELECT * FROM Clientes", null); // rawQuery, método que executa uma consulta e retorna um cursor
        while(cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex("ID"));
            String nome = cursor.getString(cursor.getColumnIndex("Nome"));
            String sexo = cursor.getString(cursor.getColumnIndex("Sexo"));
            String uf = cursor.getString(cursor.getColumnIndex("UF"));
            boolean vip = cursor.getInt(cursor.getColumnIndex("Vip")) > 0;
            clientes.add(new Cliente(id, nome, sexo, uf, vip));
        }
        cursor.close();
        return clientes;
    }

    // Método que retorna o ultimo cliente inserido
    public Cliente retornarUltimo(){
        Cursor cursor = gw.getDatabase().rawQuery("SELECT * FROM Clientes ORDER BY ID DESC", null);
        if(cursor.moveToFirst()){
            int id = cursor.getInt(cursor.getColumnIndex("ID"));
            String nome = cursor.getString(cursor.getColumnIndex("Nome"));
            String sexo = cursor.getString(cursor.getColumnIndex("Sexo"));
            String uf = cursor.getString(cursor.getColumnIndex("UF"));
            boolean vip = cursor.getInt(cursor.getColumnIndex("Vip")) > 0;
            cursor.close();
            return new Cliente(id, nome, sexo, uf, vip);
        }

        return null;
    }

    // Método que recebe um id como parametro e exclui a partir dele
    public boolean excluir(int id){
        return gw.getDatabase().delete(TABLE_CLIENTES, "ID=?", new String[]{ id + "" }) > 0;
    }
}

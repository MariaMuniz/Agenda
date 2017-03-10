package com.example.cida.agenda.dominio;

import android.content.ContentValues;
import android.content.Context;
import android.database.*;
import android.database.sqlite.*;
import android.widget.*;

import com.example.cida.agenda.ContatoArrayAdapter;
import com.example.cida.agenda.dominio.entidades.Contato;

import java.util.Date;
import com.example.cida.agenda.R;
/**
 * Created by Cida on 13/07/2016.
 */
public class RepositorioContato {

    private SQLiteDatabase com;

   public RepositorioContato(SQLiteDatabase com)
   {
       this.com = com;
   }

    private  ContentValues  preencherContentValues(Contato contato)
    {

        ContentValues values = new ContentValues();

        values.put(Contato.TELEFONE, contato.getTelefone());
        values.put(Contato.TIPOTELEFONE, contato.getTipoTelefone());
        values.put(Contato.NOME, contato.getNome());
        values.put(Contato.EMAIL, contato.getEmail());
        values.put(Contato.TIPOEMAIL, contato.getTipoEmail());
        values.put(Contato.ENDERECO, contato.getEndereco());
        values.put(Contato.TIPOENDERECO, contato.getTipoEndereco());
        values.put(Contato.DATASESPECIAIS, contato.getDatasEspeciais().getTime());
        values.put(Contato.TIPODATASESPECIAIS, contato.getTipoDatasEspeciais());
        values.put(Contato.GRUPO, contato.getGrupos());

        return values;

    }

    public  void  excluir(long id)
    {

        com.delete(Contato.TABELA, " _id= ?", new String[]{ String.valueOf(id)});
    }



    public void alterar(Contato contato)

    {
        ContentValues values = preencherContentValues(contato);

        com.update(Contato.TABELA, values, " _id= ?", new String[]{ String.valueOf(contato.getId())});

    }


    public void inserir(Contato contato)
    {
        ContentValues values= preencherContentValues(contato);

        com.insertOrThrow(Contato.TABELA, null, values);

    }




   public ContatoArrayAdapter buscaContatos(Context context)

   {

     ContatoArrayAdapter adpContatos = new ContatoArrayAdapter(context,R.layout.linha_contato);
       Cursor cursor = com.query(Contato.TABELA, null, null, null, null, null, null);

       if (cursor.getCount()> 0)
       {

           cursor.moveToFirst();
           do {
               Contato contato = new Contato();

               contato.setId(cursor.getLong((cursor.getColumnIndex(Contato.ID))));
               contato.setNome(cursor.getString(cursor.getColumnIndex(Contato.NOME)));
               contato.setTelefone(cursor.getString(cursor.getColumnIndex(Contato.TELEFONE)));
               contato.setTipoTelefone(cursor.getString(cursor.getColumnIndex(Contato.TIPOTELEFONE)));
               contato.setEmail(cursor.getString(cursor.getColumnIndex(Contato.EMAIL)));
               contato.setTipoEmail(cursor.getString(cursor.getColumnIndex(Contato.TIPOEMAIL)));
               contato.setEndereco(cursor.getString(cursor.getColumnIndex(Contato.ENDERECO)));
               contato.setTipoEndereco(cursor.getString(cursor.getColumnIndex(Contato.TIPOENDERECO)));
               contato.setDatasEspeciais(new Date(cursor.getLong(cursor.getColumnIndex(Contato.DATASESPECIAIS))));
               contato.setTipoDatasEspeciais(cursor.getString(cursor.getColumnIndex(Contato.TIPODATASESPECIAIS)));
               contato.setGrupo(cursor.getString(cursor.getColumnIndex(Contato.GRUPO)));

               adpContatos.add(contato);
           }while (cursor.moveToNext());
       }
       return  adpContatos;
   }

}



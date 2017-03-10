package com.example.cida.agenda;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.*;
import android.widget.*;
import android.content.*;
import android.database.sqlite.*;
import android.database.*;
import com.example.cida.agenda.dominio.RepositorioContato;
import com.example.cida.agenda.database.DataBase;
import com.example.cida.agenda.dominio.entidades.Contato;
import com.example.cida.agenda.app.MessageBox;

public class Actagenda extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {


    private ImageButton btnAdicionar;
    private EditText edtPesquisa;
    private ListView lstContato;
    private DataBase database;
    private SQLiteDatabase com;
    private ArrayAdapter<Contato> adpContatos;
    private RepositorioContato repositorioContato;

    public static final String PAR_CONTATO ="CONTATO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actagenda);

        btnAdicionar = (ImageButton) findViewById(R.id.btnAdicionar);
        btnAdicionar.setOnClickListener(this);
        edtPesquisa = (EditText) findViewById(R.id.edtPesquisa);
        lstContato = (ListView) findViewById(R.id.lstContato);
        lstContato.setOnItemClickListener(this);
        try {


            database = new DataBase(this);
            com = database.getWritableDatabase();

            repositorioContato = new RepositorioContato(com);

            adpContatos=repositorioContato.buscaContatos(this);
            lstContato.setAdapter(adpContatos);
            FiltraDados filtraDados= new FiltraDados(adpContatos);
            edtPesquisa.addTextChangedListener(filtraDados);

        } catch (SQLException ex)

        {
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setMessage("Erro ao criar o banco " + ex.getMessage());
            dlg.setNeutralButton("ok", null);
            dlg.show();

        }
        }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (com != null)
        {
            com.close();
        }
    }


    @Override

        public void onClick (View v)

    {

        Intent it = new Intent(this, ActCadContato.class);
        startActivityForResult(it, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        adpContatos=repositorioContato.buscaContatos(this);

        lstContato.setAdapter(adpContatos);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Contato contato =  adpContatos.getItem(position);
        Intent it = new Intent(this, ActCadContato.class);
        it.putExtra(PAR_CONTATO , contato);

        startActivityForResult(it, 0);


    }
private class FiltraDados implements TextWatcher
{
    private ArrayAdapter<Contato> arrayAdapter;

    private FiltraDados(ArrayAdapter<Contato> arrayAdapter)
    {
      this.arrayAdapter = arrayAdapter;
    }
     public  void setArrayAdapter(ArrayAdapter<Contato> arrayAdapter)
     {
         this.arrayAdapter = arrayAdapter;
     }




    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        arrayAdapter.getFilter().filter(s);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}




}

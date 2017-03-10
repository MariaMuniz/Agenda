package com.example.cida.agenda;

import android.app.DatePickerDialog;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

import com.example.cida.agenda.app.MessageBox;
import com.example.cida.agenda.app.ViewHelp;
import com.example.cida.agenda.database.DataBase;
import com.example.cida.agenda.dominio.RepositorioContato;
import com.example.cida.agenda.dominio.entidades.Contato;
import com.example.cida.agenda.util.DateUtil;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class ActCadContato extends AppCompatActivity {

    private EditText edtNome;
    private EditText edtTelefone;
    private EditText edtEmail;
    private EditText edtEndreco;
    private EditText edtDatasEspeciais;
    private EditText edtGrupo;

     private Spinner spnTipoTelefone;
     private Spinner spnTipoEmail;
     private Spinner spnTipoEndereco;
     private Spinner spnTipoDatasEspeciais;

    private ArrayAdapter<String> adptipoEmail;
    private ArrayAdapter<String> adptipoTelefone;
    private ArrayAdapter<String> adptipoEndereco;
    private ArrayAdapter<String> adptipoDatasEspeciais;

    private DataBase database;
    private SQLiteDatabase com;
    private RepositorioContato repositorioContato;
    private Contato contato;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contato);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        edtNome = (EditText) findViewById(R.id.edtNome);
        edtTelefone = (EditText) findViewById(R.id.edtTelefone);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtEndreco = (EditText) findViewById(R.id.edtEndreco);
        edtDatasEspeciais = (EditText) findViewById(R.id.edtDatasEspeciais);
        edtGrupo = (EditText) findViewById(R.id.edtGrupo);

        spnTipoTelefone = (Spinner)findViewById(R.id.spnTipoTelefone);
        spnTipoEmail = (Spinner)findViewById(R.id.spnTipoEmail);
        spnTipoEndereco = (Spinner)findViewById(R.id.spnTipoEndereco);
        spnTipoDatasEspeciais = (Spinner)findViewById(R.id.spnDatasEspeciais);

        adptipoEmail= ViewHelp.createArrayAdapter(this,spnTipoEmail);
        adptipoTelefone= ViewHelp.createArrayAdapter(this,spnTipoTelefone);
        adptipoEndereco= ViewHelp.createArrayAdapter(this,spnTipoEndereco);
        adptipoDatasEspeciais= ViewHelp.createArrayAdapter(this,spnTipoDatasEspeciais);

        /*
        adptipoTelefone = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item);
        adptipoTelefone.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adptipoEmail = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item);
        adptipoEmail.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adptipoEndereco= new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item);
        adptipoEndereco.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adptipoDatasEspeciais= new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item);
        adptipoDatasEspeciais.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spnTipoTelefone.setAdapter(adptipoTelefone);
        spnTipoEmail.setAdapter(adptipoEmail);
        spnTipoEndereco.setAdapter(adptipoEndereco);
        spnTipoDatasEspeciais.setAdapter(adptipoDatasEspeciais);
        */
        adptipoEmail.add("Casa");
        adptipoEmail.add("Trabalho");
        adptipoEmail.add("Outros");

        adptipoTelefone.add("Casa");
        adptipoTelefone.add("Celular");
        adptipoTelefone.add("Trabalho");
        adptipoTelefone.add("Principal");
        adptipoTelefone.add("Fax Casa");
        adptipoTelefone.add("Pager");
        adptipoTelefone.add("Fax Trabalho");
        adptipoTelefone.add("Outros");

        adptipoEndereco.add("Casa");
        adptipoEndereco.add("Trabalho");
        adptipoEndereco.add("Outros");

        adptipoDatasEspeciais.add("Aniversário");
        adptipoDatasEspeciais.add("Data comemorativa");
        adptipoDatasEspeciais.add("Outros");

        ExibeDataListener listener = new ExibeDataListener();
        edtDatasEspeciais.setOnClickListener(listener);
        edtDatasEspeciais.setOnFocusChangeListener(listener);

        Bundle bundle = getIntent().getExtras();

        if ((bundle != null)&& (bundle.containsKey(Actagenda.PAR_CONTATO)))
        {
            contato = (Contato)bundle.getSerializable(Actagenda.PAR_CONTATO);
            preencheDados();
        }
            else

           contato = new Contato();

        try
        {

            database = new DataBase(this);
            com = database.getWritableDatabase();

            repositorioContato = new RepositorioContato(com);



        } catch (SQLException ex)

        {
            MessageBox.show(this, "ERRO", "Erro ao criar o banco " + ex.getMessage());


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
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_actagenda, menu);

        if (contato.getId() != 0)
            menu.getItem(1).setVisible(true);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {


            case R.id.mni_acao1:
              salvar();

                finish();

                break;
            case R.id.mni_acao2:
                excluir();
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private  void preencheDados()
    {
        edtNome.setText(contato.getNome());
        edtTelefone.setText(contato.getTelefone());
        spnTipoTelefone.setSelection(Integer.parseInt(contato.getTipoTelefone()));
        edtEmail.setText(contato.getEmail());
        spnTipoEmail.setSelection( Integer.parseInt(contato.getTipoEmail() ));
        edtEndreco.setText(contato.getEndereco());
        spnTipoEndereco.setSelection( Integer.parseInt(contato.getTipoEndereco() ));

        DateFormat format= DateFormat.getDateInstance(DateFormat.SHORT);
        String dt= format.format(contato.getDatasEspeciais());

        edtDatasEspeciais.setText(dt);
        spnTipoDatasEspeciais.setSelection( Integer.parseInt(contato.getTipoDatasEspeciais() ));
        edtGrupo.setText(contato.getGrupos());
    }


      private void  excluir()
      {
          try {

              repositorioContato.excluir(contato.getId());
      } catch (Exception ex)

    {
        MessageBox.show(this, "ERRO", "Erro ao excluir dados" + ex.getMessage());

    }


      }


    private void salvar() {


        try {


            contato.setNome(edtNome.getText().toString());
            contato.setTelefone(edtTelefone.getText().toString());
            contato.setEmail(edtEmail.getText().toString());
            contato.setEndereco(edtEndreco.getText().toString());


            contato.setGrupo(edtGrupo.getText().toString());

            contato.setTipoTelefone(String.valueOf(spnTipoTelefone.getSelectedItemPosition()));
            contato.setTipoEmail(String.valueOf(spnTipoEmail.getSelectedItemPosition()));
            contato.setTipoEndereco(String.valueOf(spnTipoEndereco.getSelectedItemPosition()));
            contato.setTipoDatasEspeciais(String.valueOf(spnTipoDatasEspeciais.getSelectedItemPosition()));

            if (contato.getId() == 0)
                repositorioContato.inserir(contato);
            else
                repositorioContato.alterar(contato);

        } catch (Exception ex)

        {
            MessageBox.show(this, "ERRO", "Erro ao salvar dados" + ex.getMessage());

        }
    }
    private void exibeData() {


        Calendar calendar = Calendar.getInstance();

        int ano =  calendar.get(Calendar.YEAR);
        int mes =  calendar.get(Calendar.MONTH);
        int dia =  calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dlg = new DatePickerDialog(this, new SelecionaDataListener(), ano, mes, dia);
        dlg.show();
    }


    private class ExibeDataListener implements View.OnClickListener, View.OnFocusChangeListener


    {


        @Override
        public void onClick(View v) {
            exibeData();
        }

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus) ;
            exibeData();
        }
    }

    private class SelecionaDataListener implements DatePickerDialog.OnDateSetListener {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {


            String dt = DateUtil.dateToString(year, monthOfYear, dayOfMonth);
            Date data = DateUtil.getDate(year, monthOfYear, dayOfMonth);


            edtDatasEspeciais.setText(dt);
            contato.setDatasEspeciais(data);
        }
    }

}


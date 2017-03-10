package com.example.cida.agenda;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.cida.agenda.dominio.entidades.Contato;

/**
 * Created by AdilsonMarcelino on 21/07/2016.
 */
public class ContatoArrayAdapter extends ArrayAdapter<Contato> {

    private int resource = 0;
    private LayoutInflater inflater;
    private Context context;
    public ContatoArrayAdapter(Context context, int resource) {

        super(context, resource);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.resource = resource;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = null;
        ViewHolder viewHolder = null;

        if (convertView == null) {

            viewHolder = new ViewHolder();

            view = inflater.inflate(resource, parent, false);

            viewHolder.txtCor = (TextView)view.findViewById(R.id.txtCor);
            viewHolder.txtNome = (TextView)view.findViewById(R.id.txtNome);
            viewHolder.txtTelefone = (TextView)view.findViewById(R.id.txtTelefone);
            view.setTag(viewHolder);
            convertView = view;
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
            view = convertView;
        }
        Contato contato = getItem(position);
        //Log.d("Contatos"," nome = "+)
        if (contato.getNome().toUpperCase().startsWith("A"))
            ((GradientDrawable)viewHolder.txtCor.getBackground()).setColor(context.getResources().getColor(R.color.A));
        else
        if (contato.getNome().toUpperCase().startsWith("B"))
            ((GradientDrawable)viewHolder.txtCor.getBackground()).setColor(context.getResources().getColor(R.color.B));
        else
        if (contato.getNome().toUpperCase().startsWith("C"))
            ((GradientDrawable)viewHolder.txtCor.getBackground()).setColor(context.getResources().getColor(R.color.C));
        else
        if (contato.getNome().toUpperCase().startsWith("D"))
            ((GradientDrawable)viewHolder.txtCor.getBackground()).setColor(context.getResources().getColor(R.color.D));
        else
            ((GradientDrawable)viewHolder.txtCor.getBackground()).setColor(context.getResources().getColor(R.color.E));

        String s = contato.getNome();
        if(s!=null && s.length()>0)
            s = ""+s.charAt(0);
        viewHolder.txtCor.setText(s);
        viewHolder.txtNome.setText(contato.getNome());
        viewHolder.txtTelefone.setText(contato.getTelefone());

        return view;
    }

    static class ViewHolder
    {

       TextView txtCor;
        TextView txtNome;
        TextView txtTelefone;



    }
}


package com.example.proyectoprofesores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class AdapterDirectorio extends BaseAdapter {

    Context context;
    List<Directorio> lst;

    public AdapterDirectorio(Context context, List<Directorio> lst) {
        this.context = context;
        this.lst = lst;
    }


    @Override
    public int getCount() {
        return lst.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ImageView ImageViewContacto;
        TextView TextViewNombre;
        TextView TextViewMadre;
        TextView TextViewPadre;

        Directorio c=lst.get(i);

        if(view==null)
            view= LayoutInflater.from(context).inflate(R.layout.listview_contactos,null);

        ImageViewContacto=view.findViewById(R.id.imageViewContacto);
        TextViewNombre=view.findViewById(R.id.textViewNombreAlumno);
        TextViewMadre=view.findViewById(R.id.textViewMadre);
        TextViewPadre=view.findViewById(R.id.textViewPadre);

        ImageViewContacto.setImageResource(c.image);
        TextViewNombre.setText(c.nombre);
        TextViewMadre.setText(c.num_Madre);
        TextViewPadre.setText(c.num_Padre);
        return view;
    }
    //H
}

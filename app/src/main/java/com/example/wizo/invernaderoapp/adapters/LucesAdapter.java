package com.example.wizo.invernaderoapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;


import com.example.wizo.invernaderoapp.LucesFragment;
import com.example.wizo.invernaderoapp.R;
import com.example.wizo.invernaderoapp.beans.Luz;

import java.util.List;

/**
 * Created by ALUMNOS on 25/05/2018.
 */

public class LucesAdapter extends ArrayAdapter<Luz> {

    private Context context;
    private int resource;
    private  List<Luz> luces;
    private LucesFragment lucesFragment;

    public LucesAdapter(@NonNull Context context, int resource, @NonNull List<Luz> luces, LucesFragment lucesFragment){
        super(context,resource,luces);
        this.context = context;
        this.resource = resource;
        this.luces = luces;
        this.lucesFragment =lucesFragment;
    }


    private  class ViewHolder{
        TextView txtItemLuz;
        Switch switchEstado;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View item = convertView;
        ViewHolder viewHolder;

        if(item == null){
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            item = layoutInflater.inflate(resource,null);
            viewHolder = new ViewHolder();

            viewHolder.switchEstado = item.findViewById(R.id.switchEstado);
            viewHolder.txtItemLuz = item.findViewById(R.id.txtItemLuz);


            item.setTag(viewHolder);

        }else{
            viewHolder = (ViewHolder) item.getTag();
        }

        Luz luz = luces.get(position);
        viewHolder.switchEstado.setTag(luz.getId());
        viewHolder.switchEstado.setOnCheckedChangeListener(lucesFragment);
        viewHolder.txtItemLuz.setText(luz.getId());

        return item;

    }
}

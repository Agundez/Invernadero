package com.example.wizo.invernaderoapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.wizo.invernaderoapp.adapters.LucesAdapter;
import com.example.wizo.invernaderoapp.beans.ListaLuces;
import com.example.wizo.invernaderoapp.beans.Luz;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class LucesFragment extends Fragment implements CompoundButton.OnCheckedChangeListener{
    InvernaderoApi api;
    ListView listaLuces;
    ListaLuces luces;
    LucesAdapter lucesAdapter;
   // Switch switchEstado;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_luces,container,false);

        listaLuces = view.findViewById(R.id.listaLuces);

        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://profejuan.pe.hu/servicios/invernadero/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create()).build();

        api = retrofit.create(InvernaderoApi.class);

        Call<ListaLuces> peticion =  api.getLuzApi();
        peticion.enqueue(new Callback<ListaLuces>() {
            @Override
            public void onResponse(Call<ListaLuces> call, Response<ListaLuces> response) {
                luces = (ListaLuces) response.body();
                lucesAdapter = new LucesAdapter(getContext(),R.layout.item_luces,luces.getLuces(),LucesFragment.this);
                listaLuces.setAdapter(lucesAdapter);

            }

            @Override
            public void onFailure(Call<ListaLuces> call, Throwable t) {
                Toast.makeText(getContext(), "No se han recibido los datos correctamente",Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }


    public void PostLuzApi(String id, boolean estadoLuz){
        Call<String> peticionLuz = api.postLuzApi(id,estadoLuz);
        peticionLuz.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getContext(), "No se han enviado los datos",Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        PostLuzApi(buttonView.getTag().toString(),isChecked);
    }
}
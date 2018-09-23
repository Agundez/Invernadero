package com.example.wizo.invernaderoapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class HumedadFragment extends Fragment implements CompoundButton.OnCheckedChangeListener{
    InvernaderoApi api;
    TextView humedadText;
    ToggleButton toggleAspersores;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_humedad,container,false);

        humedadText = view.findViewById(R.id.humedadText);
        toggleAspersores = view.findViewById(R.id.toggleAspersores);
        toggleAspersores.setOnCheckedChangeListener(this);

        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://profejuan.pe.hu/servicios/invernadero/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create()).build();

        api = retrofit.create(InvernaderoApi.class);

        Call<String> peticion = api.humedadApi();
        peticion.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String respuesta = response.body();
                humedadText.setText(respuesta);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });

        Call<String> peticionAspersores = api.aspersoresApi();
        peticionAspersores.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String respuesta = response.body();
                if(respuesta.equals("true")){
                    toggleAspersores.setChecked(true);
                }else if (respuesta.equals("false")){
                    toggleAspersores.setChecked(false);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });

        return view;
    }

          public void PostAspersores(boolean estadoAspersores){
         Call<String> peticionAspersores = api.postAspersores(estadoAspersores);
        peticionAspersores.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getContext(), "No se han enviado los datos correctamente",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        PostAspersores(isChecked);

    }
}

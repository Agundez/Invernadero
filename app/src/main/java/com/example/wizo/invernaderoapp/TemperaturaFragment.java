package com.example.wizo.invernaderoapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class TemperaturaFragment extends Fragment implements CompoundButton.OnCheckedChangeListener
{

    InvernaderoApi api;
    TextView temperaturaText;
    ToggleButton toggleCalefaccion;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_temperatura, container, false);

        temperaturaText = view.findViewById(R.id.temperaturaText);
        toggleCalefaccion = view.findViewById(R.id.toggleCalefaccion);
        toggleCalefaccion.setOnCheckedChangeListener(this);

        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://profejuan.pe.hu/servicios/invernadero/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create()).build();

        api = retrofit.create(InvernaderoApi.class);

        Call<String> peticion = api.temperaturaApi();
        peticion.enqueue(new Callback<String>()
        {
            @Override
            public void onResponse(Call<String> call, Response<String> response)
            {
                String respuesta = response.body();
                temperaturaText.setText(respuesta);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t)
            {
                Toast.makeText(getContext(), "No se han recibido los datos correctamente",Toast.LENGTH_SHORT).show();
            }
        });

        Call<String> peticionCalefaccion = api.calefaccionApi();
        peticionCalefaccion.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String respuesta = response.body();
                if (respuesta.equals("true")) {
                    toggleCalefaccion.setChecked(true);
                } else if (respuesta.equals("false")) {
                    toggleCalefaccion.setChecked(false);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getContext(), "No se han recibido los datos correctamente",Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }


    public void PostCalefaccion( boolean estadoCalefaccion) {
        Call<String> peticionCalefaccion = api.postCalefaccion(estadoCalefaccion);
        peticionCalefaccion.enqueue(new Callback<String>() {
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
        PostCalefaccion(isChecked);
    }
}





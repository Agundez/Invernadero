package com.example.wizo.invernaderoapp;

import com.example.wizo.invernaderoapp.beans.ListaLuces;
import com.example.wizo.invernaderoapp.beans.Luz;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface InvernaderoApi {

    @GET("temperatura.json")
    Call<String> temperaturaApi();

    @GET ("calefaccion.json")
    Call<String> calefaccionApi();

    @FormUrlEncoded
    @POST("calefaccion.php")
    Call<String> postCalefaccion(@Field("on") boolean estadoCalefaccion);

    @GET ("humedad.json")
    Call<String> humedadApi();

    @GET ("aspersores.json")
    Call<String> aspersoresApi();

    @FormUrlEncoded
    @POST("aspersores.php")
    Call<String> postAspersores(@Field("on") boolean estadoAspersor);

    @GET ("luces.json")
    Call<ListaLuces> getLuzApi();

    @FormUrlEncoded
    @POST("luz.php")
    Call<String> postLuzApi(@Field("id") String id, @Field("on") boolean estadoLuz);


}

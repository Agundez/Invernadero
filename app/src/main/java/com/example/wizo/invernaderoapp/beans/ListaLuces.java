package com.example.wizo.invernaderoapp.beans;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ListaLuces implements Serializable{
    private List<Luz> luces;

    public ListaLuces(){
        luces = new ArrayList<>();
    }

    public List<Luz> getLuces() {
        return luces;
    }
}

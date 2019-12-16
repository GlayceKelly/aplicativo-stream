package com.example.glayce.desafioandroid.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Glayce on 12/12/2019.
 */

public class ItensFirebase implements Serializable
{
    // Variaveis da classe
    @SerializedName("name")
    public String sNome = "";

    @SerializedName("im")
    public String sImagem = "";

    @SerializedName("bg")
    public String sVideo = "";

    @SerializedName("sg")
    public String sAudio = "";

    // Construtor da classe
    public ItensFirebase( String sNomeParam, String sImagemParam, String sVideoParam, String sAudioParam )
    {
        sNome = sNomeParam;
        sImagem = sImagemParam;
        sVideo = sVideoParam;
        sAudio = sAudioParam;
    }

}

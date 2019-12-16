package com.example.glayce.desafioandroid.Util;

/**
 * Created by Glayce on 15/12/2019.
 */

public interface ComunicacaoGeral
{
    /**
     * Chame este método para se comunicar diretamente com a activity através do método
     * onReceiveData().
     * */
    void comunicaGeral(Class classe, int iId, boolean bResultado, Object... oObjetos);
}

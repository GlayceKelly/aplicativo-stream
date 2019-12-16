package com.example.glayce.desafioandroid.Util;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by Glayce on 15/12/2019.
 */

public abstract  class ActivityBase extends AppCompatActivity implements ComunicacaoGeral
{
    @Override
    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);

        try
        {
            //Inicia todos os controles da tela
            iniciaControles();

            //carrega os dados nas variaveis e controles
            carregaDados();
        }
        catch (Exception err)
        {
            Toast.makeText(this, "Erro em " + getClass() + ": " + err.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Método utilizado para iniciar os controles da tela
     * */
    public abstract void iniciaControles() throws Exception;

    /**
     * Método para atribuir dados aos controles da tela
     * */
    public abstract void carregaDados() throws Exception;

    /**
     * Método para enviar dados entre as activities e tasks
     * */
    public void onReceiveData(Class classe, int iId, boolean bResultado, Object... oObjetos) throws Exception
    {
    }

    @Override
    public final void comunicaGeral(Class classe, int iId, boolean bResultado, Object... oObjetos)
    {
        try
        {
            // Chama o método para que a activity trate, caso deseje.
            onReceiveData(classe, iId, bResultado, oObjetos);
        }
        catch (Exception err)
        {
            Toast.makeText(this, "Erro em " + getClass() + ": " + err.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}

package com.example.glayce.desafioandroid.Util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Glayce on 12/12/2019.
 */

public class Apoio
{
    // Constantes da classe
    public static String PREFS_NOME = "PREFS_NOME";
    public static String PREFS_IMAGEM = "PREFS_IMAGEM ";
    public static String PREFS_VIDEO = "PREFS_VIDEO";
    public static String PREFS_AUDIO = "PREFS_AUDIO";

    /**
     * Grava strings na preferencia
     * */
    public static void gravaPrefsValorString(Context context, String sChave, String sValor) throws Exception
    {
        SharedPreferences prefs = null;
        SharedPreferences.Editor editor = null;

        // Recupera a instancia
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        editor = prefs.edit();

        // Define a chave e o valor
        editor.putString(sChave, sValor);

        // Persiste os dados
        editor.commit();
    }

    // Método para abrir dialog
    public static AlertDialog abreDialog(Context context, String sTitulo, String sMensagem, String sBotaoPositivo, String sBotaoNegativo) throws Exception
    {
        AlertDialog.Builder dialog = null;

        dialog = new AlertDialog.Builder(context);
        dialog.setTitle(sTitulo);
        dialog.setMessage(sMensagem);
        dialog.setPositiveButton(sBotaoPositivo, (DialogInterface.OnClickListener) context);
        dialog.setNegativeButton(sBotaoNegativo, (DialogInterface.OnClickListener) context);

        return dialog.create();
    }
}

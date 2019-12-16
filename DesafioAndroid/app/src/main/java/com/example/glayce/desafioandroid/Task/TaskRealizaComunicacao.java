package com.example.glayce.desafioandroid.Task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.glayce.desafioandroid.Model.ItensFirebase;
import com.example.glayce.desafioandroid.Model.RestReturn;
import com.example.glayce.desafioandroid.Util.ComunicacaoGeral;
import com.example.glayce.desafioandroid.Util.RestCommunication;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Glayce on 15/12/2019.
 */

public class TaskRealizaComunicacao extends AsyncTask<Void, Boolean, Boolean>
{
    //Variaveis da classe
    private ProgressDialog progressDialog = null;
    private String sMensagem = "";
    private Context context = null;
    private ComunicacaoGeral comunicacaoGeral = null;
    private ArrayList<ItensFirebase> arrItens = null;

    /**
     * Construtor da classe
     */
    public TaskRealizaComunicacao(Context contextParam, ComunicacaoGeral comunicacaoGeralParam)
    {
        //Alimenta as variaveis da classe
        context = contextParam;
        comunicacaoGeral = comunicacaoGeralParam;
    }

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();

        //Mostra o ProgressDialog
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Aguarde...");
        progressDialog.setMessage("Buscando informações");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected Boolean doInBackground(Void... voidArg)
    {
        boolean bRetorno = false;
        RestReturn restReturn = null;
        JSONObject jsonObject = null;
        JsonObject jsonObjectAux = null;
        JSONArray arrObjects = null;
        JSONObject jsonObj = null;
        Gson gson = null;
        int iRetHppt = 200;
        ItensFirebase itensFirebase = null;
        String sCaminho = "";

        try
        {
            sCaminho = "http://linuxapp01.eastus.cloudapp.azure.com/looke/assets.json";

            // Executa a comunicacao
            restReturn = RestCommunication.efetuaOperacaoRest(sCaminho, RestCommunication.RESTPOST, "", 5 * 1000);

            // Guarda o retorno do RestCommunication
            iRetHppt = restReturn.getiCodResposta();

            //Se for código 200 de retorno
            if ( iRetHppt <= 200 )
            {
                //Executa importacao de dados que retorna em formato json
                gson = new Gson();

                //gera o json
                jsonObjectAux = gson.fromJson(restReturn.getsRetorno(), JsonObject.class);

                jsonObj = new JSONObject(jsonObjectAux.toString());

                arrObjects = jsonObj.getJSONArray("objects");

                // Instancia o arraylist
                arrItens = new ArrayList<>();

                // loop pelo JSONArray forecast
                for (int i = 0; i < arrObjects.length(); i++)
                {
                    JSONObject c = arrObjects.getJSONObject(i);

                    // Definindo os itens obtidos nas strings
                    String nome = c.getString("name");
                    String imagem= c.getString("im");
                    String video = c.getString("bg");
                    String audio= c.getString("sg");

                    //Instacia o objeto e adiciona no array
                    itensFirebase = new ItensFirebase(nome, imagem, video, audio);
                    arrItens.add(itensFirebase);
                }

                //verifico se o retorno veio maior que 0
                if(jsonObj.length() != 0)
                {
                    //retorna true
                    bRetorno = true;
                }
                else
                {
                    sMensagem = restReturn.getsMensagem();
                }
            }
            else
            {
                sMensagem = "Erro de comunicação - Codigo : " + restReturn.getiCodResposta() + " - Mensagem : " + restReturn.getsMensagem();
            }
        }
        catch (Exception err)
        {
            sMensagem = err.getMessage();
        }

        return bRetorno;
    }

    @Override
    protected void onPostExecute(Boolean bResult)
    {
        super.onPostExecute(bResult);

        try
        {
            // Caso haja sucesso do processo
            if ( bResult )
            {
                //Passa o controle com o resultado para a activity que fez a chamada
                comunicacaoGeral.comunicaGeral(getClass(), 0, true, arrItens);
            }
            else
            {
                //Passa o controle com o resultado para a activity que fez a chamada
                comunicacaoGeral.comunicaGeral(getClass(), 0, false, sMensagem);
            }
        }
        catch(Exception err)
        {
            Toast.makeText(context, "Erro TaskRealizaComunicacao: " + err.getMessage(), Toast.LENGTH_SHORT).show();
        }
        finally
        {
            if (progressDialog.isShowing())
            {
                progressDialog.dismiss();
            }
        }
    }
}

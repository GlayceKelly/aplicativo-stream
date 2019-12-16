package com.example.glayce.desafioandroid.Task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.example.glayce.desafioandroid.Util.ComunicacaoGeral;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Glayce on 15/12/2019.
 */

public class TaskRealizaDownloadVideo extends AsyncTask<Void, Boolean, Boolean>
{
    // Variaveis da classe
    private Context context = null;
    private ComunicacaoGeral comunicacaoGeral = null;
    private ProgressDialog progressDialog = null;
    private boolean bRetorno = false;
    private String sLinkVideo = "";
    private String sNomeDesc = "";

    // Contrutor da classe
    public TaskRealizaDownloadVideo(Context contextParam, ComunicacaoGeral comunicacaoGeralParam, String sLinkVideoParam, String sNomeDescParam)
    {
        context = contextParam;
        comunicacaoGeral = comunicacaoGeralParam;
        sLinkVideo = sLinkVideoParam;
        sNomeDesc = sNomeDescParam;
    }

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();

        // Mostra o ProgressDialog
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Aguarde...");
        progressDialog.setMessage("Realizando Download do arquivo.");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected Boolean doInBackground(Void... voids)
    {
        String sUrl = sLinkVideo;

        downloadfile(sUrl);

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
                comunicacaoGeral.comunicaGeral(getClass(), 0, true, null);
            }
            else
            {
                //Passa o controle com o resultado para a activity que fez a chamada
                comunicacaoGeral.comunicaGeral(getClass(), 0, false, null);
            }
        }
        catch (Exception err)
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

    private void downloadfile(String sVideoCaminho)
    {
        // Variaveis do método
        String sNomeArquivo = "";
        String sRootDir = "";
        File rootFile = null;
        URL url = null;
        HttpURLConnection connection = null;
        FileOutputStream fileOutPutStream = null;
        InputStream inputStream = null;
        byte[] buffer = null;
        int len1 = 0;

        try
        {
            // Define o nome do arquivo que ficara armazenado
            sNomeArquivo = "video_" + sNomeDesc +  ".mp4";

            // Obtem o caminho para salvar
            sRootDir = String.valueOf(Environment.getExternalStorageDirectory());
            rootFile = new File(sRootDir, sNomeArquivo);

            // Monta a URL
            url = new URL(sVideoCaminho);

            // Realiza a conexao
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.connect();

            fileOutPutStream = new FileOutputStream(rootFile);

            inputStream = connection.getInputStream();

            String sAux = inputStream.toString();

            if( !sAux.equals("") )
            {
                bRetorno = true;
            }

            buffer = new byte[1024];

            len1 = 0;

            while ((len1 = inputStream.read(buffer)) > 0)
            {
                fileOutPutStream.write(buffer, 0, len1);
            }

            fileOutPutStream.close();
        }
        catch (IOException e)
        {
            Log.d("Error....", e.toString());
        }

    }
}

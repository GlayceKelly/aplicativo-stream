package com.example.glayce.desafioandroid.Util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Glayce on 12/12/2019.
 */

public class HttpHandler
{
    public HttpHandler()
    {
    }

    //Método que faz a chamada HTTP
    public String chamadaHttp(String sUrl)
    {
        String respota = null;

        try
        {
            URL url = new URL(sUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            //Le a resposta
            InputStream inputStream = new BufferedInputStream(connection.getInputStream());
            respota = converteStream(inputStream);


        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return respota;
    }

    //Método que converte o Strem em String
    private String converteStream(InputStream inputStream) throws IOException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();

        String linha;

        try
        {
            while ((linha = reader.readLine()) != null)
            {
                stringBuilder.append(linha).append("\n");
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                inputStream.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        return stringBuilder.toString();

    }
}

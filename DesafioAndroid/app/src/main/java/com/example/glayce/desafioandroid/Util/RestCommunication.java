package com.example.glayce.desafioandroid.Util;

import com.example.glayce.desafioandroid.Model.RestReturn;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * Created by Glayce on 15/12/2019.
 */

public class RestCommunication
{
    // Enderecos das tabelas rest
    public static final String RESTPOST = "POST";
    public static final String RESTDELETE = "DELETE";
    public static final String RESTGET = "GET";
    public static final String RESTPUT = "PUT";

    //Flags de compactação e criptografia
    private static final boolean COMPACTA = false;
    private static final boolean CRIPTOGRAFA = false;

    /**
     * Efetua a operacao solicitada com o servidor REST
     */
    public static RestReturn efetuaOperacaoRest(String sCaminhoRest, String sOperacao, String sBody, int iTimeOut) throws  Exception
    {
        URL url = null;
        HttpURLConnection httpConn = null;
        OutputStream streamGravacao = null;
        InputStream streamLeitura = null;
        ByteArrayOutputStream streamBinaria = null;
        RestReturn restReturn = null;
        byte[] btDados = null;
        int iBytesRec = 0;

        try
        {
            // Instancia a conexao http e o objeto de url
            sCaminhoRest = sCaminhoRest.replace(" ", "%20");

            url = new URL(sCaminhoRest);

            httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setRequestMethod(sOperacao);
            httpConn.setUseCaches(false);
            httpConn.setAllowUserInteraction(false);
            httpConn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            httpConn.setReadTimeout(iTimeOut*1000);
            httpConn.setConnectTimeout(iTimeOut*1000);

            // Caso a requisicao seja diferente de um GET
            if ( sOperacao.equals(RestCommunication.RESTPOST) || sOperacao.equals(RestCommunication.RESTPUT) )
            {
                httpConn.setDoOutput(true);

                httpConn.setRequestProperty("Content-Length", "0");
            }

            // Se recebemos sucesso do servidor
            if ( httpConn.getResponseCode() < 400 )
            {
                // Recria a stream binaria assionada a stream de recebimento dos dados
                streamBinaria = new ByteArrayOutputStream();
                streamGravacao = new DataOutputStream(streamBinaria);

                // Recupera a stream de leitura binaria
                streamLeitura = httpConn.getInputStream();

                // Aloca memoria pra receber os dados
                btDados = new byte[1024];

                // Entra em laco
                do
                {
                    // Le dados do tamanho do buffer
                    iBytesRec = streamLeitura.read(btDados, 0, btDados.length);

                    // Se nao tem mais anda pra ler, saimos
                    if ( iBytesRec <= 0 )
                    {
                        break;
                    }

                    // Grava os bytes lidos, na quantidade lida, na stream de gravacao
                    streamGravacao.write(btDados, 0, iBytesRec);
                }
                while ( true );

                // 'Commita' os dados na stream
                streamGravacao.flush();

                // Recupera os bytes recebidos
                btDados = streamBinaria.toByteArray();

                streamLeitura.close();
                streamBinaria.close();
                streamGravacao.close();

                // Instancia o retorno que sera enviado ao client
                restReturn = new RestReturn(httpConn.getResponseCode(), "", new String(btDados));
            }
            else if ( httpConn.getResponseCode() == 500 )
            {
                // Instancia o retorno que sera enviado ao client
                restReturn = new RestReturn(httpConn.getResponseCode(), "Erro interno no servidor!", null);
            }
            else if ( httpConn.getResponseCode() == 400 )
            {
                // Instancia o retorno que sera enviado ao client
                restReturn = new RestReturn(httpConn.getResponseCode(), "Caminho não encontrado!", null);
            }
            else if ( httpConn.getResponseCode() == 403 )
            {
                // Instancia o retorno que sera enviado ao client
                restReturn = new RestReturn(httpConn.getResponseCode(), "Acesso no autorizado!", null);
            }
            else if ( httpConn.getResponseCode() == 404 )
            {
                // Instancia o retorno que sera enviado ao client
                restReturn = new RestReturn(httpConn.getResponseCode(), "Caminho não encontrado!", null);
            }
            else
            {
                // Instancia o retorno que sera enviado ao client
                restReturn = new RestReturn(httpConn.getResponseCode(), "Erro ao tentar comunicar com o servidor!", null);
            }
        }
        finally
        {
            //Fecha a conexão
            if ( httpConn != null )
            {
                httpConn.disconnect();
            }
        }

        // Retorna os dados recebidos do servidor
        return restReturn;
    }
}

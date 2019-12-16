package com.example.glayce.desafioandroid.Model;

/**
 * Created by Glayce on 15/12/2019.
 */

public class RestReturn
{
    // Variaveis da classe
    private int iCodResposta = 0;
    private String sMensagem = "";
    private String sRetorno = null;

    // Construtor da classe
    public RestReturn (int iCodRespostaParam, String sMensagemParam, String osRetornoParam)
    {
        iCodResposta = iCodRespostaParam;
        sMensagem = sMensagemParam;
        sRetorno = osRetornoParam;
    }

    public int getiCodResposta()
    {
        return iCodResposta;
    }

    public void setiCodResposta(int iCodResposta)
    {
        this.iCodResposta = iCodResposta;
    }

    public String getsMensagem() {
        return sMensagem;
    }

    public void setsMensagem(String sMensagem)
    {
        this.sMensagem = sMensagem;
    }

    public String getsRetorno() {
        return sRetorno;
    }

    public void setsRetorno(String sRetorno)
    {
        this.sRetorno = sRetorno;
    }
}

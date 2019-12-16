package com.example.glayce.desafioandroid.Telas;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.glayce.desafioandroid.Adapter.AdapterNomeBotao;
import com.example.glayce.desafioandroid.Model.ItensFirebase;
import com.example.glayce.desafioandroid.R;
import com.example.glayce.desafioandroid.Task.TaskRealizaComunicacao;
import com.example.glayce.desafioandroid.Util.ActivityBase;

import java.io.File;
import java.util.ArrayList;

public class FrmDownloads extends ActivityBase
{
    // controles da classe
    private VideoView videoView = null;
    private RecyclerView rcvDownloads= null;

    // Variaveis da classe
    private ArrayList<ItensFirebase> arrItens = null;
    private AdapterNomeBotao adapterNomeBotao = null;
    private int iPosicaoItemClicado = -1;
    private String sNomeDescricao = "";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        try
        {
            setContentView(R.layout.activity_frm_downloads);
            super.onCreate(savedInstanceState);
        }
        catch (Exception err)
        {
            Toast.makeText(this, "Erro tela FrmDownloads método onCreate: " + err.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void iniciaControles() throws Exception
    {
        // Obtem as referencias do xml
        videoView = (VideoView) findViewById(R.id.videoViewDownloads);
        rcvDownloads = (RecyclerView) findViewById(R.id.rcvDownloads);
    }

    @Override
    public void carregaDados() throws Exception
    {
        // Inicializa o arraylist
        arrItens = new ArrayList<>();

        // Dispara a task
        new TaskRealizaComunicacao(this, this).execute();
    }

    /**
     * Configura o video video e reproduz o download que foi efetuado na memoria interna
     * */
    private void configuraVideo(String sNomeArquivo) throws Exception
    {
        String sCaminho = "";
        String sNomeAux = "";
        File rootFile = null;
        MediaController mediaController = null;

        sCaminho = String.valueOf(Environment.getExternalStorageDirectory());

        // Concatena no nome
        sNomeAux = "video_" + sNomeArquivo + ".mp4";

        rootFile = new File(sCaminho, sNomeAux);

        // Inicia o media controller
        mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        mediaController.setMediaPlayer(videoView);

        // Configura e inicia o video
        videoView.setMediaController(mediaController);
        videoView.setVideoPath(rootFile.toString());
        videoView.start();

        // Deixa o video em loop
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener()
        {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer)
            {
                mediaPlayer.setLooping(true);
            }
        });
    }

    @Override
    public void onReceiveData(Class classe, int iId, boolean bResultado, Object... oObjetos) throws Exception
    {
        String sMensagem = "";

        try
        {
            // Se a classe de retorno for a task de comunicacao
            if( classe == TaskRealizaComunicacao.class )
            {
                // Se o resultado for positivo
                if( bResultado )
                {
                    // Obtem o array com os itens do servidor
                    arrItens = (ArrayList<ItensFirebase>) oObjetos[0];

                    // Preenche a lista do recycler com a descrição e a imagem
                    preencheLista();
                }
                else
                {
                    // Obtem a mensagem de erro e apresenta no toast
                    sMensagem = (String) oObjetos[0];
                    Toast.makeText(this, sMensagem, Toast.LENGTH_LONG).show();
                }
            }
            else if( classe == AdapterNomeBotao.ViewHolder.class )
            {
                // Obtem a posicao do item clicado
                iPosicaoItemClicado = (int) oObjetos[0];

                // Obtem a descricao
                sNomeDescricao = arrItens.get(iPosicaoItemClicado).sNome;

                // Chama o método que configura e carrega o video
                configuraVideo(sNomeDescricao);
            }
        }
        catch (Exception err)
        {
            Toast.makeText(this, "Erro tela FrmDownloads método onReceiveData: " + err.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Preenche a recycler view com o nome e a imagem
     * */
    private void preencheLista() throws Exception
    {
        // Se conter itens no arraylist
        if( arrItens.size() != 0 )
        {
            rcvDownloads.setLayoutManager(new GridLayoutManager(this, 1, LinearLayoutManager.VERTICAL, false));
            rcvDownloads.setHasFixedSize(true);

            //Adiciona no RecyclerView
            adapterNomeBotao = new AdapterNomeBotao(this, this,  arrItens);
            rcvDownloads.setAdapter(adapterNomeBotao);
        }
        else
        {
            Toast.makeText(this, "Não há itens para ser exibido.", Toast.LENGTH_SHORT).show();
        }
    }
}

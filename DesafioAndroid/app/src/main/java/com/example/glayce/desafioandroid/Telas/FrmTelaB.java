package com.example.glayce.desafioandroid.Telas;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.glayce.desafioandroid.Adapter.AdapterNomeBotao;
import com.example.glayce.desafioandroid.Model.ItensFirebase;
import com.example.glayce.desafioandroid.R;
import com.example.glayce.desafioandroid.Task.TaskRealizaComunicacao;
import com.example.glayce.desafioandroid.Task.TaskRealizaDownloadVideo;
import com.example.glayce.desafioandroid.Util.ActivityBase;
import com.example.glayce.desafioandroid.Util.Apoio;

import java.util.ArrayList;

public class FrmTelaB extends ActivityBase implements View.OnClickListener, DialogInterface.OnClickListener
{
    // Variaveis da classe
    private VideoView videoView = null;
    private RecyclerView rcvNomeBotao = null;
    private ImageView imgPrev = null;
    private ImageView imgNext = null;
    private Toolbar toolbar = null;

    // Variaveis da classe
    private ArrayList<ItensFirebase> arrItens = null;
    private AdapterNomeBotao adapterNomeBotao = null;
    private MediaPlayer player = new MediaPlayer();
    private int iPosicaoItemClicado = -1;
    private AlertDialog dialog = null;
    private String sNomeDescricao = "";
    private String sLinkAudio = "";
    private String sLinkVideo = "";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        try
        {
            setContentView(R.layout.frm_tela_b);
            super.onCreate(savedInstanceState);
        }
        catch (Exception err)
        {
            Toast.makeText(this, "Erro tela FrmTelaB método onCreate: " + err.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Inicia os controles da classe
     * */
    public void iniciaControles() throws Exception
    {
        // Obtendo as referencias do xml
        videoView = (VideoView) findViewById(R.id.videoView);
        rcvNomeBotao = (RecyclerView) findViewById(R.id.rcvNomeBotao);
        imgPrev = (ImageView) findViewById(R.id.imgPrev);
        imgNext = (ImageView) findViewById(R.id.imgNext);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(R.drawable.voltar);

        // Define os listeners
        imgPrev.setOnClickListener(this);
        imgNext.setOnClickListener(this);
    }

    /**
     * Carrega os dados da tela
     * */
    public void carregaDados() throws Exception
    {
        // Inicializa o arraylist
        arrItens = new ArrayList<>();

        // Dispara a task
        new TaskRealizaComunicacao(this, this).execute();
    }

    /**
     * Preenche a recycler view com o nome e a imagem
     * */
    private void preencheLista() throws Exception
    {
        // Se conter itens no arraylist
        if( arrItens.size() != 0 )
        {
            rcvNomeBotao.setLayoutManager(new GridLayoutManager(this, 1, LinearLayoutManager.VERTICAL, false));
            rcvNomeBotao.setHasFixedSize(true);

            //Adiciona no RecyclerView
            adapterNomeBotao= new AdapterNomeBotao(this, this,  arrItens);
            rcvNomeBotao.setAdapter(adapterNomeBotao);
        }
        else
        {
            Toast.makeText(this, "Não há itens para ser exibido.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onReceiveData(Class classe, int iId, boolean bResultado, Object... oObjetos) throws Exception
    {
        String sMensagem = "";

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
        // Se for o retorno do adapter
        else if( classe == AdapterNomeBotao.ViewHolder.class )
        {
            // Obtem a posicao do item clicado
            iPosicaoItemClicado = (int) oObjetos[0];

            // Obtem a descricao
            sNomeDescricao = arrItens.get(iPosicaoItemClicado).sNome;
            sLinkAudio = arrItens.get(iPosicaoItemClicado).sAudio;
            sLinkVideo = arrItens.get(iPosicaoItemClicado).sVideo;

            // Abre um dialog para questionar se deseja reproduzir ou fazer o download do video
            dialog = Apoio.abreDialog(this, "Atenção",
                    "Deseja reproduzir o vídeo " + sNomeDescricao + " ou fazer o download?",
                    "Reproduzir",
                    "Download");
            dialog.show();
        }
        // Se a classe de retorno for a task de download
        else if( classe == TaskRealizaDownloadVideo.class )
        {
            // Se o resultado for positivo
            if( bResultado )
            {
                Toast.makeText(this, "Download realizado com sucesso!", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(this, "Falha ao efetuar o download!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onClick(View view)
    {
        try
        {
            // Se clicou na seta de anterior
            if( view == imgPrev )
            {
                // Chama o método que carrega o video anterior
                prevVideo();
            }
            // Se clicou na seta para avançar
            else if( view == imgNext )
            {
                // Chama o método que carrega o proximo video
                nextVideo();
            }
        }
        catch (Exception err)
        {
            Toast.makeText(this, "Erro tela FrmPrincipal método onClick: " + err.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();

        // Pausa o video e o audio
        player.stop();
        videoView.stopPlayback();
        this.finish();
    }

    private void iniciaAudioVideo(String sUriAudio, String sUriVideo) throws Exception
    {
        MediaController mc = new MediaController(this);
        mc.setAnchorView(videoView);
        mc.setMediaPlayer(videoView);

        // Configura e inicia o audio
        player.reset();
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        player.setDataSource(FrmTelaB.this, Uri.parse(sUriAudio));
        player.prepare();
        player.start();

        // Configura e inicia o video
        videoView.setMediaController(mc);
        videoView.setVideoURI(Uri.parse(sUriVideo));
        videoView.start();

        mc.show(player.getDuration());

        // Deixa o video em loop
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener()
        {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer)
            {
                mediaPlayer.setLooping(true);
            }
        });

        // Ao finalizar o audio, para o video e tira do loop
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
        {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer)
            {
                mediaPlayer.setLooping(false);
                videoView.stopPlayback();
            }
        });
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int iEscolha)
    {
        try
        {
            // Se for o dialog de pergunta
            if( dialogInterface == dialog )
            {
                // Se clicou em reproduzir
                if( iEscolha == DialogInterface.BUTTON_POSITIVE )
                {
                    // Chama o método que configura e inicia o audio e video
                    iniciaAudioVideo(sLinkAudio, sLinkVideo);
                }
                // Se clicou em fazer download
                else
                {
                    player.stop();
                    videoView.stopPlayback();

                    // Dispara a task que efetua o download do video
                    new TaskRealizaDownloadVideo(this, this, sLinkVideo, sNomeDescricao).execute();
                }
            }
        }
        catch ( Exception err )
        {
            Toast.makeText(this, "Erro onClick DialogInterface na tela FrmTelaB: " + err.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     *
     * */
    private void prevVideo() throws Exception
    {
        int iPosicaoAux = -1;

        // Obtem a posicao clicada e subtrai 1
        iPosicaoAux = iPosicaoItemClicado - 1;

        // Se a posicao for negativa, apresenta mensagem
        if( iPosicaoAux < 0 )
        {
            Toast.makeText(this, "Não há vídeos anteriores.", Toast.LENGTH_SHORT).show();
        }
        // Se a posicao for maior que o tamanho do array, apresenta mensagem
        else if( iPosicaoAux >= arrItens.size() )
        {
            Toast.makeText(this, "Não há vídeos para avançar.", Toast.LENGTH_SHORT).show();
        }
        else
        {
            // Obtem o item
            sLinkVideo = arrItens.get(iPosicaoAux).sVideo;
            sLinkAudio = arrItens.get(iPosicaoAux).sAudio;
            sNomeDescricao = arrItens.get(iPosicaoAux).sNome;

            // Inicia e configura o audio e o video
            iniciaAudioVideo(sLinkAudio, sLinkVideo);

            Toast.makeText(this, "Reproduzindo vídeo: " + sNomeDescricao, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     *
     * */
    private void nextVideo() throws Exception
    {
        int iPosicaoAux = -1;

        // Obtem a posicao clicada e soma 1
        iPosicaoAux = iPosicaoItemClicado + 1;

        // Se a posicao for negativa, apresenta mensagem
        if( iPosicaoAux < 0 )
        {
            Toast.makeText(this, "Não há vídeos anteriores.", Toast.LENGTH_SHORT).show();
        }
        // Se a posicao for maior que o tamanho do array, apresenta mensagem
        else if( iPosicaoAux >= arrItens.size() )
        {
            Toast.makeText(this, "Não há vídeos para avançar.", Toast.LENGTH_SHORT).show();
        }
        else
        {
            // Obtem o item
            sLinkVideo = arrItens.get(iPosicaoAux).sVideo;
            sLinkAudio = arrItens.get(iPosicaoAux).sAudio;
            sNomeDescricao = arrItens.get(iPosicaoAux).sNome;

            // Inicia e configura o audio e o video
            iniciaAudioVideo(sLinkAudio, sLinkVideo);

            Toast.makeText(this, "Reproduzindo vídeo: " + sNomeDescricao, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onSupportNavigateUp()
    {
        onBackPressed();
        return true;
    }
}

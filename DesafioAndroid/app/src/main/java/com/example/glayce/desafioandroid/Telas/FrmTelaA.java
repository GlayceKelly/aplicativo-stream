package com.example.glayce.desafioandroid.Telas;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.example.glayce.desafioandroid.Adapter.AdapterImagemNome;
import com.example.glayce.desafioandroid.Model.ItensFirebase;
import com.example.glayce.desafioandroid.R;
import com.example.glayce.desafioandroid.Task.TaskRealizaComunicacao;
import com.example.glayce.desafioandroid.Util.ActivityBase;

import java.util.ArrayList;

public class FrmTelaA extends ActivityBase
{
    // Controles da classe
    private RecyclerView rcvItens = null;
    private Toolbar toolbar = null;

    // Variaveis da classe
    private ArrayList<ItensFirebase> arrItens = null;
    private AdapterImagemNome adapterImagemNome = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        try
        {
            setContentView(R.layout.frm_tela_a);
            super.onCreate(savedInstanceState);
        }
        catch (Exception err)
        {
            Toast.makeText(this, "Erro tela FrmTelaA método onCreate: " + err.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Inicia os controles da classe
     * */
    public void iniciaControles() throws Exception
    {
        // Obtem as referencias do xml
        rcvItens = (RecyclerView) findViewById(R.id.rcvImagemNome);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(R.drawable.voltar);
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
                // Obtem a mensagem de erro
                sMensagem = (String) oObjetos[0];

                Toast.makeText(this, sMensagem, Toast.LENGTH_LONG).show();
            }
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
            rcvItens.setLayoutManager(new GridLayoutManager(this, 1, LinearLayoutManager.VERTICAL, false));
            rcvItens.setHasFixedSize(true);

            //Adiciona no RecyclerView
            adapterImagemNome = new AdapterImagemNome(this, arrItens);
            rcvItens.setAdapter(adapterImagemNome);
        }
        else
        {
            Toast.makeText(this, "Não há itens para ser exibido.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onSupportNavigateUp()
    {
        onBackPressed();
        return true;
    }
}

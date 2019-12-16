package com.example.glayce.desafioandroid.Telas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.glayce.desafioandroid.R;

import java.util.ArrayList;

public class FrmPrincipal extends AppCompatActivity implements View.OnClickListener
{
    // Variaveis da classe
    private CardView cardTelaA = null;
    private CardView cardTelaB = null;
    private CardView cardTelaDownloads = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        try
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.frm_principal);

            // Chama os método que iniciam e carregam os controles da classe
            iniciaControles();
            carregaDados();
        }
        catch (Exception err)
        {
            Toast.makeText(this, "Erro tela FrmPrincipal método onCreate: " + err.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Inicia os controles da classe
     * */
    private void iniciaControles() throws Exception
    {
        // Obtendo as referencias do xml
        cardTelaA = (CardView) findViewById(R.id.cardTelaA);
        cardTelaB = (CardView) findViewById(R.id.cardTelaB);
        cardTelaDownloads = (CardView) findViewById(R.id.cardTelaDownloads);

        // Define os listeners
        cardTelaA.setOnClickListener(this);
        cardTelaB.setOnClickListener(this);
        cardTelaDownloads.setOnClickListener(this);
    }

    /**
     * Carrega os dados da tela
     * */
    private void carregaDados() throws Exception
    {
    }

    @Override
    public void onClick(View view)
    {
        try
        {
            // Se clicou para ir para a tela A
            if( view == cardTelaA )
            {
                // Chama o método para abrir a tela A
                chamaTelaA();
            }
            // Se clicou para ir para a tela B
            else if( view == cardTelaB )
            {
                // Chama o método para abrir a tela B
                chamaTelaB();
            }
            // Se clicou para ir para a tela B
            else if( view == cardTelaDownloads )
            {
                // Chama o método para abrir a tela de downloads
                chamaTelaDownloads();
            }
        }
        catch (Exception err)
        {
            Toast.makeText(this, "Erro tela FrmPrincipal método onClick: " + err.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Método para abrir a proxima tela
     * */
    private void chamaTelaA() throws Exception
    {
        Intent intent = null;

        // Inicializa a intent e abre a tela
        intent = new Intent(this, FrmTelaA.class);
        startActivity(intent);
    }

    /**
     * Método para abrir a proxima tela
     * */
    private void chamaTelaB() throws Exception
    {
        Intent intent = null;

        // Inicializa a intent e abre a tela
        intent = new Intent(this, FrmTelaB.class);
        startActivity(intent);
    }

    /**
     * Método para abrir a proxima tela
     * */
    private void chamaTelaDownloads() throws Exception
    {
        Intent intent = null;

        // Inicializa a intent e abre a tela
        intent = new Intent(this, FrmDownloads.class);
        startActivity(intent);
    }
}

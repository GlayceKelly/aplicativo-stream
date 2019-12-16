package com.example.glayce.desafioandroid.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.glayce.desafioandroid.Model.ItensFirebase;
import com.example.glayce.desafioandroid.R;
import com.example.glayce.desafioandroid.Util.ComunicacaoGeral;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Glayce on 12/12/2019.
 */
public class AdapterNomeBotao extends RecyclerView.Adapter<AdapterNomeBotao.ViewHolder>
{
    // Variaveis da classe
    private Context context = null;
    private ArrayList<ItensFirebase> arrItens = null;
    private ComunicacaoGeral comunicacaoGeral = null;
    private int iPosicao = -1;

    // Construtor da classe
    public AdapterNomeBotao(Context contextParam, ComunicacaoGeral comunicacaoGeralParam, ArrayList<ItensFirebase> arrItensParam )
    {
        context = contextParam;
        comunicacaoGeral = comunicacaoGeralParam;
        arrItens = arrItensParam;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lst_nome_botao, null, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int iPosition)
    {
        // Atributos do método
        String sImagem = "";
        String sNome = "";

        // Atribuindo para as variaveis os itens do array
        sImagem = arrItens.get(iPosition).sImagem;
        sNome = arrItens.get(iPosition).sNome;

        // Define a sImagem e a descricao
        Picasso.with(context).load(sImagem).into(holder.imgImagem);
        holder.lblNome.setText(sNome);
    }

    @Override
    public int getItemCount()
    {
        return arrItens.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        // Variaveis da classe
        private ImageView imgImagem = null;
        private TextView lblNome = null;
        private CardView cardBotao = null;

        public ViewHolder(View itemView)
        {
            super(itemView);

            // Obtendo as referencias do xml
            imgImagem = (ImageView) itemView.findViewById(R.id.imgBotao);
            lblNome = (TextView) itemView.findViewById(R.id.lblNomeBotao);
            cardBotao = (CardView) itemView.findViewById(R.id.cardBotao);

            // Define o listener
            cardBotao.setOnClickListener(this);
        }

        @Override
        public void onClick(View view)
        {
            try
            {
                // Se clicou no cardview
                if( view == cardBotao )
                {
                    iPosicao = this.getPosition();

                    //Passa o controle com o resultado para a activity que fez a chamada
                    comunicacaoGeral.comunicaGeral(getClass(), 0, true, iPosicao);
                }
            }
            catch (Exception err)
            {
                Toast.makeText(context, "Erro onClick AdapterNomeBotao: " + err.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}

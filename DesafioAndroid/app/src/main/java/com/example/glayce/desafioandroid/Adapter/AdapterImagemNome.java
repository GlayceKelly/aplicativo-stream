package com.example.glayce.desafioandroid.Adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.glayce.desafioandroid.Model.ItensFirebase;
import com.example.glayce.desafioandroid.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Glayce on 12/12/2019.
 */

public class AdapterImagemNome extends RecyclerView.Adapter<AdapterImagemNome.ViewHolder>
{
    // Variaveis da classe
    private Context context = null;
    private ArrayList<ItensFirebase> arrItens = null;

    // Construtor da classe
    public AdapterImagemNome( Context contextParam, ArrayList<ItensFirebase> arrItensParam )
    {
        context = contextParam;
        arrItens = arrItensParam;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lst_imagens_nome, null, false);
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
        //holder.imgImagem.setImageURI(Uri.parse(sImagem));
        //Glide.with(context).load(sImagem).into(holder.imgImagem);
        Picasso.with(context).load(sImagem).resize(280, 300).into(holder.imgImagem);
        holder.lblNome.setText(sNome);
    }

    @Override
    public int getItemCount()
    {
        return arrItens.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        // Variaveis da classe
        private ImageView imgImagem = null;
        private TextView lblNome = null;

        public ViewHolder(View itemView)
        {
            super(itemView);

            // Obtendo as referencias do xml
            imgImagem = (ImageView) itemView.findViewById(R.id.imgRecebida);
            lblNome = (TextView) itemView.findViewById(R.id.lblNomeRecebido);
        }
    }
}

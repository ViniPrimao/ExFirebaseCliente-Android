package com.example.qi.exfirebasecliente;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by QI on 20/12/2017.
 */

public class ClienteAdapter extends RecyclerView.Adapter {

    private Context context;
    private ArrayList<Cliente> clientes;
    private static ClickListener clickListener;

    private static final String TAG = "logClienteAdapter";

    public ClienteAdapter(Context context,
                          ArrayList<Cliente> clientes) {
        this.context = context;
        this.clientes = clientes;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.linhacliente, parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder hold = (ViewHolder) holder;
        Cliente c = clientes.get(position);

        hold.tvNome.setText(c.getNome());
        hold.tvCidade.setText(c.getCidade());
        hold.tvRenda.setText(String.valueOf(c.getRenda()));
    }

    @Override
    public int getItemCount() {
        return clientes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private final TextView tvNome;
        private final TextView tvCidade;
        private final TextView tvRenda;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            tvNome = itemView.findViewById(R.id.lc_tv_nome);
            tvCidade = itemView.findViewById(R.id.lc_tv_cidade);
            tvRenda = itemView.findViewById(R.id.lc_tv_renda);
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(),v);
        }

        @Override
        public boolean onLongClick(View v) {
            clickListener.onItemLongClick(getAdapterPosition(),v);
            return true;
        }
    }

    public void setOnItemClickListener(ClickListener clickListener){
        ClienteAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
        void onItemLongClick(int position, View v);
    }//fecha ClickListener
}//fecha classe

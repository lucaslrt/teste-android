package br.com.leanwork.testedevandroidlean.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.leanwork.testedevandroidlean.R;
import br.com.leanwork.testedevandroidlean.dao.PessoaDAO;
import br.com.leanwork.testedevandroidlean.model.Pessoa;

public class PessoasAdapter extends RecyclerView.Adapter<PessoasAdapter.PessoaHolder> {

    private List<Pessoa> pessoasList;

    public PessoasAdapter(Context context) {
        PessoaDAO pessoaDAO = new PessoaDAO(context);

        pessoasList = pessoaDAO.getPessoas();
    }

    @NonNull
    @Override
    public PessoasAdapter.PessoaHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_view_pessoa, viewGroup, false);
        return new PessoaHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PessoasAdapter.PessoaHolder viewHolder, int position) {
        Pessoa pessoa = pessoasList.get(position);
        viewHolder.tvNomePessoa.setText(pessoa.getNome());
        viewHolder.tvGeneroPessoa.setText(pessoa.getGenero());
        viewHolder.tvDataNascPessoa.setText(pessoa.getDataNascimento());
        viewHolder.tvTelefonePessoa.setText(pessoa.getTelefone());
    }

    @Override
    public int getItemCount() {
        return pessoasList.size();
    }

    public void setData(List<Pessoa> data) {
        pessoasList.clear();
        pessoasList.addAll(data);
        notifyDataSetChanged();
    }

    static class PessoaHolder extends RecyclerView.ViewHolder {

        private TextView tvNomePessoa, tvGeneroPessoa, tvTelefonePessoa, tvDataNascPessoa;

        PessoaHolder(View itemView) {
            super(itemView);
            tvNomePessoa = itemView.findViewById(R.id.tvNomePessoa);
            tvGeneroPessoa = itemView.findViewById(R.id.tvGeneroPessoa);
            tvDataNascPessoa = itemView.findViewById(R.id.tvDataNascimentoPessoa);
            tvTelefonePessoa = itemView.findViewById(R.id.tvTelefonePessoa);
        }
    }

}

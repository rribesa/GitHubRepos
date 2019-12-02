package br.com.rrs.githubrepos.lista.activity.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import br.com.rrs.githubrepos.R
import br.com.rrs.githubrepos.lista.model.GitRepositorio
import br.com.rrs.githubrepos.lista.viewmodel.ListaGitHubViewModel
import br.com.rrs.githubrepos.lista.viewmodel.states.ListaGitHubInteractor

class RepositoriosListaAdapter(
    private val repositoriosGit: List<GitRepositorio>,
    private val viewModel: ListaGitHubViewModel
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.repositorio_lista_item, parent, false)
        return RepositoriosGitViewHolder(view)
    }

    override fun getItemCount() = repositoriosGit.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position == itemCount - 5) {
            viewModel.interpretar(ListaGitHubInteractor.ChamarProximaPagina())
        }
        (holder as RepositoriosGitViewHolder).bindRepositorioGit(repositoriosGit[position], viewModel)
    }
}

class RepositoriosGitViewHolder(
    itemView: View
) : RecyclerView.ViewHolder(itemView) {
    fun bindRepositorioGit(repositorioGit: GitRepositorio, viewModel: ListaGitHubViewModel) {
        val nomeRepositorioButton: Button = itemView.findViewById(R.id.item_nome_repositorio_bt)
        nomeRepositorioButton.text = repositorioGit.nomeRepositorio
        nomeRepositorioButton.setOnClickListener {
            viewModel.interpretar(
                ListaGitHubInteractor.ClicarNoRepositorio(
                    repositorioGit
                )
            )
        }
    }
}
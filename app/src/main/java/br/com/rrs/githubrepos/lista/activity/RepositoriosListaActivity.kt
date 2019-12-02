package br.com.rrs.githubrepos.lista.activity

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.Group
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.rrs.githubrepos.R
import br.com.rrs.githubrepos.detalhes.GitHubRepositorioDetalhe
import br.com.rrs.githubrepos.lista.activity.adapter.RepositoriosListaAdapter
import br.com.rrs.githubrepos.lista.model.GitRepositorio
import br.com.rrs.githubrepos.lista.viewmodel.ListaGitHubViewModel
import br.com.rrs.githubrepos.lista.viewmodel.states.ListaGitHubEvent
import br.com.rrs.githubrepos.lista.viewmodel.states.ListaGitHubInteractor
import br.com.rrs.githubrepos.lista.viewmodel.states.ListaGitHubStates
import org.koin.androidx.viewmodel.ext.android.viewModel


class RepositoriosListaActivity : AppCompatActivity() {
    private lateinit var listaRepositorios: RecyclerView
    private lateinit var loading: ProgressBar
    private lateinit var tentarNovamenteBotao: Button
    private lateinit var errorText: TextView
    private lateinit var grupoErro: Group

    val gitHubViewModel: ListaGitHubViewModel by viewModel()
    var adapter: RepositoriosListaAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repositorios_lista)
        listaRepositorios = findViewById(R.id.lista_repositorios_rv)
        loading = findViewById(R.id.loading_pb)
        tentarNovamenteBotao = findViewById(R.id.button)
        errorText = findViewById(R.id.textView)
        grupoErro = findViewById(R.id.grupo_error)
        observarStates()
        observarEventos()
    }

    private fun observarStates() {
        gitHubViewModel.viewState.observe(this, Observer { state ->
            state?.let {
                when (it) {
                    is ListaGitHubStates.ListaGitHubSucesso -> atualizaLista(it.lista)
                    is ListaGitHubStates.ListaGitHubError -> exibeErro(it.error)
                }
            }
        })
        gitHubViewModel.inicio()
    }


    private fun observarEventos() {
        gitHubViewModel.viewEvent.observe(this, Observer { event ->
            event?.let {
                when (it) {
                    is ListaGitHubEvent.NavegarDetalhes -> navegarDetalhes(it.navegarDetalhes)
                    is ListaGitHubEvent.ExibeLoading -> exibeLoading(it.loadingVisibility)

                }
            }
        })
    }

    private fun exibeLoading(visibility: Int) {
        gitHubViewModel.resetEvent()
        grupoErro.visibility = View.GONE
        loading.visibility = visibility
    }

    private fun atualizaLista(itens: List<GitRepositorio>) {
        grupoErro.visibility = View.GONE
        loading.visibility = View.GONE
        adapter?.notifyDataSetChanged() ?: preencheLista(itens)
    }

    private fun exibeErro(exception: Exception) {
        loading.visibility = View.GONE
        grupoErro.visibility = View.VISIBLE
        tentarNovamenteBotao.setOnClickListener {
            gitHubViewModel.interpretar(ListaGitHubInteractor.TentarNovamente())
        }
        exception.message?.let {
            errorText.text = it
        }
    }

    private fun preencheLista(itens: List<GitRepositorio>) {
        adapter = RepositoriosListaAdapter(itens, gitHubViewModel)
        listaRepositorios.layoutManager = LinearLayoutManager(this)
        listaRepositorios.adapter = adapter
        listaRepositorios.visibility = View.VISIBLE
        val decoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        listaRepositorios.addItemDecoration(decoration)
    }

    private fun navegarDetalhes(item: GitRepositorio) {
        gitHubViewModel.resetEvent()
        listaRepositorios.visibility = View.GONE
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.main_container, GitHubRepositorioDetalhe.newInstance(item))
        ft.addToBackStack(null)
        ft.commit()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        listaRepositorios.visibility = View.VISIBLE

    }

}

package br.com.rrs.githubrepos.lista.viewmodel

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.rrs.githubrepos.lista.model.GitRepositorio
import br.com.rrs.githubrepos.lista.usecase.ListaGitHubUseCase
import br.com.rrs.githubrepos.lista.viewmodel.states.ListaGitHubEvent
import br.com.rrs.githubrepos.lista.viewmodel.states.ListaGitHubInteractor
import br.com.rrs.githubrepos.lista.viewmodel.states.ListaGitHubStates
import kotlinx.coroutines.*

class ListaGitHubViewModel(private val useCase: ListaGitHubUseCase) : ViewModel() {
    private val state: MutableLiveData<ListaGitHubStates> = MutableLiveData()
    val viewState: LiveData<ListaGitHubStates> = state

    private val event: MutableLiveData<ListaGitHubEvent> = MutableLiveData()
    val viewEvent: LiveData<ListaGitHubEvent> = event

    val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    private var firstTime = true

    fun inicio() {
        if (firstTime) {
            scope.launch {
                event.value = ListaGitHubEvent.ExibeLoading(View.VISIBLE)
                try {
                    state.value = ListaGitHubStates.ListaGitHubSucesso(useCase.listarRepositoriosGitHub())
                    firstTime = false
                } catch (exception: Exception) {
                    state.value = ListaGitHubStates.ListaGitHubError(exception)
                }
            }
        }
    }

    fun interpretar(acao: ListaGitHubInteractor) {
        when (acao) {
            is ListaGitHubInteractor.ChamarProximaPagina -> proximaPaginaRepositorio()
            is ListaGitHubInteractor.ClicarNoRepositorio -> navegaDetalhes(acao.repositorio)
            is ListaGitHubInteractor.TentarNovamente -> this.inicio()
        }
    }

    private fun proximaPaginaRepositorio() {
        scope.launch {
            event.value = ListaGitHubEvent.ExibeLoading(View.VISIBLE)
            try {
                state.value = ListaGitHubStates.ListaGitHubSucesso(useCase.listarProximaPaginaRepositoriosGitHub())
            } catch (exception: Exception) {
                exception.printStackTrace()
                Log.e("Viewmodel", exception.message)
                event.value = ListaGitHubEvent.ExibeLoading(View.GONE)

            }
        }
    }

    private fun navegaDetalhes(repositorioGit: GitRepositorio) {
        event.value = ListaGitHubEvent.NavegarDetalhes(repositorioGit)
    }

    fun resetEvent() {
        event.value = null
    }

    override fun onCleared() {
        super.onCleared()
        job.cancelChildren()
    }

}
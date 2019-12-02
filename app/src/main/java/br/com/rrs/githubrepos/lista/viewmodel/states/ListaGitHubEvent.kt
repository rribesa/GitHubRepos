package br.com.rrs.githubrepos.lista.viewmodel.states

import br.com.rrs.githubrepos.lista.model.GitRepositorio

sealed class ListaGitHubEvent {
    data class NavegarDetalhes(val navegarDetalhes: GitRepositorio) : ListaGitHubEvent()
    data class ExibeLoading(val loadingVisibility: Int) : ListaGitHubEvent()
    data class ExibeInformacaoCache(val loadingVisibility: Int) : ListaGitHubEvent()
}
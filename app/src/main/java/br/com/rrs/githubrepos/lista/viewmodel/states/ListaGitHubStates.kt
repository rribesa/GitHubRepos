package br.com.rrs.githubrepos.lista.viewmodel.states

import br.com.rrs.githubrepos.lista.model.GitRepositorio

sealed class ListaGitHubStates {
    data class ListaGitHubSucesso(val lista: List<GitRepositorio>) : ListaGitHubStates()
    data class ListaGitHubError(val error: Exception) : ListaGitHubStates()
}
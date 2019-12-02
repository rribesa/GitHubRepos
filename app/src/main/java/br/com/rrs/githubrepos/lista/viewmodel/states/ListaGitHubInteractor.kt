package br.com.rrs.githubrepos.lista.viewmodel.states

import br.com.rrs.githubrepos.lista.model.GitRepositorio

sealed class ListaGitHubInteractor {
    class ChamarProximaPagina : ListaGitHubInteractor()
    class TentarNovamente : ListaGitHubInteractor()
    data class ClicarNoRepositorio(val repositorio: GitRepositorio) : ListaGitHubInteractor()
}
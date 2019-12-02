package br.com.rrs.githubrepos.lista.usecase

import br.com.rrs.githubrepos.lista.model.GitRepositorio
import br.com.rrs.githubrepos.lista.repository.ListaRepository

class ListaGitHubUseCase(private val repository: ListaRepository) {
    private var page: Int = 1

    suspend fun listarRepositoriosGitHub() =
        repository.listarRepositorios(this.page)

    suspend fun listarProximaPaginaRepositoriosGitHub(): MutableList<GitRepositorio> {
        page++
        return listarRepositoriosGitHub()
    }


    fun aListaEstaLocal() = repository.dadosCache
}
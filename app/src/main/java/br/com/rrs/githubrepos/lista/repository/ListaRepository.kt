package br.com.rrs.githubrepos.lista.repository

import br.com.rrs.githubrepos.lista.Service
import br.com.rrs.githubrepos.lista.listaDataBase.ListaDataBase
import br.com.rrs.githubrepos.lista.model.GitRepositorio
import br.com.rrs.githubrepos.lista.model.data.entity.GitProprietarioEntity
import br.com.rrs.githubrepos.lista.model.data.entity.GitRepositorioEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ListaRepository(private val service: Service, private val database: ListaDataBase) {
    private val listaGitHubRepos: MutableList<GitRepositorio> = ArrayList()
    var dadosCache = false
    suspend fun listarRepositorios(page: Int) = withContext(Dispatchers.IO) {
        try {
            listaGitHubRepos.addAll(service.getHubRepos(page = page).repositoriosLista)
            salvarCache(listaGitHubRepos)
            return@withContext listaGitHubRepos
        } catch (exception: Exception) {
            if (listarRepositoriosCache().isNotEmpty()) {
                listaGitHubRepos.clear()
                listaGitHubRepos.addAll(listarRepositoriosCache())
                return@withContext listaGitHubRepos
            } else {
                throw exception
            }
        }
    }

    private suspend fun listarRepositoriosCache() = withContext(Dispatchers.IO) {
        dadosCache = true
        return@withContext database.listaDao().getRepositoriosCache()
    }

    private suspend fun salvarCache(repositorio: MutableList<GitRepositorio>) {
        val listEntity: MutableList<GitRepositorioEntity> = ArrayList()
        repositorio.forEach { gitHubRepositorio ->
            val proprietarioEntity = GitProprietarioEntity(
                gitHubRepositorio.proprietarioRepositorio.imagemProprietario,
                gitHubRepositorio.proprietarioRepositorio.idProprietario,
                gitHubRepositorio.proprietarioRepositorio.nomeProprietario
            )
            val repositorioEntity = GitRepositorioEntity(
                gitHubRepositorio.quantidadeForksRepositorio,
                gitHubRepositorio.idRepositorio,
                gitHubRepositorio.nomeRepositorio,
                proprietarioEntity,
                gitHubRepositorio.quantidadeEstrelasRepositorio
            )
            listEntity.add(repositorioEntity)
        }
        database.listaDao().insertAllRepositorio(listEntity)
    }
}
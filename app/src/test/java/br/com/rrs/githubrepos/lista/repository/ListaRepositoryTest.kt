package br.com.rrs.githubrepos.lista.repository

import br.com.rrs.githubrepos.lista.Service
import br.com.rrs.githubrepos.lista.listaDataBase.ListaDataBase
import br.com.rrs.githubrepos.lista.model.data.entity.GitProprietarioEntity
import br.com.rrs.githubrepos.lista.model.data.entity.GitRepositorioEntity
import br.com.rrs.githubrepos.lista.model.data.response.GitListaRepositorioResponse
import br.com.rrs.githubrepos.lista.model.data.response.GitProprietarioResponse
import br.com.rrs.githubrepos.lista.model.data.response.GitRepositorioResponse
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test


class ListaRepositoryTest {

    @MockK
    lateinit var service: Service

    @MockK
    lateinit var serviceError: Service

    @MockK
    lateinit var database: ListaDataBase

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        coEvery { service.getHubRepos(page = 1) } returns mockRetornoService(2)
        coEvery { serviceError.getHubRepos(page = 1) } throws NullPointerException("teste")
        coEvery { database.listaDao().getRepositoriosCache() } returns mockRetornoDataBase(1)
    }

    @Test
    fun `quando chamar a Api retornar sucesso e retornar os dados da api`() {
        val repository = ListaRepository(service, database)
        runBlocking {
            val lista = repository.listarRepositorios(1)
            Assert.assertEquals(2, lista.size)
        }
    }

    @Test
    fun `quando chamar a Api receber erro e retornar os dados do cache`() {
        val repository = ListaRepository(serviceError, database)
        runBlocking {
            val lista = repository.listarRepositorios(1)
            Assert.assertEquals(1, lista.size)
        }
    }

    private fun mockRetornoDataBase(tamanhoLista: Int): MutableList<GitRepositorioEntity> {
        val lista: MutableList<GitRepositorioEntity> = ArrayList()
        for (i in 0 until tamanhoLista) {
            val owner = GitProprietarioEntity("foto", i, "login_teste$i")
            val repositorio = GitRepositorioEntity(5, i, "repositorio_teste$i", owner, 5)
            lista.add(repositorio)
        }
        return lista
    }

    private fun mockRetornoService(tamanhoLista: Int): GitListaRepositorioResponse {
        val lista: MutableList<GitRepositorioResponse> = ArrayList()
        for (i in 0 until tamanhoLista) {
            val owner = GitProprietarioResponse("foto", i, "login_teste$i")
            val repositorio = GitRepositorioResponse(5, i, "repositorio_teste$i", owner, 5)
            lista.add(repositorio)
        }
        return GitListaRepositorioResponse(lista)
    }

}
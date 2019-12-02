package br.com.rrs.githubrepos.lista.usecase

import br.com.rrs.githubrepos.lista.model.GitRepositorio
import br.com.rrs.githubrepos.lista.repository.ListaRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ListaGitHubUseCaseTest {
    @MockK
    lateinit var repository: ListaRepository

    @MockK
    lateinit var repositoryCache: ListaRepository

    @MockK
    lateinit var gitHubResponse: MutableList<GitRepositorio>

    @MockK
    lateinit var gitHubResponse1: MutableList<GitRepositorio>

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        every { gitHubResponse.size } returns 0
        every { gitHubResponse1.size } returns 1
        coEvery { repository.listarRepositorios(page = 1) } returns gitHubResponse
        coEvery { repository.listaRepositoriosProximaPagina(page = 1) } returns gitHubResponse

        every { repositoryCache.dadosCache } returns true
        every { repository.dadosCache } returns false
        coEvery { repository.listarRepositorios(page = 2) } returns gitHubResponse1
        coEvery { repository.listaRepositoriosProximaPagina(page = 2) } returns gitHubResponse1
        coEvery { repositoryCache.listaRepositoriosProximaPagina(page = 2) } returns gitHubResponse1



    }

    @Test
    fun `quando chamar listarRepositorios deve retornar a lista`() {
        val usecase = ListaGitHubUseCase(repository)
        runBlocking {
            val lista = usecase.listarRepositoriosGitHub()
            assertEquals(0, lista.size)
        }
    }

    @Test
    fun `quando chamar listarRepositoriosProximaPagina com o cache falso deve retornar a lista`() {
        val usecase = ListaGitHubUseCase(repository)
        runBlocking {
            val lista = usecase.listarProximaPaginaRepositoriosGitHub()
            assertEquals(1, lista.size)
        }
    }

    @Test
    fun `quando chamar listarRepositoriosProximaPagina com o cache true deve retornar lista`() {
        val usecase = ListaGitHubUseCase(repositoryCache)
        runBlocking {
            val lista = usecase.listarProximaPaginaRepositoriosGitHub()
            assertEquals(1, lista.size)
        }
    }

    @Test
    fun `quando chamar a Lista esta Local com o cache deve Retornar True`() {
        val usecase = ListaGitHubUseCase(repositoryCache)
        Assert.assertTrue(usecase.aListaEstaLocal())
    }

    @Test
    fun `quando chamar a Lista esta Local sem o cache deve Retornar False`() {
        val usecase = ListaGitHubUseCase(repository)
        Assert.assertFalse(usecase.aListaEstaLocal())
    }
}
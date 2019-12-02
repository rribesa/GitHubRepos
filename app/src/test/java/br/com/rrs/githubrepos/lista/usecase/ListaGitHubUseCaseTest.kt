package br.com.rrs.githubrepos.lista.usecase

import br.com.rrs.githubrepos.lista.model.GitRepositorio
import br.com.rrs.githubrepos.lista.repository.ListaRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ListaGitHubUseCaseTest {
    @MockK
    lateinit var repository: ListaRepository

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
        coEvery { repository.listarRepositorios(page = 2) } returns gitHubResponse1

    }

    @Test
    fun listarRepositoriosGitHub() {
        val usecase = ListaGitHubUseCase(repository)
        runBlocking {
            val lista = usecase.listarRepositoriosGitHub()
            assertEquals(0, lista.size)
        }
    }

    @Test
    fun listarProximaPaginaRepositoriosGitHub() {
        val usecase = ListaGitHubUseCase(repository)
        runBlocking {
            val lista = usecase.listarProximaPaginaRepositoriosGitHub()
            assertEquals(1, lista.size)
        }
    }
}
package br.com.rrs.githubrepos.lista.viewmodel

import android.view.View
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.rrs.githubrepos.CoroutineTestRule
import br.com.rrs.githubrepos.lista.model.GitRepositorio
import br.com.rrs.githubrepos.lista.model.data.entity.GitProprietarioEntity
import br.com.rrs.githubrepos.lista.model.data.entity.GitRepositorioEntity
import br.com.rrs.githubrepos.lista.usecase.ListaGitHubUseCase
import br.com.rrs.githubrepos.lista.viewmodel.states.ListaGitHubEvent
import br.com.rrs.githubrepos.lista.viewmodel.states.ListaGitHubInteractor
import br.com.rrs.githubrepos.lista.viewmodel.states.ListaGitHubStates
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ListaGitHubViewModelTest {
    @MockK
    lateinit var useCaseSucesso: ListaGitHubUseCase

    @MockK
    lateinit var useCaseCache: ListaGitHubUseCase
    @MockK
    lateinit var useCaseError: ListaGitHubUseCase
    var exception = java.lang.NullPointerException()
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    @ExperimentalCoroutinesApi
    @get:Rule
    val scope = CoroutineTestRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        coEvery { useCaseSucesso.listarRepositoriosGitHub() } returns mockRetornoUsecase(2)
        coEvery { useCaseSucesso.listarProximaPaginaRepositoriosGitHub() } returns mockRetornoUsecase(4)
        every { useCaseSucesso.aListaEstaLocal() } returns false

        coEvery { useCaseCache.listarRepositoriosGitHub() } returns mockRetornoUsecase(2)
        every { useCaseCache.aListaEstaLocal() } returns true

        coEvery { useCaseError.listarRepositoriosGitHub() } throws exception
    }

    @Test
    fun `quando iniciar a viewmodel deve retornar uma lista com 2 itens`() {
        val viewModel = ListaGitHubViewModel(useCaseSucesso)
        runBlocking {
            viewModel.inicio()
            Assert.assertEquals(ListaGitHubStates.ListaGitHubSucesso(mockRetornoUsecase(2)), viewModel.viewState.value)
            Assert.assertEquals(ListaGitHubEvent.ExibeLoading(View.VISIBLE), viewModel.viewEvent.value)
        }
    }

    @Test
    fun `quando iniciar a viewmodel deve retornar uma lista com cache com 2 itens`() {
        val viewModel = ListaGitHubViewModel(useCaseCache)
        runBlocking {
            viewModel.inicio()
            Assert.assertEquals(ListaGitHubStates.ListaGitHubSucesso(mockRetornoUsecase(2)), viewModel.viewState.value)
            Assert.assertEquals(ListaGitHubEvent.ExibeInformacaoCache(View.VISIBLE), viewModel.viewEvent.value)
        }
    }

    @Test
    fun `quando iniciar a viewmodel deve retornar uma excecao`() {
        val viewModel = ListaGitHubViewModel(useCaseError)
        runBlocking {
            viewModel.inicio()
            Assert.assertEquals(ListaGitHubStates.ListaGitHubError(exception), viewModel.viewState.value)
            Assert.assertEquals(ListaGitHubEvent.ExibeLoading(View.VISIBLE), viewModel.viewEvent.value)
        }
    }

    @Test
    fun `quando entrar com interactor ClicarNoRepositorio deve navegar para detalhes com sucesso`() {
        val viewModel = ListaGitHubViewModel(useCaseSucesso)
        runBlocking {
            viewModel.interpretar(ListaGitHubInteractor.ClicarNoRepositorio(mockRetornoUsecase(1)[0]))
            Assert.assertEquals(ListaGitHubEvent.NavegarDetalhes(mockRetornoUsecase(1)[0]), viewModel.viewEvent.value)
        }
    }

    @Test
    fun `quando entrar com interactor TentarNovamente deve navegar para detalhes com sucesso`() {
        val viewModel = ListaGitHubViewModel(useCaseSucesso)
        runBlocking {
            viewModel.interpretar(ListaGitHubInteractor.TentarNovamente())
            Assert.assertEquals(ListaGitHubStates.ListaGitHubSucesso(mockRetornoUsecase(2)), viewModel.viewState.value)
            Assert.assertEquals(ListaGitHubEvent.ExibeLoading(View.VISIBLE), viewModel.viewEvent.value)
        }
    }

    @Test
    fun `quando entrar com interactor ChamarProximaPagina deve navegar para detalhes com sucesso`() {
        val viewModel = ListaGitHubViewModel(useCaseSucesso)
        runBlocking {
            viewModel.interpretar(ListaGitHubInteractor.ChamarProximaPagina())
            Assert.assertEquals(ListaGitHubStates.ListaGitHubSucesso(mockRetornoUsecase(4)), viewModel.viewState.value)
        }
    }

    @Test
    fun `quando entrar com interactor ChamarProximaPagina com error`() {
        val viewModel = ListaGitHubViewModel(useCaseError)
        runBlocking {
            viewModel.interpretar(ListaGitHubInteractor.ChamarProximaPagina())
        }
    }

    @Test
    fun `quando entrar com interactor ChamarProximaPagina com cache deve devolver o Evento para exibir aviso`() {
        val viewModel = ListaGitHubViewModel(useCaseCache)
        runBlocking {
            viewModel.interpretar(ListaGitHubInteractor.ChamarProximaPagina())
            Assert.assertEquals(ListaGitHubEvent.ExibeInformacaoCache(View.VISIBLE), viewModel.viewEvent.value)
        }
    }

    @Test
    fun `quando resetar o evento o event deve retornar nul`() {
        val viewModel = ListaGitHubViewModel(useCaseSucesso)
        runBlocking {
            viewModel.inicio()
            viewModel.resetEvent()
            Assert.assertNull(viewModel.viewEvent.value)
        }
    }

    private fun mockRetornoUsecase(tamanhoLista: Int): MutableList<GitRepositorio> {
        val lista: MutableList<GitRepositorio> = ArrayList()
        for (i in 0..tamanhoLista) {
            val owner = GitProprietarioEntity("foto", i, "login_teste$i")
            val repositorio = GitRepositorioEntity(5, i, "repositorio_teste$i", owner, 5)
            lista.add(repositorio)
        }
        return lista
    }
}
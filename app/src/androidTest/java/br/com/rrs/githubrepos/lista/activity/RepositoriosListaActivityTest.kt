package br.com.rrs.githubrepos.lista.activity

import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import br.com.rrs.githubrepos.lista.WebClient
import io.mockk.every
import io.mockk.mockkObject
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import utils.Resources.lerArquivo

@LargeTest
class RepositoriosListaActivityTest {

    private lateinit var server: MockWebServer

    @Rule
    var mActivityRule: ActivityTestRule<RepositoriosListaActivity> =
        ActivityTestRule(RepositoriosListaActivity::class.java, false, false)

    @Before
    fun setUp() {
        server = MockWebServer()
        setupServerUrl()
        server.start()
    }

    private fun setupServerUrl() {
        mockkObject(WebClient)
        every { WebClient.retrofit(any()) } returns WebClient.retrofit(
            server.url("/").toString()
        )
    }

    @After
    fun tearDown() {
        server.shutdown()
    }

    @Test
    fun aoAbrirActivityDeveCarregarALista() {
        adicionarRequisicao(200, "")
    }

    fun adicionarRequisicao(statusCode: Int, nomeArquivoResources: String) {
        server.enqueue(
            MockResponse().apply {
                setResponseCode(statusCode)
                setBody(lerArquivo(nomeArquivoResources))
            }
        )
    }
}
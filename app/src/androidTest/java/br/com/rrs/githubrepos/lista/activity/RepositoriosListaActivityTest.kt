package br.com.rrs.githubrepos.lista.activity

import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import br.com.rrs.githubrepos.ListaDataBase
import br.com.rrs.githubrepos.R
import br.com.rrs.githubrepos.WebClient
import io.mockk.every
import io.mockk.mockkObject
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.AfterClass
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import utils.Resources.lerArquivo


@LargeTest
class RepositoriosListaActivityTest {

    private lateinit var dataBase: ListaDataBase

    @get:Rule
    var rule: ActivityTestRule<RepositoriosListaActivity> =
        ActivityTestRule(RepositoriosListaActivity::class.java, false, false)

    @Before
    fun setUp() {
        setupServerUrl()
        dataBase = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().context,
            ListaDataBase::class.java
        ).build()
        runBlocking { dataBase.clearAllTables() }
    }

    private fun setupServerUrl() {
        mockkObject(WebClient)
        every { WebClient.retrofit() } returns WebClient.retrofit(
            server.url("/").toString()
        )
    }


    @Test
    fun aoClicarDeveExibirDetalhes() {
        adicionarRequisicao(200, "listaRepositorio.json")
        rule.launchActivity(Intent())
        onView(withId(R.id.lista_repositorios_rv)).perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(1, click()))
        Thread.sleep(500)
        onView(withId(R.id.imagem_autor)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun aoAbrirAtelasemConexaoSemCacheDeveExibirTelaDeLista() {
        adicionarRequisicao(200, "listaRepositorio.json")
        rule.launchActivity(Intent())
        Thread.sleep(500)
        onView(withId(R.id.lista_repositorios_rv)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun aoAbrirAtelasemConexaoSemCacheDeveExibirTelaDeGErro() {
        adicionarRequisicao(400, "listaRepositorio.json")
        rule.launchActivity(Intent())
        Thread.sleep(500)
        onView(withId(R.id.button)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun aoAbrirAtelasemConexaoComCacheDeveExibirTelaDeErro() {
        adicionarRequisicao(200, "listaRepositorio.json")
        rule.launchActivity(Intent())
        rule.finishActivity()
        adicionarRequisicao(400, "listaRepositorio.json")
        rule.launchActivity(Intent())
        Thread.sleep(500)
        onView(withId(R.id.warning_lista)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }


    fun adicionarRequisicao(statusCode: Int, nomeArquivoResources: String) {
        server.enqueue(
            MockResponse().apply {
                setResponseCode(statusCode)
                setBody(lerArquivo(nomeArquivoResources))
            }
        )
    }

    companion object {
        private var server: MockWebServer = MockWebServer()

        @JvmStatic
        @AfterClass
        fun tearDown() {
            server.shutdown()
            InstrumentationRegistry.getInstrumentation().targetContext.deleteDatabase("GIT_HUB_DATABASE")
        }
    }
}
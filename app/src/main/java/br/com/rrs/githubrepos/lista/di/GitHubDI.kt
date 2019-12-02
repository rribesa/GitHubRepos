package br.com.rrs.githubrepos.lista.di


import androidx.room.Room
import br.com.rrs.githubrepos.ListaDataBase
import br.com.rrs.githubrepos.WebClient
import br.com.rrs.githubrepos.lista.repository.ListaRepository
import br.com.rrs.githubrepos.lista.usecase.ListaGitHubUseCase
import br.com.rrs.githubrepos.lista.viewmodel.ListaGitHubViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

const val DATABASE_NAME = "GIT_HUB_DATABASE"

object GitHubDI {
    val viewModelModule = module {
        viewModel { ListaGitHubViewModel(useCase = get()) }
        factory { ListaGitHubUseCase(repository = get()) }
        factory { ListaRepository(service = get(), database = get()) }
        single { WebClient.service() }
        single {
            Room.databaseBuilder(
                get(),
                ListaDataBase::class.java,
                DATABASE_NAME
            )
                .build()
        }
        single { get<ListaDataBase>().listaDao() }
    }

    fun init() = loadKoinModules(viewModelModule)
}


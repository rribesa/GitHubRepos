package br.com.rrs.githubrepos

import android.app.Application
import br.com.rrs.githubrepos.lista.di.GitHubDI
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class GitHubApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@GitHubApplication)
        }
        GitHubDI.init()
    }
}
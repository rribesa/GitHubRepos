package br.com.rrs.githubrepos.lista.listaDataBase

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.rrs.githubrepos.lista.listaDAO.ListaDAO
import br.com.rrs.githubrepos.lista.model.data.entity.GitRepositorioEntity

@Database(version = 1, entities = arrayOf(GitRepositorioEntity::class), exportSchema = false)
abstract class ListaDataBase : RoomDatabase() {
    abstract fun listaDao(): ListaDAO
}

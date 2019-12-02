package br.com.rrs.githubrepos

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.rrs.githubrepos.lista.model.data.entity.GitRepositorioEntity
import br.com.rrs.githubrepos.lista.repository.database.listaDAO.ListaDAO

@Database(version = 1, entities = [GitRepositorioEntity::class], exportSchema = false)
abstract class ListaDataBase : RoomDatabase() {
    abstract fun listaDao(): ListaDAO
}

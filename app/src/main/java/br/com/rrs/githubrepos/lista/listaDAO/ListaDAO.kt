package br.com.rrs.githubrepos.lista.listaDAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.rrs.githubrepos.lista.model.data.entity.GitRepositorioEntity

@Dao
interface ListaDAO {

    @Query("SELECT * FROM gitHubRepo")
    suspend fun getRepositoriosCache(): MutableList<GitRepositorioEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRepositorio(repositorio: GitRepositorioEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllRepositorio(repositorio: MutableList<GitRepositorioEntity>)

}
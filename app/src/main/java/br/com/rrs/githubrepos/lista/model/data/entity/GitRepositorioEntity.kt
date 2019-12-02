package br.com.rrs.githubrepos.lista.model.data.entity

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.rrs.githubrepos.lista.model.GitRepositorio
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "gitHubRepo")
data class GitRepositorioEntity(
    override val quantidadeForksRepositorio: Int,
    @PrimaryKey
    override val idRepositorio: Int,
    override val nomeRepositorio: String,
    @Embedded
    override val proprietarioRepositorio: GitProprietarioEntity,
    override val quantidadeEstrelasRepositorio: Int
) : Parcelable, GitRepositorio
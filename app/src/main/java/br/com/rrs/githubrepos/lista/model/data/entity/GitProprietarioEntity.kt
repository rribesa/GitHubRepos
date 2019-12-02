package br.com.rrs.githubrepos.lista.model.data.entity

import android.os.Parcelable
import br.com.rrs.githubrepos.lista.model.GitProprietario
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GitProprietarioEntity(
    override val imagemProprietario: String,
    override val idProprietario: Int,
    override val nomeProprietario: String
) : Parcelable, GitProprietario
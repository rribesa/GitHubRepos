package br.com.rrs.githubrepos.lista.model.data.response

import android.os.Parcelable
import br.com.rrs.githubrepos.lista.model.GitRepositorio
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GitRepositorioResponse(
    @SerializedName("forks_count")
    override val quantidadeForksRepositorio: Int,
    @SerializedName("id")
    override val idRepositorio: Int,
    @SerializedName("name")
    override val nomeRepositorio: String,
    @SerializedName("owner")
    override val proprietarioRepositorio: GitProprietarioResponse,
    @SerializedName("stargazers_count")
    override val quantidadeEstrelasRepositorio: Int
) : Parcelable, GitRepositorio
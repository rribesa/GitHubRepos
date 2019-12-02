package br.com.rrs.githubrepos.lista.model.data.response


import android.os.Parcelable
import br.com.rrs.githubrepos.lista.model.GitProprietario
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GitProprietarioResponse(
    @SerializedName("avatar_url")
    override val imagemProprietario: String,
    @SerializedName("id")
    override val idProprietario: Int,
    @SerializedName("login")
    override val nomeProprietario: String
) : Parcelable, GitProprietario
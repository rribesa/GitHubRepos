package br.com.rrs.githubrepos.lista.model.data.response


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GitListaRepositorioResponse(
    @SerializedName("items")
    val repositoriosLista: MutableList<GitRepositorioResponse>
) : Parcelable
package br.com.rrs.githubrepos.lista.model

import android.os.Parcelable

interface GitRepositorio : Parcelable {
    val quantidadeForksRepositorio: Int
    val idRepositorio: Int
    val nomeRepositorio: String
    val proprietarioRepositorio: GitProprietario
    val quantidadeEstrelasRepositorio: Int
}
package br.com.rrs.githubrepos.detalhes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import br.com.rrs.githubrepos.R
import br.com.rrs.githubrepos.lista.model.GitRepositorio
import com.squareup.picasso.Picasso

private const val REPOSITORIO = "repo"

class GitHubRepositorioDetalhe : Fragment() {
    private var repositorio: GitRepositorio? = null
    private lateinit var autorText: TextView
    private lateinit var forkText: TextView
    private lateinit var estrelasText: TextView
    private lateinit var repositorioText: TextView
    private lateinit var imagemAutor: ImageView
    private lateinit var botaoVoltar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            repositorio = it.getParcelable(REPOSITORIO)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_git_hub_repositorio_detalhe, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        autorText = view.findViewById(R.id.autor_text)
        forkText = view.findViewById(R.id.forks_text)
        estrelasText = view.findViewById(R.id.estrelas_text)
        repositorioText = view.findViewById(R.id.repositorio_text)
        imagemAutor = view.findViewById(R.id.imagem_autor)
        botaoVoltar = view.findViewById(R.id.voltar_bt)
        preencheDetalhes()
    }

    private fun preencheDetalhes() {
        botaoVoltar.setOnClickListener { voltarTela() }
        autorText.text = repositorio?.proprietarioRepositorio?.nomeProprietario
        forkText.text = repositorio?.quantidadeForksRepositorio.toString()
        estrelasText.text = repositorio?.quantidadeEstrelasRepositorio.toString()
        repositorioText.text = repositorio?.nomeRepositorio
        Picasso.with(this.context)
            .load(repositorio?.proprietarioRepositorio?.imagemProprietario)
            .resize(160, 160)
            .centerCrop()
            .placeholder(R.mipmap.ic_launcher)
            .into(imagemAutor)
    }

    private fun voltarTela() {
        this.activity?.onBackPressed()
    }

    companion object {
        @JvmStatic
        fun newInstance(repo: GitRepositorio) =
            GitHubRepositorioDetalhe().apply {
                arguments = Bundle().apply {
                    putParcelable(REPOSITORIO, repo)
                }
            }
    }
}

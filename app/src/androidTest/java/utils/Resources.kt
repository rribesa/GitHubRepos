package utils

object Resources {
    fun lerArquivo(nomeArquivoResources: String): String {
        return javaClass
            .classLoader!!
            .getResourceAsStream(nomeArquivoResources)
            .bufferedReader()
            .use { it.readText() }
    }
}
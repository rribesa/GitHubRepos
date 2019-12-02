# GitHubRepos

Este projeto tem a finalidade de estudo, ele consome as apis do github.


## Arquitetura

Estou aplicando o MVVM CLEAN ARCHITETURE


## Libs Utilizadas

retrofit: chamadas de api do github

picasso: renderização da imagem do autor

room: utilizado para armazenar o cache dos repositorios

coroutines: chamadas assyncronas

viewmodel: aplicação da arquitetura e deixar a view reativa

koin: injeção de dependência

junit: testes unitarios

mockk: mocar os objetos dentro dos testes

espresso: TestesInstrumentados



## proximas melhorias
transformar a activity principal em apenas um container e passar a lista para um fragment

aplicar o navigattionComponentes

anunciar via acessibility delegate toda vez que o progressbar aparecer na tela

## Observacoes
Decidi por usar sealedClass com as seguintes definicoes

State: Mapeia os estados da tela, optei por apenas sucesso e Erro

Event: Apenas os eventos que nao devem guardar os estados, optei por Nao criar uma classe de liveEvent e sim por resetar o evento após ele ser emitido pois facilitaria os testes

Interactors: Tem a Funcao de mapear todas as interacoes do Usuario, desta forma a viewmodel expõe apenas dois métodos publicos, o para iniciar e o interpretador


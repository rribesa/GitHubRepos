# GitHubRepos

Este projeto tem a finalidade de estudo, ele consome as APIs do GIT HUB.


## Arquitetura

Estou aplicando o MVVM CLEAN ARCHITETURE


## Libs Utilizadas

retrofit: chamadas de api do github

picasso: renderização da imagem do autor

room: utilizado para armazenar o cache dos repositorios

coroutines: chamadas assyncronas

viewmodel: aplicação da arquitetura e deixar a view reativa

koin: injeção de dependência

junit: testes unitários

mockk: mocar os objetos dentro dos testes

espresso: TestesInstrumentados



## proximas melhorias
transformar a activity principal em apenas um container e passar a lista para um fragment

aplicar o navigattionComponentes

anunciar via acessibility delegate toda vez que o progressbar aparecer na tela

## Observacoes
Optei por colocar botões na recyclerview apenas para facilitar a acessibilidade, permitindo a navegacao por controles em cada item

Decidi por usar sealedClass com as seguintes definições

State: Mapeia os estados da tela, optei por apenas sucesso e Erro

Event: Apenas os eventos que não devem guardar os estados, optei por Não criar uma classe de liveEvent e sim por resetar o evento após ele ser emitido pois facilitaria os testes

Interactors: Tem a Funcao de mapear todas as interações do Usuário, desta forma a viewmodel expõe apenas dois métodos públicos, o para iniciar e o interpretador

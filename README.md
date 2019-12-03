# GitHubRepos

[![CircleCI](https://circleci.com/gh/rribesa/GitHubRepos/tree/master.svg?style=svg)](https://circleci.com/gh/rribesa/GitHubRepos/tree/master)
[![ktlint](https://img.shields.io/badge/code%20style-%E2%9D%A4-FF4081.svg)](https://ktlint.github.io/)

Este projeto tem a finalidade de estudo, ele consome as API do GIT HUB.

## Arquitetura

Estou aplicando o MvvM Clean Architeture


## Libs Utilizadas

retrofit: chamadas de api do github

picasso: renderização da imagem do autor

room: para armazenar o cache dos repositórios no banco de dados local

coroutines: chamadas assyncronas

viewmodel: aplicação da arquitetura e deixar a view reativa

koin: injeção de dependência

junit: testes unitários

mockk: mocar os objetos dentro dos testes

espresso: Testes Instrumentados



## proximas melhorias
transformar a activity principal em apenas um container e passar a lista para um fragment

aplicar o navigattionComponentes

anunciar via acessibility delegate toda vez que o progressbar aparecer na tela

## Observações
Optei por colocar botões na recyclerview apenas para facilitar a acessibilidade, permitindo a navegacao por controles em cada item

Decidi por usar sealedClass com as seguintes definições

State: Mapeia os estados da tela, optei por apenas sucesso e Erro

Event: Apenas os eventos que não devem guardar os estados, optei por Não criar uma classe de liveEvent e sim por resetar o evento após ele ser emitido pois facilitaria os testes

Interactors: Tem a Funcao de mapear todas as interações do Usuário, desta forma a viewmodel expõe apenas dois métodos públicos, o inicio e o interpretador

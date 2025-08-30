# Product Requirements Document (PRD) - Melhor Preço Scraping

## Overview

Este documento descreve os requisitos para uma aplicação que visa ajudar consumidores individuais a encontrar os menores preços para suas listas de compras em supermercados. O problema atual é que os consumidores gastam tempo e esforço pesquisando preços em diferentes estabelecimentos. A solução proposta é uma aplicação baseada em WhatsApp que automatiza esse processo, fornecendo uma lista consolidada dos produtos com os menores preços e seus respectivos supermercados. O valor principal para o usuário é a economia de tempo e dinheiro.

## Goals

O objetivo principal desta aplicação é capacitar os usuários a encontrar os menores preços para suas listas de compras. O sucesso será medido pela economia média gerada por lista de compras para o usuário.

## User Stories

- Como consumidor, quero enviar minha lista de compras em vários formatos (texto, áudio, imagem de lista em papel) via WhatsApp para obter facilmente comparações de preços.
- Como consumidor, quero receber uma lista clara de produtos com o menor preço e o nome do supermercado correspondente para decidir onde comprar.
- Como consumidor, quero assinar mensalmente para receber até 3 cotações de preços para gerenciar meu orçamento de supermercado de forma eficaz.

## Core Features

### 1. Envio de Lista de Compras
- Suporte para entrada de texto via WhatsApp.
- Suporte para entrada de áudio via WhatsApp (requer conversão de fala para texto e processamento por LLM).
- Suporte para entrada de imagem (foto de lista em papel) via WhatsApp (requer OCR e processamento por LLM).
- Integração com uma Large Language Model (LLM), como Firecrawl.dev, para estruturar dados de listas de compras não estruturadas.

### 2. Web Scraping
- Realizar scraping de sites de supermercados em Caraguatatuba, Brasil.
- Foco na extração de nome do produto e preço.
- Utilização de Firecrawl.dev para web scraping.
- Supermercados iniciais a serem suportados: Assai Caraguatatuba, Chibata Caraguatatuba, Semar, Silva Indaia.

### 3. Comparação de Preços e Saída
- Comparar preços de cada item entre os supermercados suportados.
- Identificar o menor preço para cada item.
- Gerar uma lista de saída clara e concisa via WhatsApp, mostrando produto, menor preço e nome do supermercado.

### 4. Gerenciamento de Assinatura
- Mecanismo para assinatura mensal.
- Controle de uso de cotações (até 3 por mês).

## User Experience

A interação será simples e intuitiva, ocorrendo totalmente via WhatsApp. O usuário receberá instruções claras para o envio das listas de compras e a saída será em um formato fácil de ler e compreender.

## High-Level Technical Constraints

- Dependência da API do WhatsApp para comunicação, utilizando a solução open source EvolutionAPI (auto-gerenciada).
- Integração com LLM para processamento de linguagem natural de listas de compras.
- Integração com Firecrawl.dev para web scraping.
- Foco inicial em supermercados de Caraguatatuba.

## Non-Goals (Out of Scope)

- Histórico de preços.
- Alertas de preço.
- Integração direta com aplicativos de supermercado ou programas de fidelidade.
- Suporte para variações de produtos (diferentes marcas, tamanhos para o mesmo item) neste primeiro momento.
- Suporte para regiões fora de Caraguatatuba na fase inicial.

## Phased Rollout Plan

### MVP (Produto Mínimo Viável)
- Integração com WhatsApp para envio de lista de compras baseada em texto.
- Integração básica com LLM para estruturação da lista.
- Web scraping para os supermercados iniciais de Caraguatatuba (Assai, Chibata, Semar, Silva Indaia) usando Firecrawl.dev.
- Saída de produto, menor preço e nome do supermercado.
- Gerenciamento manual de assinatura (inicial).

### Fase 2
- Adicionar suporte para entrada de áudio e imagem para listas de compras.
- Gerenciamento automatizado de assinatura.
- Expandir a cobertura de supermercados dentro de Caraguatatuba.

### Fase 3
- Considerar expansão para outras regiões (se bem-sucedido).

## Success Metrics

- Economia média por lista de compras (a ser definida, ex: meta de 10% de economia).
- Engajamento do usuário: número de assinantes ativos e número de cotações solicitadas.

## Risks and Mitigations

- **Desafios de Scraping:** Mudanças frequentes em sites de supermercados, medidas anti-scraping.
    - Mitigação: Monitoramento regular dos sites, desenvolvimento de lógica de scraping flexível, exploração de fontes de dados alternativas se necessário.
- **Precisão do LLM:** Má interpretação de itens da lista de compras.
    - Mitigação: Implementação de um loop de feedback do usuário para correções, treinamento e ajuste contínuo do modelo.
- **Limitações da API do WhatsApp:** Limites de taxa, mudanças de política da plataforma.
    - Mitigação: Aderir rigorosamente às diretrizes da API, explorar alternativas de comunicação se houver restrições significativas.
- **Adoção do Modelo de Assinatura:** Usuários relutantes em pagar.
    - Mitigação: Proposta de valor clara e convincente, precificação competitiva (R$ 10/mês por 3 listas), oferta de períodos de teste.

## Open Questions

- Detalhes técnicos específicos da integração com a EvolutionAPI.

## Decisões Tomadas Adicionais

- Uma "cotação" é definida como o processamento completo de uma lista de compras.
- **Variações de Produtos:** No MVP, priorizaremos a busca por correspondências exatas. Em caso de ambiguidade ou ausência de correspondência exata, o sistema solicitará clarificação ao usuário. Não haverá substituição automática de produtos genéricos por marcas/tamanhos específicos sem confirmação do usuário.

## Appendix

[A ser preenchido com pesquisas adicionais, mockups, etc., conforme o projeto avança.]
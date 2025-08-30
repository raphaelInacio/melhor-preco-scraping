---
status: pending # Options: pending, in-progress, completed, excluded
---

# Task 2.0: Desenvolvimento do Módulo de Processamento de Lista

## Overview

Desenvolver o módulo responsável por processar as listas de compras fornecidas pelo usuário, utilizando Langchain4j com Gemini para estruturação e Firecrawl.dev como alternativa.

**MUST READ**: Before starting, review the relevant project rules in `docs/ai_guidance/rules/`.

## Requirements

- Processar listas de compras a partir de texto, áudio e imagem.
- Estruturar a lista em um formato padronizado (JSON).
- Lidar com erros de processamento e fornecer feedback ao usuário.

## Subtasks

- [ ] 2.1 Integrar Langchain4j e Gemini para processamento de LLM.
- [ ] 2.2 Implementar lógica para estruturar listas de compras a partir de texto.
- [ ] 2.3 Implementar lógica para estruturar listas de compras a partir de áudio (STT + LLM).
- [ ] 2.4 Implementar lógica para estruturar listas de compras a partir de imagem (OCR + LLM).
- [ ] 2.5 Implementar tratamento de erros e feedback para o usuário em caso de falha no processamento.
- [ ] 2.6 Implementar testes unitários e de integração para o módulo de processamento de lista.

## Implementation Details

Utilizar as APIs do Langchain4j para interagir com o modelo Gemini.
Considerar a integração com Firecrawl.dev para LLM como fallback ou para casos específicos.
Definir o formato JSON para a lista estruturada.

### Relevant Files

- `src/main/java/com/melhorpreco/llm/ShoppingListProcessor.java`
- `src/main/java/com/melhorpreco/llm/InputType.java`
- `src/main/java/com/melhorpreco/llm/StructuredShoppingList.java`

## Success Criteria

- O módulo consegue processar listas de compras em diferentes formatos e estruturá-las corretamente.
- Erros de processamento são tratados e comunicados ao usuário.
- Testes unitários e de integração para o módulo de processamento de lista passam.

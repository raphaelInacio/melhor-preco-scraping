---
status: pending # Options: pending, in-progress, completed, excluded
---

# Task 3.0: Desenvolvimento do Módulo de Web Scraping

## Overview

Desenvolver o módulo responsável por realizar o web scraping nos sites dos supermercados para coletar informações de produtos e preços, utilizando Firecrawl.dev.

**MUST READ**: Before starting, review the relevant project rules in `docs/ai_guidance/rules/`.

## Requirements

- Realizar scraping nos sites dos supermercados definidos (Assai, Chibata, Semar, Silva Indaia).
- Extrair nome do produto e preço.
- Lidar com tratamento de erros para scraping (produto não encontrado, site fora do ar, mudanças de layout).

## Subtasks

- [ ] 3.1 Integrar Firecrawl.dev para web scraping.
- [ ] 3.2 Implementar scraping para Assai Caraguatatuba.
- [ ] 3.3 Implementar scraping para Chibata Caraguatatuba.
- [ ] 3.4 Implementar scraping para Semar.
- [ ] 3.5 Implementar scraping para Silva Indaia.
- [ ] 3.6 Implementar extração de nome do produto e preço.
- [ ] 3.7 Implementar tratamento de erros para scraping (produto não encontrado, site fora do ar, mudanças de layout).
- [ ] 3.8 Implementar testes unitários e de integração para o módulo de web scraping.

## Implementation Details

Utilizar as APIs do Firecrawl.dev para realizar as requisições de scraping.
Definir estratégias para identificar e extrair os dados relevantes de cada site.
Considerar a necessidade de normalização dos nomes dos produtos após o scraping.

### Relevant Files

- `src/main/java/com/melhorpreco/scraping/PriceScraper.java`
- `src/main/java/com/melhorpreco/scraping/ProductPrice.java`
- `src/main/java/com/melhorpreco/scraping/Supermarket.java`

## Success Criteria

- O módulo consegue coletar informações de produtos e preços dos supermercados definidos.
- Erros de scraping são tratados e logados.
- Testes unitários e de integração para o módulo de web scraping passam.

# Technical Specification - Melhor Preço Scraping

## Executive Summary

Este documento detalha a abordagem técnica para o desenvolvimento da aplicação "Melhor Preço Scraping", que visa automatizar a busca pelos menores preços de itens de listas de compras em supermercados via WhatsApp. A solução será construída utilizando Java com Spring Boot, executada em contêineres Docker, e integrará a EvolutionAPI para comunicação com WhatsApp e Firecrawl.dev para processamento de linguagem natural e web scraping. O foco inicial é um MVP que valida a ideia, com escalabilidade e funcionalidades adicionais planejadas para fases futuras.

## System Architecture

### Domain Placement

A arquitetura será modular, com componentes que podem ser mapeados para domínios lógicos. Embora o template sugira subdiretórios específicos, a implementação em Java/Spring Boot seguirá uma estrutura de pacotes que reflete as responsabilidades:

-   **`com.melhorpreco.whatsapp`**: Módulo de integração com WhatsApp (EvolutionAPI).
-   **`com.melhorpreco.llm`**: Módulo de processamento de listas via LLM (Firecrawl.dev).
-   **`com.melhorpreco.scraping`**: Módulo de web scraping (Firecrawl.dev).
-   **`com.melhorpreco.comparison`**: Módulo de comparação de preços e formatação de saída.
-   **`com.mel melhorpreco.subscription`**: Módulo de gerenciamento de assinaturas.
-   **`com.melhorpreco.data`**: Módulo de persistência de dados (repositórios, entidades).
-   **`com.melhorpreco.config`**: Configurações gerais da aplicação.

### Component Overview

-   **WhatsApp Gateway (EvolutionAPI):** Serviço externo responsável pela comunicação com o WhatsApp. Recebe mensagens do usuário e envia respostas.
-   **Backend Application (Java/Spring Boot):** O coração da aplicação, responsável por orquestrar os fluxos de dados, processar as requisições, interagir com Firecrawl.dev e o banco de dados.
    -   **WhatsApp Controller/Service:** Recebe webhooks da EvolutionAPI, invoca o processamento da lista.
    -   **List Processing Service:** Envia a lista bruta para Firecrawl.dev (LLM), recebe a lista estruturada.
    -   **Scraping Service:** Envia itens da lista estruturada para Firecrawl.dev (Scraping), recebe dados de preços.
    -   **Price Comparison Service:** Compara preços, aplica lógica de correspondência exata, formata o resultado.
    -   **Subscription Service:** Gerencia o uso de cotações por usuário.
    -   **Data Access Layer:** Interage com o banco de dados PostgreSQL.
-   **Firecrawl.dev:** Serviço externo para web scraping. Para processamento de linguagem natural (LLM), utilizaremos Langchain4j com o modelo Gemini, ou Firecrawl.dev como alternativa.
-   **PostgreSQL Database:** Armazena dados de usuários, listas de compras, resultados de cotações e informações de assinatura.
-   **Redis:** Utilizado para cache e gerenciamento de sessões (se necessário pela EvolutionAPI ou para otimização interna).

## Implementation Design

### Core Interfaces

Exemplos de interfaces no Spring Boot:

```java
// Interface para o serviço de processamento de listas
public interface ShoppingListProcessor {
    StructuredShoppingList processRawList(String rawListInput, InputType type);
}

// Interface para o serviço de scraping
public interface PriceScraper {
    List<ProductPrice> scrapePrices(String productName, String supermarket);
}

// Interface para o serviço de comparação de preços
public interface PriceComparator {
    ComparisonResult compare(StructuredShoppingList list);
}

// Interface para o serviço de assinatura
public interface SubscriptionService {
    boolean hasAvailableQuote(String userId);
    void decrementQuote(String userId);
}
```

### Data Models

-   **User:** `id`, `whatsappNumber`, `subscriptionStatus`, `quotesRemaining`.
-   **ShoppingList:** `id`, `userId`, `rawInput`, `structuredJson`, `timestamp`.
-   **ShoppingListItem:** `id`, `listId`, `productName`, `quantity`.
-   **QuotationResult:** `id`, `listId`, `productId`, `supermarketName`, `price`, `timestamp`.
-   **Product:** `id`, `name`, `normalizedName`. (Para auxiliar na correspondência exata)

### API Endpoints

-   **EvolutionAPI Webhook:** `POST /webhook/whatsapp` (recebe mensagens do WhatsApp).
-   **Internal Backend Endpoints:**
    -   `POST /api/v1/lists/process` (para processar listas, invocado internamente).
    -   `GET /api/v1/products/compare` (para comparar produtos, invocado internamente).
    -   `POST /api/v1/subscription/check` (para verificar cotações, invocado internamente).

## Integration Points

-   **EvolutionAPI:**
    -   **Tipo:** API REST (Webhooks para entrada, requisições HTTP para saída).
    -   **Autenticação:** API Key.
    -   **Tratamento de Erros:** Monitoramento de status HTTP, retentativas para erros transitórios, logging de falhas.
-   **Firecrawl.dev:**
    -   **Tipo:** API REST.
    -   **Autenticação:** API Key.
    -   **Tratamento de Erros:** Monitoramento de status HTTP, retentativas, tratamento de limites de requisição, logging de falhas de processamento/scraping.

## Impact Analysis

| Affected Component | Type of Impact | Description & Risk Level | Required Action |
| :----------------- | :------------- | :----------------------- | :-------------- |
| Backend Application | New Service | Criação de uma nova aplicação Spring Boot. | Desenvolvimento completo |
| PostgreSQL DB | New Schema | Criação de novas tabelas para dados do sistema. | Definição de schema, migrações |
| EvolutionAPI | Configuração | Configuração de webhooks e instâncias. | Configuração inicial |
| Firecrawl.dev | Uso de API | Consumo das APIs de LLM e Scraping. | Gerenciamento de chaves, monitoramento de uso |

## Testing Approach

### Unit Tests

-   **Estratégia:** Testes unitários para cada classe de serviço e componente lógico, utilizando JUnit e Mockito.
-   **Requisitos de Mock:** Mock de dependências externas (EvolutionAPI, Firecrawl.dev) e camadas de acesso a dados.
-   **Cenários Críticos:** Testar processamento de diferentes formatos de lista, lógica de comparação de preços, gerenciamento de cotações.

### Integration Tests

-   **Estratégia:** Testes de integração para verificar a comunicação entre os módulos internos e com serviços externos (EvolutionAPI, Firecrawl.dev) em um ambiente controlado.
-   **Componentes a Testar:** Backend com PostgreSQL, Backend com EvolutionAPI (simulada), Backend com Firecrawl.dev (simulado).
-   **Dados de Teste:** Massa de dados representativa para listas de compras e resultados de scraping.

## Development Sequencing

### Build Order

1.  **Configuração da Infraestrutura Básica:** Docker Compose para PostgreSQL e Redis.
2.  **Módulo de Processamento de Lista (Backend + Firecrawl.dev LLM):** Integração com Firecrawl.dev para estruturar listas.
3.  **Módulo de Web Scraping (Backend + Firecrawl.dev Scraping):** Integração com Firecrawl.dev para coletar preços.
4.  **Módulo de Comparação de Preços e Saída:** Lógica de comparação e formatação da resposta final.
5.  **Módulo de Gerenciamento de Assinaturas:** Controle de cotações.
6.  **Módulo de Integração WhatsApp (Backend):** Recebimento de webhooks e envio de mensagens básicas.
7.  **Testes e Refinamentos:** Implementação de testes e otimizações.

### Technical Dependencies

-   Docker e Docker Compose instalados e configurados.
-   Acesso e chaves de API para EvolutionAPI e Firecrawl.dev.
-   Instância de PostgreSQL e Redis em execução.

## Monitoring & Observability

-   **Logging:** Utilização de SLF4J com Logback para logging estruturado. Níveis de log (INFO, DEBUG, WARN, ERROR) serão usados apropriadamente.
-   **Métricas:** Spring Boot Actuator será configurado para expor métricas básicas de saúde da aplicação, uso de recursos (CPU, memória) e contadores de requisições.
-   **Alertas:** Configuração de alertas básicos para erros críticos (ex: falha na integração com APIs externas, erros de banco de dados).

## Technical Considerations

### Key Decisions

-   **Linguagem/Framework:** Java com Spring Boot para o backend, devido à robustez, ecossistema e familiaridade.
-   **Containerização:** Toda a aplicação será executada em contêineres Docker para portabilidade e isolamento.
-   **Persistência de Dados:** PostgreSQL para o banco de dados principal. Listas e cotações serão armazenadas por um período limitado no MVP.
-   **Integração WhatsApp:** EvolutionAPI como solução auto-gerenciada.
-   **LLM/Scraping:** Firecrawl.dev será utilizado para web scraping. Para o processamento de linguagem natural (LLM), utilizaremos Langchain4j com o modelo Gemini, com Firecrawl.dev como alternativa para o LLM, visando flexibilidade e otimização.
-   **Variações de Produtos:** Foco em correspondência exata no MVP, com clarificação ao usuário para ambiguidades.
-   **Gateway de Pagamento:** Excluído do MVP para validação da ideia.

### Known Risks

-   **Instabilidade de Scraping:** Mudanças frequentes nos layouts dos sites de supermercados podem quebrar os scrapers.
    -   **Mitigação:** Monitoramento contínuo dos scrapers, implementação de lógica de parsing flexível, e uso de um serviço como Firecrawl.dev que pode ter mecanismos de resiliência.
-   **Precisão do LLM:** O LLM pode interpretar incorretamente itens da lista de compras.
    -   **Mitigação:** Implementação de um ciclo de feedback do usuário para correções e refinamento contínuo do modelo/prompts.
-   **Limitações da EvolutionAPI/WhatsApp:** Possíveis limites de taxa ou mudanças nas políticas do WhatsApp.
    -   **Mitigação:** Aderir às melhores práticas da API, monitorar o uso e estar preparado para adaptar a estratégia de comunicação.
-   **Escalabilidade:** O volume de requisições pode crescer rapidamente, exigindo otimizações.
    -   **Mitigação:** Arquitetura modular para facilitar a escalabilidade horizontal de componentes, monitoramento de desempenho.

### Special Requirements

-   **Performance:** Latência aceitável para a resposta da cotação via WhatsApp (alvo: menos de 60 segundos para listas pequenas).
-   **Segurança:** Gerenciamento seguro de chaves de API, proteção de dados sensíveis do usuário, conformidade com LGPD (Lei Geral de Proteção de Dados).

### Standards Compliance

-   Aderência aos princípios de Clean Architecture e SOLID (quando aplicável ao Spring Boot).
-   Uso de padrões de codificação Java/Spring Boot.
-   Implementação de tratamento de erros robusto em todas as camadas.
-   Utilização de bibliotecas e frameworks padrão do ecossistema Spring.

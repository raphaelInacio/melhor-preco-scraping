---
status: pending # Options: pending, in-progress, completed, excluded
---

# Task 1.0: Configuração da Infraestrutura Básica

## Overview

Configurar os serviços de banco de dados (PostgreSQL) e cache (Redis) utilizando Docker Compose para ambiente de desenvolvimento local.

**MUST READ**: Before starting, review the relevant project rules in `docs/ai_guidance/rules/`.

## Requirements

- PostgreSQL deve estar acessível pela aplicação backend.
- Redis deve estar acessível pela aplicação backend.
- Configurações devem ser parametrizáveis via variáveis de ambiente.

## Subtasks

- [ ] 1.1 Criar arquivo `docker-compose.yml` para PostgreSQL.
- [ ] 1.2 Criar arquivo `docker-compose.yml` para Redis.
- [ ] 1.3 Configurar variáveis de ambiente para conexão com PostgreSQL no backend.
- [ ] 1.4 Configurar variáveis de ambiente para conexão com Redis no backend.
- [ ] 1.5 Implementar testes unitários e de integração para a conexão com o banco de dados e Redis.

## Implementation Details

Referenciar a documentação da Evolution API para configuração de PostgreSQL e Redis com Docker Compose.
Utilizar as configurações padrão do Spring Boot para conexão com PostgreSQL e Redis.

### Relevant Files

- `docker-compose.yml`
- `src/main/resources/application.properties` (ou `application.yml`)

## Success Criteria

- PostgreSQL e Redis iniciam corretamente via Docker Compose.
- A aplicação backend consegue se conectar a ambos os serviços.
- Testes de conexão com banco de dados e Redis passam.

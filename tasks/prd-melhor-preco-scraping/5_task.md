---
status: pending # Options: pending, in-progress, completed, excluded
---

# Task 5.0: Desenvolvimento do Módulo de Gerenciamento de Assinaturas

## Overview

Desenvolver o módulo responsável por gerenciar as assinaturas dos usuários, incluindo o rastreamento do uso de cotações.

**MUST READ**: Before starting, review the relevant project rules in `docs/ai_guidance/rules/`.

## Requirements

- Implementar modelo de dados para usuários e assinaturas.
- Rastrear o uso de cotações (3 por mês por usuário).
- Gerenciar o status da assinatura (ativa, inativa).
- Fornecer APIs para verificar cotações restantes e decrementar o contador.

## Subtasks

- [ ] 5.1 Implementar modelo de dados para usuários e assinaturas.
- [ ] 5.2 Implementar lógica para rastrear o uso de cotações (3 por mês).
- [ ] 5.3 Implementar lógica para gerenciar o status da assinatura.
- [ ] 5.4 Implementar APIs para verificar cotações restantes e decrementar o contador.
- [ ] 5.5 Implementar testes unitários e de integração para o módulo de gerenciamento de assinaturas.

## Implementation Details

A lógica de contagem de cotações deve ser robusta e considerar o período mensal.
A integração com um gateway de pagamento será excluída do MVP, o gerenciamento inicial pode ser manual ou simulado.

### Relevant Files

- `src/main/java/com/melhorpreco/subscription/SubscriptionService.java`
- `src/main/java/com/melhorpreco/subscription/UserSubscription.java`

## Success Criteria

- O módulo gerencia corretamente as assinaturas e o uso de cotações.
- As APIs de gerenciamento de cotações funcionam conforme o esperado.
- Testes unitários e de integração para o módulo de gerenciamento de assinaturas passam.

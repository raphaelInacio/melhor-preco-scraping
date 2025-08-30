---
status: pending # Options: pending, in-progress, completed, excluded
---

# Task 6.0: Desenvolvimento do Módulo de Integração WhatsApp

## Overview

Desenvolver o módulo responsável pela comunicação com o WhatsApp via EvolutionAPI, incluindo o recebimento de mensagens e o envio de respostas.

**MUST READ**: Before starting, review the relevant project rules in `docs/ai_guidance/rules/`.

## Requirements

- Configurar a EvolutionAPI para comunicação com o WhatsApp.
- Implementar webhooks para receber mensagens do WhatsApp.
- Implementar envio de mensagens de resposta via EvolutionAPI.
- Lidar com diferentes tipos de mensagens (texto, áudio, imagem).

## Subtasks

- [ ] 6.1 Configurar EvolutionAPI (instalação e credenciais).
- [ ] 6.2 Implementar endpoint de webhook para receber mensagens do WhatsApp.
- [ ] 6.3 Implementar parsing de mensagens recebidas (texto, áudio, imagem).
- [ ] 6.4 Implementar lógica para invocar o processamento da lista de compras.
- [ ] 6.5 Implementar envio de mensagens de texto via EvolutionAPI.
- [ ] 6.6 Implementar envio de mensagens de mídia (se necessário para feedback).
- [ ] 6.7 Implementar tratamento de erros para comunicação com EvolutionAPI.
- [ ] 6.8 Implementar testes unitários e de integração para o módulo de integração WhatsApp.

## Implementation Details

Utilizar as APIs da EvolutionAPI para interagir com o WhatsApp.
Garantir a segurança do webhook.

### Relevant Files

- `src/main/java/com/melhorpreco/whatsapp/WhatsappService.java`
- `src/main/java/com/melhorpreco/whatsapp/WebhookController.java`

## Success Criteria

- A aplicação consegue receber e enviar mensagens via WhatsApp.
- O webhook funciona corretamente.
- Testes unitários e de integração para o módulo de integração WhatsApp passam.

# integrationhubspot

Projeto de integração backend com HubSpot utilizando Spring Boot, desenvolvido para um case técnico de entrevista. O objetivo é demonstrar domínio em OAuth 2.0, integração com APIs externas, boas práticas de código, segurança e clareza na documentação.

---

## Visão Geral

Esta aplicação expõe uma API REST para:
- Autenticação OAuth 2.0 com o HubSpot (Authorization Code Flow)
- Criação e listagem de contatos no CRM do HubSpot
- Recebimento de notificações via Webhook

O projeto foi estruturado para ser didático, seguro e facilmente extensível para produção.

---

## Diferenciais do Projeto
- **Separação clara de responsabilidades** (Controller, Service, Model)
- **Tratamento robusto de erros** e mensagens amigáveis
- **Código limpo e comentado**
- **Exemplos práticos de uso** (Postman/cURL)
- **Pronto para deploy em cloud** (basta configurar variáveis de ambiente)
- **README completo e didático**

---

## Como rodar o projeto

### 1. Pré-requisitos
- Java 17+
- Maven 3.8+

### 2. Configuração
- Edite o arquivo `src/main/resources/application.properties` com as credenciais do seu app HubSpot:
  ```properties
  hubspot.client-id=SEU_CLIENT_ID
  hubspot.client-secret=SEU_CLIENT_SECRET
  hubspot.redirect-uri=http://localhost:8080/oauth/callback
  ```
- No painel do app HubSpot, cadastre exatamente a URL de redirecionamento acima.

### 3. Executando
```bash
./mvnw spring-boot:run
```

---

## Passo a passo para testar a aplicação

1. **Inicie a aplicação**
   - Execute `./mvnw spring-boot:run` na raiz do projeto.

2. **Obtenha a URL de autorização OAuth**
   - Acesse `http://localhost:8080/oauth/authorize-url` no navegador.
   - Copie a URL exibida e cole no navegador.

3. **Autorize o app no HubSpot**
   - Faça login e autorize o app.
   - Você será redirecionado para `http://localhost:8080/oauth/callback?code=...` e verá uma mensagem de sucesso.

4. **Teste a criação de contatos**
   - No Postman, importe a coleção `postman/hubspot.postman_collection.json`.
   - Selecione a requisição `POST http://localhost:8080/contacts`.
   - No body, envie:
     ```json
     {
       "firstName": "João",
       "lastName": "Silva",
       "email": "joao.silva@email.com"
     }
     ```
   - Clique em **Send**. Você verá a resposta com os dados do contato criado.

5. **Teste a listagem de contatos**
   - No Postman, selecione a requisição `GET http://localhost:8080/contacts`.
   - Clique em **Send**. Você verá a lista de contatos cadastrados no HubSpot.
---

## Fluxo de uso detalhado

1. **Inicie a aplicação**
2. **Acesse `/oauth/authorize-url`**
   - Retorna a URL para autorizar o app no HubSpot
3. **Autorize o app no HubSpot**
   - Você será redirecionado para `/oauth/callback?code=...`
   - O token será salvo em memória
4. **Crie contatos via `/contacts` (POST)**
5. **Liste contatos via `/contacts` (GET)**
6. **Receba webhooks em `/webhook/contact-creation`**

---

## Endpoints principais

### 1. Geração da URL de autorização OAuth
- **GET** `/oauth/authorize-url`
- **Descrição:** Retorna a URL para iniciar o fluxo OAuth no HubSpot.
- **Exemplo de resposta:**
  ```
  https://app.hubspot.com/oauth/authorize?client_id=...&redirect_uri=...&scope=...
  ```

### 2. Callback OAuth
- **GET** `/oauth/callback?code=...`
- **Descrição:** Recebe o código do HubSpot, troca pelo token e armazena em memória.
- **Resposta de sucesso:**
  ```
  Autenticação realizada com sucesso! Token: {"access_token":"...", ...}
  ```

### 3. Criação de Contatos
- **POST** `/contacts`
- **Descrição:** Cria um contato no HubSpot.
- **Body (JSON):**
  ```json
  {
    "firstName": "João",
    "lastName": "Silva",
    "email": "joao.silva@email.com"
  }
  ```
- **Exemplo cURL:**
  ```bash
  curl -X POST http://localhost:8080/contacts \
    -H "Content-Type: application/json" \
    -d '{"firstName":"João","lastName":"Silva","email":"joao.silva@email.com"}'
  ```
- **Exemplo de resposta:**
  ```json
  {
    "id": "117480891295",
    "properties": {
      "firstname": "João",
      "lastname": "Silva",
      "email": "joao.silva@email.com",
      ...
    }
  }
  ```

### 4. Listagem de Contatos
- **GET** `/contacts`
- **Descrição:** Lista todos os contatos do HubSpot.
- **Exemplo cURL:**
  ```bash
  curl http://localhost:8080/contacts
  ```
- **Exemplo de resposta:**
  ```json
  {
    "results": [
      { "id": "...", "properties": { ... } },
      ...
    ]
  }
  ```

### 5. Recebimento de Webhook para Criação de Contatos
- **POST** `/webhook/contact-creation`
- **Descrição:** Endpoint para receber notificações de criação de contatos do HubSpot (webhook).
- **Body:** Recebe o payload enviado pelo HubSpot.
---

## Exemplo de uso com Postman

1. Importe a coleção `postman/hubspot.postman_collection.json` no Postman.
2. Teste os endpoints de criação e listagem de contatos facilmente.
3. Edite os bodies conforme necessário para outros testes.

---

## Troubleshooting (Dúvidas e problemas comuns)

- **401 Unauthorized ao criar contato:**
  - Refazer o fluxo OAuth (token pode ter expirado)
  - Verifique se o backend está salvando apenas o `access_token` do JSON
- **Erro de redirect URI:**
  - Verifique se a URL cadastrada no HubSpot é idêntica à do `application.properties`
- **Conta não tem acesso aos escopos:**
  - Use uma conta de desenvolvedor ou ajuste os escopos do app

---

## Sugestões de melhorias para produção
- Armazenar tokens de forma segura (ex: banco de dados ou serviço de secrets)
- Implementar refresh automático do token OAuth
- Adicionar autenticação/autorização nos endpoints da API
- Validar assinatura dos webhooks do HubSpot
- Adicionar testes automatizados (unitários e integração)
- Deploy em cloud (Heroku, AWS, GCP, etc)

---

## Estrutura do projeto

- `controller/` - Endpoints REST
- `service/` - Lógica de negócio e integração com HubSpot
- `model/` - DTOs e entidades
- `config/` - Configurações
- `postman/` - Coleção de testes para Postman

---

## Contato
Dúvidas, sugestões ou feedback? Fique à vontade para entrar em contato! 

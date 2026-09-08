# Restaurante — API REST

API de gestão de restaurante: cadastro de **clientes**, **pratos** (cardápio) e **managers**,
e o fluxo de **pedidos**. O cliente escolhe prato e quantidade; o servidor valida
disponibilidade, calcula o total e registra o pedido. A administração (CRUD de pratos,
gestão de pedidos, managers) é protegida por **JWT**.

## Stack

Java 25 · Spring Boot 4 · Spring Web · Spring Data JPA / Hibernate · Spring Security 7 ·
JWT (jjwt) · Bean Validation · PostgreSQL · Maven · JUnit 5 + H2 (testes)

## Arquitetura

```
controller  →  service  →  repository  →  banco
   (HTTP)      (regra +      (Spring
                transação)    Data JPA)
```

- **DTOs** (`dto/`) na borda HTTP — a API nunca expõe a entidade JPA (evita vazar `password`
  e impede o cliente de setar campos internos como `id` ou `valorTotal`).
- **Erros** centralizados em `exception/ApiExceptionHandler` (`@RestControllerAdvice`):
  404 para recurso inexistente, 400 para validação, 401 para credencial inválida,
  409 para conflito de unicidade — sempre com corpo JSON padronizado (`ApiError`).

## Como rodar

**1. Banco (PostgreSQL).** Com Docker:

```bash
docker run --name restaurante-db -e POSTGRES_DB=restaurante \
  -e POSTGRES_PASSWORD=postgres -p 5432:5432 -d postgres:17
```

**2. Configuração.** Os defaults em `src/main/resources/application.properties` já apontam
para esse banco local. Para produção, defina as variáveis de ambiente (ver `.env.example`):
`DB_URL`, `DB_USERNAME`, `DB_PASSWORD`, `JWT_SECRET` (≥ 32 caracteres), `ADMIN_PASSWORD`.

**3. Subir a aplicação:**

```bash
./mvnw spring-boot:run
```

Na primeira execução, se não houver nenhum manager, é criado um **`admin`** com a senha de
`ADMIN_PASSWORD` (padrão `admin12345`) — ver `DataInitializer`.

## Autenticação

1. `POST /auth/login` com `{ "username": "admin", "password": "admin12345" }` → devolve um JWT.
2. Nas rotas protegidas, enviar o header `Authorization: Bearer <token>`.

O token é **stateless** (HS256, nada guardado no servidor), válido por
`jwt.expiration-minutes` (padrão 60).

## Endpoints

| Método | Rota | Auth | Descrição |
|---|---|---|---|
| POST | `/auth/login` | pública | Login do manager → JWT |
| POST | `/clientes` | pública | Cliente se cadastra |
| GET/PUT/DELETE | `/clientes`, `/clientes/{id}` | manager | Gestão de clientes |
| GET | `/pratos`, `/pratos/{id}` | pública | Cardápio |
| POST/PUT/DELETE | `/pratos`, `/pratos/{id}` | manager | Gestão do cardápio |
| POST | `/pedidos` | pública | Cliente faz um pedido (`{clienteId, pratoId, quantidade}`) |
| GET | `/pedidos/cliente/{clienteId}` | pública | Pedidos de um cliente |
| PATCH | `/pedidos/{id}/cancelar` | pública | Cliente cancela o próprio pedido |
| GET | `/pedidos`, `/pedidos/{id}` | manager | Ver todos os pedidos |
| PATCH | `/pedidos/{id}/status` | manager | Avançar status (`{ "status": "EM_PREPARO" }`) |
| DELETE | `/pedidos/{id}` | manager | Remover pedido |
| GET/POST/PUT/DELETE | `/managers`, `/managers/{id}` | manager | Gestão de managers |

## Testes

```bash
./mvnw test
```

Rodam contra **H2 em memória** (não precisa de Postgres): lógica de pedido em
`PedidoServiceTest` e o percurso HTTP de autenticação em `AuthFlowTest`.

## Decisões e limitações

- **Um papel só (`ROLE_MANAGER`).** O cliente final não tem login: criar cliente, fazer e
  cancelar pedido são rotas públicas. Um fluxo de autenticação de cliente seguiria o mesmo
  padrão do manager.
- **Sem refresh token.** Token único de curta duração — simples de propósito.
- `ddl-auto=update` (Hibernate cria/atualiza o schema). Em produção o certo é usar
  migrations versionadas (Flyway/Liquibase).

# Refatoração — 2026-09-08

Uma passada de limpeza + camada de segurança. Cada item abaixo é uma mudança que dá para
explicar em uma frase — use este arquivo para revisar antes do `git push` e para
entender/defender o que mudou.

---

## 1. Bugs que impediam o projeto de rodar

| O que estava errado | Correção |
|---|---|
| `private XService service;` declarado nos controllers **sem `@Autowired`** → `service` era `null` → NullPointerException em todo POST | Injeção por construtor (`private final XService service; public XController(XService s){...}`) |
| Services faziam `repository.existsById(model.getId())` num registro novo cujo `id` ainda é `null` (`@GeneratedValue`) → `IllegalArgumentException` | No cadastro, apenas `save()`. Unicidade agora é checada por campo de negócio (`existsByUsername`) |
| `ManagerResponse` colocava a **senha** no campo `username` da resposta | Response DTO sem `password`; mapeamento correto (`ManagerResponse.of`) |
| Entidades `Cliente`/`Prato`/`Manager` **sem construtor sem-args** → JPA não instancia na leitura | `protected NomeEntidade() {}` em todas |
| `int managerCPF` → 11 dígitos estouram `int` (~2,1 bilhões) | `String cpf`, com validação `@Pattern(regexp = "\\d{11}")` |

## 2. Segurança (as libs já estavam no `pom.xml`, faltava o código)

- `SecurityConfig` — `SecurityFilterChain` stateless, CSRF off, CORS configurado, rotas
  públicas vs. protegidas, entry point que devolve **401** (e não a página de login).
- `JwtService` — gera e valida JWT HS256; segredo vem de `JWT_SECRET` (efêmero se ausente,
  com warning no log).
- `JwtAuthFilter` (`OncePerRequestFilter`) — lê `Authorization: Bearer <token>`, valida,
  popula o `SecurityContext`.
- `ManagerDetailsService` (`UserDetailsService`) — carrega o manager pelo username.
- `AuthController` — `POST /auth/login`: confere a senha com `PasswordEncoder.matches` e
  devolve o token.
- `DataInitializer` — cria o manager `admin` na primeira execução (senão não haveria como
  fazer o primeiro login).
- **Senhas agora com BCrypt** (`PasswordEncoder`), nunca texto puro.

## 3. Estrutura e convenção

- Pacotes minúsculos: `Model`→`model`, `Controller`→`controller`, `Repository`→`repository`,
  `Service`→`service`, `DTOS`→`dto` (+ subpacotes minúsculos).
- Classes em PascalCase: `clienteModel`→`Cliente`, `pratoController`→`PratoController`,
  `clienteRequestDTO`→`ClienteRequest`, etc.
- **DTOs agora são usados de fato.** Os controllers recebem `*Request` (com Bean
  Validation) e devolvem `*Response` — nunca mais a entidade JPA crua no `@RequestBody`.
- Rotas RESTful no plural: `/cliente/addCliente` → `POST /clientes`, etc.
- `RuntimeException` para "não encontrado" (que virava HTTP 500) → `NotFoundException`
  tratada como **404** pelo `ApiExceptionHandler`.
- `PedidoStatus` virou **enum** (era String solta); `valor`/`valorTotal` viraram
  **`BigDecimal`** (era `double` — erro de arredondamento em dinheiro).
- `pratoSaindo` (nome ambíguo) → `disponivel`.
- CORS centralizado no `SecurityConfig` (era `@CrossOrigin("*")` espalhado); imports mortos
  removidos.
- `@ManyToOne` do `Pedido` agora é `LAZY` + `open-in-view=false`; o mapeamento para DTO
  acontece dentro da transação do service.

## 4. Configuração e segredos

- Recriado `src/main/resources/application.properties` (tinha sido apagado junto com o
  commit "Delete API Key") — **só com placeholders `${VAR:default}`, nenhum segredo**.
- `.env.example` com as variáveis esperadas.
- `.gitignore` passou a cobrir `.env` e `application-*-local.properties`.
- `pom.xml`: adicionado `spring-boot-starter-validation`; `jjwt` 0.11.5 → **0.12.6**;
  `h2` + `spring-security-test` em escopo de teste.

## 5. Testes (`./mvnw test`, contra H2, sem precisar de Postgres)

- `PedidoServiceTest` — total calculado no servidor, prato indisponível não gera pedido,
  pedido entregue não pode ser cancelado.
- `AuthFlowTest` — cardápio público responde 200, rota de manager sem token responde 401,
  login devolve token, com token responde 201; senha errada → 401; entrada inválida → 400.
- `RestauranteApplicationTests.contextLoads` continua passando (agora com todo o contexto
  de segurança + JPA).

## 6. Ainda pendente (não feito aqui)

- ⚠️ **A API key antiga continua no histórico do git** (o commit `f606a35` só apagou do
  working tree). **Revogar essa chave** no provedor. Reescrever o histórico é possível
  (`git filter-repo`) mas muda todos os hashes.
- Migrations versionadas (Flyway/Liquibase) no lugar de `ddl-auto=update`.
- Autenticação do cliente final (hoje as rotas de cliente são públicas).

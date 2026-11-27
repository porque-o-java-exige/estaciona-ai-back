# EstacionaAI — Backend

API RESTful do **EstacionaAI**, plataforma de locação e reserva de vagas de garagem particulares. Desenvolvida em **Java 21** com **Spring Boot**, em produção na **Azure App Service** com esteira de CI/CD automatizada via **GitHub Actions**, gerenciamento de segredos no **Azure Key Vault** e banco de dados **MySQL na Aiven**.

---

## Ambiente de Produção (Online)

* **Base URL da API:** `https://estaciona-ai-app-dweefuc2eqckdwfn.brazilsouth-01.azurewebsites.net`
* **Documentação Interativa (Swagger UI):** `https://estaciona-ai-app-dweefuc2eqckdwfn.brazilsouth-01.azurewebsites.net/swagger-ui/index.html`

---

## Arquitetura e Tecnologias

* **Linguagem & Framework:** Java 21, Spring Boot 3, Maven
* **Segurança & Autenticação:** Spring Security, JWT (JSON Web Token) e criptografia BCrypt
* **Persistência de Dados:** Spring Data JPA (Hibernate) + MySQL gerenciado na **Aiven** (conexão via SSL)
* **Cloud & Infraestrutura (Azure):**
  * **Azure App Service (`estaciona-ai-app`):** Hospedagem Linux com Java 21 SE (Embedded Web Server) na região *Brazil South*
  * **Azure Key Vault (`estaciona-ai-kv`):** Armazenamento seguro de credenciais acessado via *Managed Identity*
  * **Azure Blob Storage (`estaciona-ai-images`):** Armazenamento de imagens de perfil e garagens
* **Pagamentos:** Integração com **Mercado Pago**
* **Documentação & Produtividade:** SpringDoc OpenAPI (Swagger UI) e Lombok
* **CI/CD:** GitHub Actions com autenticação federada (OIDC) na Azure

---

## Como rodar o projeto localmente

### Pré-requisitos
* **JDK 21** instalado e configurado
* **IntelliJ IDEA** (com *Annotation Processing* ativado para suporte ao Lombok)
* **Azure CLI** instalado (`az`) com permissão de leitura (`Key Vault Secrets User`) no cofre `estaciona-ai-kv`

### Passo a passo
1. Clone este repositório:
   ```bash
   git clone [https://github.com/porque-o-java-exige/estaciona-ai-back.git](https://github.com/porque-o-java-exige/estaciona-ai-back.git)
   ```
2. Abra a pasta do projeto no **IntelliJ IDEA** e aguarde o Maven baixar as dependências do `pom.xml`.
3. Autentique-se na Azure pelo terminal para que o Spring Boot carregue automaticamente as credenciais do **Azure Key Vault** (`db-url`, `db-username`, `db-password`, `jwt-secret`, `azure-storage-connection-string` e `mercadopago-access-token`):
   ```bash
   az login
   ```
4. Execute a classe principal `Application.java`.
5. Acesse a documentação interativa local no navegador:
   ```text
   http://localhost:8080/swagger-ui/index.html
   ```

---

## Deploy, CI/CD e Integração Mobile

### Esteira Automatizada (GitHub Actions + Azure)
* Qualquer `push` na branch **`main`** dispara o workflow `.github/workflows/main_estaciona-ai-app.yml`.
* O pipeline compila o projeto (`mvn clean package -DskipTests`), autentica na Azure via **OIDC** (`azure/login@v2`) e publica o `.jar` diretamente no Web App **`estaciona-ai-app`**.
* Em produção, o Web App utiliza **System Assigned Managed Identity** para ler os segredos do `estaciona-ai-kv` sem expor credenciais no código ou variáveis de ambiente.

### Monitoramento de Logs em Produção
Para acompanhar o console do Spring Boot em tempo real via terminal:
```bash
az webapp log tail --name estaciona-ai-app --resource-group estaciona-ai-rg
```

### Consumo no App Mobile (React Native + Expo)
O frontend mobile consome a API hospedada na Azure através da variável de ambiente configurada no `.env` do projeto Expo:
```env
EXPO_PUBLIC_API_URL=[https://estaciona-ai-app-dweefuc2eqckdwfn.brazilsouth-01.azurewebsites.net](https://estaciona-ai-app-dweefuc2eqckdwfn.brazilsouth-01.azurewebsites.net)
```

---

## Banco de Dados e Dados Iniciais (Seed)

* **Provedor:** Aiven MySQL (`defaultdb`, porta `12715`, SSL obrigatório).
* **Carga Inicial (`data.sql`):** Popula usuários de teste, veículos, reservas, avaliações, notificações e garagens georreferenciadas no entorno do **SENAI Suíço-Brasileira** (*Rua Bento Branco de Andrade Filho, 379 - Santo Amaro, São Paulo - SP*).
* **Credenciais de Teste:**
  * **E-mail:** `ryan@email.com` ou `vinimalvadeza@email.com`
  * **Senha:** `password`

*(Nota: Em produção contínua, após a carga inicial no banco, mantenha `spring.sql.init.mode=never` no `application.properties`.)*

---

## Estrutura das pastas

```text
├── .github/
│   └── workflows/
│       └── main_estaciona-ai-app.yml
├── .mvn/
│   └── wrapper/
│       └── maven-wrapper.properties
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com.estaciona_ai/
│   │   │       ├── config/
│   │   │       ├── controllers/
│   │   │       ├── dtos/
│   │   │       ├── entities/
│   │   │       ├── repositories/
│   │   │       ├── security/
│   │   │       ├── services/
│   │   │       └── Application.java
│   │   └── resources/
│   │       ├── application.properties
│   │       └── data.sql
│   └── test/
│       └── java/
│           └── com.estaciona_ai/
│               └── ApplicationTests.java
├── .gitattributes
├── .gitignore
├── mvnw
├── mvnw.cmd
├── pom.xml
└── README.md
```

---

## Convenção de nomenclatura

### Linguagem
* **Código (classes, métodos, variáveis, atributos e endpoints):** Inglês.
* **Documentação, commits e mensagens de negócio:** Português.

### Arquivos e pastas
* **`kebab-case`** para pastas e arquivos gerais.
* Arquivos de governança dentro de `.github` seguem em **MAIÚSCULO** (ex.: `CODEOWNERS`, `PULL_REQUEST_TEMPLATE.md`), enquanto workflows seguem o padrão gerado pela esteira (`main_estaciona-ai-app.yml`).

### Variáveis e funções
* **`camelCase`** em todo o backend e frontend.
  ```java
  private String userName;

  public String getUserName() {
      return userName;
  }
  ```

### Endpoints
* **`kebab-case`**, sem iniciar ou finalizar com `/` e separando palavras compostas por hífen.
  ```java
  @RestController
  @RequestMapping("user")
  public class UserController {

      @PostMapping("login-admin")
      public ResponseEntity<?> login(@RequestBody UserRequestDTO user) {
          return ResponseEntity.ok(userService.login(user));
      }
  }
  ```

---

## Modelagem do banco de dados

### Entidades Principais (MER)
* **`users`**: Motoristas e proprietários de garagens (`id`, `name`, `email`, `password`, `phone_number`, `photo_url`).
* **`garages`**: Vagas cadastradas com geolocalização e tarifação (`id`, `user_id`, `name`, `address`, `latitude`, `longitude`, `description`, `available`, `price_per_day`, `price_per_hour`, `created_at`, `updated_at`).
* **`garage_features`**: Diferenciais vinculados a cada garagem (`garage_id`, `feature` — ex.: *Coberto*, *Segurança 24h*, *Câmeras*, *Acessibilidade*).
* **`vehicles`**: Veículos cadastrados pelos motoristas (`id`, `user_id`, `license_plate`, `year`, `brand`, `model`, `color`).
* **`bookings`**: Reservas realizadas entre motorista e garagem (`id`, `driver_id`, `garage_id`, `vehicle_id`, `start_date_time`, `end_date_time`, `total_amount`, `booking_type`, `status`, `created_at`).
* **`chat_rooms`**: Salas de conversa vinculadas a uma garagem e reserva (`id`, `garage_id`, `booking_id`, `driver_id`, `owner_id`, `created_at`).
* **`garage_reviews` & `driver_reviews`**: Avaliações mútuas após reservas concluídas (`COMPLETED`).
* **`notifications`**: Alertas de status de reserva e perfil para os usuários (`id`, `user_id`, `title`, `message`, `is_read`, `created_at`).

### Relacionamentos (DER)
* Um **`user`** (proprietário) pode cadastrar várias **`garages`** ($1:N$), e cada garagem possui múltiplas **`garage_features`** ($1:N$).
* Um **`user`** (motorista) pode cadastrar vários **`vehicles`** ($1:N$) e realizar várias **`bookings`** ($1:N$).
* Cada **`booking`** conecta um motorista (`users`), uma vaga (`garages`) e um veículo (`vehicles`), podendo gerar uma **`chat_room`** ($1:1$), uma **`garage_review`** ($1:1$) e uma **`driver_review`** ($1:1$).
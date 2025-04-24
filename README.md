<p align="center">
  <img src="https://iili.io/3FFO5cF.png" alt="Universidade Católica de Brasília">
</p>

## PROJETO PESSOAL - MS-CATALOGO 📚:

#### 📖 Descrição:
Microsserviço responsável por interagir com a [Fake Store API](https://fakestoreapi.com/) para consultar informações de produtos, possibilitando integração com outros microsserviços para simular um ambiente de e-commerce. Este microsserviço permite filtrar produtos por categoria, ID e nome. Além disso, agora conta com persistência de dados utilizando o banco de dados **PostgreSQL** hospedado na **Amazon RDS**.

Este serviço faz parte de uma arquitetura de microsserviços composta por:

- [`ms-catalogo`](https://github.com/seu-usuario/ms-catalogo) – consulta e persistência de produtos via Fake Store API + PostgreSQL (RDS)
- [`ms-pedido`](https://github.com/seu-usuario/ms-pedido) – orquestrador que recebe as solicitações de compra e aciona os demais serviços
- [`ms-comprovante`](https://github.com/seu-usuario/ms-comprovante) – geração de PDF, envio por e-mail e armazenamento S3

---
## 🔁 Fluxo de Arquitetura utilizada

<img src="docs/arquitetura-fluxo.gif" alt="Fluxo da Arquitetura" width="450">

1. Cliente → `ms-pedido`: Início da requisição de compra.
2. `ms-pedido` → `ms-catalogo`: Requisição de validação de produtos.
3. `ms-catalogo` → Fake Store API: Consulta externa dos produtos.
4. Fake Store API → `ms-catalogo`: Resposta com detalhes dos produtos.
   5 → 6. `ms-catalogo` → PostgreSQL (via RDS): Persistência dos produtos.
7. PostgreSQL → `ms-catalogo`: Confirmação da persistência.
8. `ms-catalogo` → `ms-pedido`: Retorno dos produtos persistidos.
9. `ms-pedido` → `ms-comprovante`: Geração do comprovante.
   10 → 11. `ms-comprovante` → iText: Geração do PDF.
12. `ms-comprovante` → SES: Envio de e-mail com comprovante.
13. SES → E-mail do cliente: Entrega do e-mail.
14. `ms-comprovante` → S3: Armazenamento do PDF com metadados.
15. S3 → `ms-pedido`: Retorno da URI do comprovante.
16. `ms-pedido` → Cliente: Resposta final ao cliente com link do comprovante.
---

#### ⚡ Funcionalidades:
1. 📊 Listagem de todos os produtos disponíveis na Fake Store API;
2. 🔖 Busca de produto por ID;
3. 🔍 Busca de produto por categoria;
4. 🔎 Filtro de produtos por nome (via lógica no backend);
5. 📂 Persistência e atualização dos produtos no banco de dados (PostgreSQL via Amazon RDS).

#### Métodos de execução:

### 🖥️ **Rodar Localmente**
Requisitos para execução:

##### 🔍 Configuração do banco na Amazon RDS:

1. Crie uma instância no RDS utilizando o mecanismo PostgreSQL (ou outro banco SQL de sua preferência);
2. Crie uma **VPC (Virtual Private Cloud)** ou utilize uma existente e associe-a à instância criada;
3. Defina as **credenciais de acesso** ao banco (usuário e senha);
4. Certifique-se de liberar a porta 5432 no grupo de segurança da instância para permitir acesso externo;
5. Copie a URL de conexão da instância RDS.

##### 📁 Configuração no projeto:
No IntelliJ, insira as credenciais no arquivo `application.properties` ou `application.yml`:

```properties
spring.datasource.url=jdbc:postgresql://<sua-instancia>.rds.amazonaws.com:5432/catalogo
spring.datasource.username=<seu-usuario>
spring.datasource.password=<sua-senha>
```

Depois:
```sh
./gradlew bootRun
```

### 🔠 Como funciona a comunicação?
- O microsserviço faz chamadas HTTP reativas para a Fake Store API utilizando WebClient;
- Os dados recebidos podem ser armazenados e consultados via banco de dados PostgreSQL hospedado na Amazon RDS;
- Utiliza JPA para abstração das operações de banco de dados.

#### 🛠️ Tecnologias utilizadas:
- ☕ Java 21;
- 🍃 Spring Boot;
- ✨ WebFlux (reativo);
- 🤖 WebClient;
- 📂 PostgreSQL;
- ☁️ Amazon RDS;
- 📆 Spring Data JPA;

---

## 📌 Endpoints e exemplos de uso:

### 📊 1 - Listagem de Produtos:
```cmd
curl --request GET \
  --url http://localhost:8080/v1/produtos \
  --header 'User-Agent: insomnia/10.3.1'
```

### 🔖 2 - Buscar Produto por ID:
```cmd
curl --request GET \
  --url http://localhost:8080/v1/produtos/id/3 \
  --header 'User-Agent: insomnia/10.3.1'
```

### 🔍 3 - Buscar Produto por Categoria:
```cmd
curl --request GET \
  --url http://localhost:8080/v1/produtos/categoria/electronics \
  --header 'User-Agent: insomnia/10.3.1'
```

### 🔎 4 - Buscar Produto por Título:
```cmd
curl --request GET \
  --url "http://localhost:8080/v1/produtos/titulo?title=Fjallraven%20-%20Foldsack%20No.%201" \
  --header 'User-Agent: insomnia/10.3.1'
```

## 🚚 Autor

<table>
  <tr>
    <td align="center">
      <a href="https://www.linkedin.com/in/wesley-lima-244405251/" title="Wesley Lima">
        <img src="https://media.licdn.com/dms/image/v2/D4D03AQEVAsL2UL6A0w/profile-displayphoto-shrink_400_400/profile-displayphoto-shrink_400_400/0/1721323972268?e=1746662400&v=beta&t=4_2RDPgz5FqJ2G-yRQk3y0vWMVRpSeAPKMAO7IOFXeE" width="100px;" alt="Foto do Wesley Lima"/><br>
        <sub>
          <b>Wesley Lima</b>
        </sub>
      </a>
    </td>
  </tr>
</table>


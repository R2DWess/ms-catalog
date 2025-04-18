## PROJETO PESSOAL - MS-CATALOGO 📚:

#### 📖 Descrição:

Microsserviço responsável por interagir com a [Fake Store API](https://fakestoreapi.com/) para consultar informações de produtos, possibilitando integração com outros microsserviços para simular um ambiente de e-commerce. Este microsserviço permite filtrar produtos por categoria, ID e nome.

#### ⚡ Funcionalidades:

1. 📊 Listagem de todos os produtos disponíveis na Fake Store API;
2. 🔖 Busca de produto por ID;
3. 🔍 Busca de produto por categoria;
4. 🔎 Filtro de produtos por nome (via lógica no backend).

#### Métodos de execução:

### 🖥️ **1️⃣ Rodar Localmente**

Para executar o projeto localmente:

> ⚡ Este projeto **não possui banco de dados**, pois é uma integração direta com a Fake Store API.

```sh
./gradlew bootRun
```

### 🔄 **2️⃣ Rodar com Docker**

Para criar e rodar a imagem Docker do microsserviço:

1. Construir a imagem:

```sh
docker build -t ms-catalogo:latest .
```

2. Subir o container:

```sh
docker run -p 8080:8080 ms-catalogo:latest
```

3. Para parar o container:

```sh
docker stop <id_container>
```

### 🔠 Como funciona a comunicação?

- Este microsserviço faz chamadas HTTP reativas para a Fake Store API utilizando WebClient.
- Todos os dados são consumidos diretamente da API externa.

#### 🛠️ Tecnologias utilizadas:

- ☕ Java 17;
- 🍃 Spring Boot;
- ✨ WebFlux (reativo);
- 🤖 WebClient;
- 📡 Docker;

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
  --url http://localhost:8080/v1/produtos/listar-id/3 \
  --header 'User-Agent: insomnia/10.3.1'
```

### 🔍 3 - Buscar Produto por Categoria:

```cmd
curl --request GET \
  --url http://localhost:8080/v1/produtos/listar-categoria/electronics \
  --header 'User-Agent: insomnia/10.3.1'
```

*(Opcional: Se implementar busca por nome, podemos adicionar aqui depois!)*

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

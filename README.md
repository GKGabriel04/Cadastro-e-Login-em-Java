# 🚀 Sistema de Login Seguro com Spring Boot

Este projeto é um sistema de cadastro e login desenvolvido como parte de uma atividade acadêmica, demonstrando conceitos avançados de autenticação, autorização, segurança de dados e conformidade com a LGPD. O sistema foi construído de forma modular para servir como uma base robusta para futuros projetos.

**Status do Projeto:** Concluído ✔️

---

## 🛠️ Tecnologias Utilizadas

- **Backend:**
  - Java 21
  - Spring Boot 3
  - Spring Security 6
  - Spring Data MongoDB
- **Frontend:**
  - Thymeleaf
- **Banco de Dados:**
  - MongoDB Atlas (Cloud)
- **Ferramentas:**
  - Maven
  - Lombok & MapStruct

---

## ✨ Funcionalidades

- [x] **Autenticação de Usuários:** Cadastro, Login e Logout seguros.
- [x] **Segurança de Senhas:** Armazenamento de senhas com hashing (BCrypt).
- [x] **Controle de Acesso por Perfil (Autorização):** Distinção entre usuários comuns (`ROLE_USER`) e administradores (`ROLE_ADMIN`).
- [x] **Painel de Administração:**
  - [x] Listagem de todos os usuários cadastrados.
  - [x] Exclusão de usuários.
  - [x] Mascaramento de dados sensíveis para o admin (CPF, Rua, Número).
- [x] **Conformidade com a LGPD:** Criptografia de dados pessoais sensíveis (CPF) no banco de dados (nível de aplicação).
- [x] **Criação de Admin Padrão:** Um usuário `admin` é criado automaticamente na primeira inicialização da aplicação para facilitar o acesso inicial.

---

## ⚙️ Como Executar o Projeto

Siga os passos abaixo para configurar e executar o projeto localmente.

### Pré-requisitos
- JDK 21 (ou superior)
- Apache Maven 3.8+
- Uma conta no MongoDB Atlas

### Configuração

1.  **Clone o repositório:**
    ```bash
    git clone https://github.com/GKGabriel04/Cadastro-e-Login-em-Java.git
    cd Cadastro-e-Login-em-Java
    ```

2.  **Crie o arquivo de configuração local:**
    Como o arquivo de configuração com os segredos não é enviado para o GitHub, você precisa criá-lo. Na pasta `src/main/resources/`, crie um arquivo chamado `application-dev.yaml` e adicione o seguinte conteúdo, substituindo os placeholders `<...>`:

    ```yaml
    spring:
      data:
        mongodb:
          host: localhost
          port: 27017
          database: Login_seguro

    app:
      encryption:
        key: k9pXvR7sL3bA2mHq
    ```

3.  **Execute a aplicação:**
    Você pode executar usando o Maven pelo terminal na raiz do projeto:
    ```bash
    mvn spring-boot:run -Dspring-boot.run.profiles=dev
    ```
    Ou pode executar a classe `LoginSeguroApplication.java` diretamente da sua IDE, garantindo que o perfil `dev` esteja ativo.

### Acesso ao Sistema

- A aplicação estará disponível em `http://localhost:8080`.
- **Usuário Admin Padrão (criado na primeira execução):**
  - **Login:** `admin@gmail.com`
  - **Senha:** `testedeadmin` *(Recomendado alterar no código para um ambiente mais seguro)*

---

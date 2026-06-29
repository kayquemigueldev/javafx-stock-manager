# 📦 Stock Manager

![Java](https://img.shields.io/badge/Java-21-orange)
![JavaFX](https://img.shields.io/badge/JavaFX-21-blue)
![MySQL](https://img.shields.io/badge/MySQL-8-4479A1)
![Maven](https://img.shields.io/badge/Maven-3.8-red)
![License](https://img.shields.io/badge/License-MIT-green)

Sistema desktop para gerenciamento de estoque desenvolvido em JavaFX como projeto de estudo, aplicando autenticação de usuários, controle de permissões, gerenciamento de produtos e movimentações de estoque.

## Tecnologias

* Java 21
* JavaFX
* Maven
* MySQL
* JDBC
* BCrypt
* iText PDF

## Funcionalidades

* Login com autenticação utilizando BCrypt
* Controle de acesso por perfil (Administrador e Funcionário)
* Dashboard com indicadores e gráficos
* Cadastro, edição e exclusão de produtos
* Upload de imagens dos produtos
* Controle de usuários
* Registro de entradas e saídas de estoque
* Histórico de movimentações
* Auditoria de ações (logs)
* Exportação de produtos em PDF
* Backup do banco de dados
* Splash Screen na inicialização

---

## Telas

### Login

![Login](assets/login.png)

### Dashboard

![Dashboard](assets/dashboard.png)

### Produtos

![Produtos](assets/products.png)

### Movimentações

![Movimentações](assets/movements.png)

### Usuários

![Usuários](assets/users.png)

### Logs

![Logs](assets/logs.png)

---

## Estrutura do projeto

```text
src
├── controller
├── dao
├── database
├── model
├── security
├── util
└── resources
```

---

## Como executar

Clone o projeto:

```bash
git clone https://github.com/SEU-USUARIO/javafx-stock-manager.git
```

Entre na pasta:

```bash
cd javafx-stock-manager
```

Execute a aplicação:

```bash
./mvnw javafx:run
```

---

## Banco de dados

Crie um banco chamado:

```text
stock_manager
```

Configure as credenciais da conexão em:

```text
database/Conexao.java
```

---

## Objetivo

Este projeto foi desenvolvido para aprofundar conhecimentos em desenvolvimento desktop utilizando Java, JavaFX e MySQL, aplicando conceitos como autenticação, arquitetura em camadas, persistência de dados, controle de acesso e organização de código.

---

## Autor

**Kayque Miguel da Fonseca Reis Galvão**

GitHub: https://github.com/kayquemigueldev

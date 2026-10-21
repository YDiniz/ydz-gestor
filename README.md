# YDZ Gestor

Sistema desktop para gestão de serviços de reforma, desenvolvido para a **YDZ Reformas**, empresa especializada em reformas de escolas.

> 🚧 **Projeto em desenvolvimento.** Este README será atualizado conforme novas funcionalidades forem implementadas.

## 📋 Sobre o projeto

O YDZ Gestor foi criado para organizar digitalmente informações que antes eram controladas em papel: dados de escolas atendidas, serviços/obras em andamento, funcionários envolvidos, documentos (orçamentos, comprovantes) e controle financeiro de cada serviço.

Além de uso real pela empresa, este projeto também serve como estudo prático de desenvolvimento Java, aplicando conceitos de:
- Programação Orientada a Objetos
- Persistência de dados com JDBC
- Arquitetura em camadas (Model, DAO, Service, Controller)
- Interfaces gráficas com JavaFX e CSS
- Autenticação com hash de senha (BCrypt)
- Testes automatizados com JUnit

## 🛠️ Tecnologias

- **Java 17**
- **JavaFX** — interface gráfica desktop (FXML + CSS)
- **MySQL** — banco de dados relacional
- **JDBC** — conexão com o banco (sem ORM, implementado manualmente via padrão DAO)
- **Maven** — gerenciamento de dependências
- **BCrypt (jBCrypt)** — hash de senha para autenticação
- **JUnit 5** — testes automatizados

## ✅ Funcionalidades implementadas

**Acesso**
- Login com senha criptografada
- Menu principal em grade de cartões

**Serviços (obras)**
- Cadastro de serviço, com escola, funcionários envolvidos e orçamento anexado
- Acompanhamento com filtro por status (orçamento, em andamento, concluído)
- Janela de detalhes: dados do serviço, funcionários, documentos (abre o arquivo com duplo clique), pagamentos e totais recebido, gasto e saldo
- Edição: status, funcionários, documentos e pagamentos

**Pagamentos**
- Lançamento de pagamentos recebidos (pagos pela escola) e realizados (funcionários ou materiais), vinculados a um serviço
- Descrição, data e anexo opcional de comprovante
- Edição e exclusão de pagamentos

**Escolas**
- Cadastro, listagem, edição e exclusão, com nome e telefone/e-mail do contato
- Exclusão bloqueada quando a escola possui serviços vinculados

**Funcionários**
- Cadastro, listagem, edição e exclusão, com cargo e telefone
- Exclusão bloqueada quando o funcionário está vinculado a serviços

**Qualidade**
- Testes automatizados (JUnit) nas regras de negócio

## 🔜 Em desenvolvimento

- Campo CNPJ da escola, com validação
- Empacotamento como executável

## 🏗️ Arquitetura

```
Tela (JavaFX/FXML) → Controller → Service (regras de negócio) → DAO (acesso a dados) → Banco de dados (MySQL)
```

Essa separação em camadas mantém a lógica de negócio independente da interface e do banco de dados.

## 📄 Licença

Este projeto possui licença personalizada — veja o arquivo [LICENSE](https://github.com/YDiniz/ydz-gestor/blob/main/LICENSE) para detalhes sobre uso permitido.

## 👤 Autor

Desenvolvido por **Yuri Diniz Barbosa**, como projeto de estudo e portfólio em desenvolvimento Java.

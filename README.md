# YDZ Gestor

Sistema desktop para gestão de serviços de reforma, desenvolvido para a **YDZ Reformas**, empresa especializada em reformas de escolas.

> 🚧 **Projeto em desenvolvimento.** Este README será atualizado conforme novas funcionalidades forem implementadas.

## 📋 Sobre o projeto

O YDZ Gestor foi criado para organizar digitalmente informações que antes eram controladas em papel: dados de escolas atendidas, serviços/obras em andamento, funcionários envolvidos, documentos (orçamentos, comprovantes) e controle de pagamentos.

Além de uso real pela empresa, este projeto também serve como estudo prático de desenvolvimento Java, aplicando conceitos de:
- Programação Orientada a Objetos
- Persistência de dados com JDBC
- Arquitetura em camadas (Model, DAO, Service, Controller)
- Interfaces gráficas com JavaFX
- Autenticação com hash de senha (BCrypt)

## 🛠️ Tecnologias

- **Java 17**
- **JavaFX** — interface gráfica desktop
- **MySQL** — banco de dados relacional
- **JDBC** — conexão com o banco (sem ORM, implementado manualmente via padrão DAO)
- **Maven** — gerenciamento de dependências
- **BCrypt (jBCrypt)** — hash de senha para autenticação
- **ControlsFX** — componentes JavaFX adicionais (CheckComboBox)

## ✅ Funcionalidades implementadas

- Autenticação de usuário (login com senha criptografada)
- Cadastro de serviços (obras), com vínculo a escola e seleção múltipla de funcionários
- Cadastro de escolas e funcionários (camada de dados e regras de negócio)

## 🔜 Em desenvolvimento

- Anexação de documentos (orçamentos, comprovantes) por serviço
- Lançamento de pagamentos/adiantamentos por serviço
- Tela de acompanhamento de serviços com filtro por status
- Testes automatizados (JUnit)
- Empacotamento como executável

## 🏗️ Arquitetura

```
Tela (JavaFX/FXML) → Controller → Service (regras de negócio) → DAO (acesso a dados) → Banco de dados (MySQL)
```

Essa separação em camadas mantém a lógica de negócio independente da interface e do banco de dados.

## 📄 Licença

Este projeto possui licença personalizada — veja o arquivo [LICENSE](./LICENSE) para detalhes sobre uso permitido.

## 👤 Autor

Desenvolvido por **Yuri Diniz Barbosa**, como projeto de estudo e portfólio em desenvolvimento Java.

# Tedros Apps

**Tedros Apps** é o repositório dedicado aos aplicativos e módulos base do ecossistema corporativo Tedros. Ele contém aplicações prontas para extensão (Gestão de Pessoas, Estoque, Serviços, TI, etc.) e é o local onde você desenvolverá novos aplicativos de negócios para a plataforma.

Este repositório compõe a stack do framework:
- **[Tedros](https://github.com/Tedros-Box/Tedros)**: O núcleo do framework (lado cliente e servidor).
- **[tedros-apps](https://github.com/Tedros-Box/tedros-apps)**: Este repositório, focado nos aplicativos e módulos de negócio.
- **[tedros-environment](https://github.com/Tedros-Box/tedros-environment)**: Infraestrutura, orquestração Docker, proxy e banco de dados.

---

## 🏗️ Arquitetura de um Aplicativo

O ecossistema Tedros adota uma arquitetura rigorosa de 5 submódulos Maven para cada novo aplicativo. Esta separação garante segurança, organização da lógica de negócios e UI isolada:

1. **Root (`pom.xml`)**: O projeto pai que amarra os módulos de um aplicativo.
2. **`-model`**: Contém as entidades JPA e objetos de domínio, transferidos entre cliente e servidor.
3. **`-ejb-client`**: Interfaces dos controladores EJB remotos.
4. **`-ejb`**: O backend do seu aplicativo, contendo regras de negócio (BO), repositórios (EAO), lógica transacional (Services) e exposição segura de API (Controllers), além de configurações JPA (`persistence.xml`).
5. **`-ejb-ear`**: O empacotador *Enterprise Archive* (EAR) usado para o deploy da aplicação no Apache TomEE.
6. **`-fx`**: O lado cliente em JavaFX, contendo classes de Visão (`ModelView`), anotações de formulário, layouts e recursos de internacionalização (i18n).

O Tedros utiliza o **PostgreSQL** como o banco de dados principal de produção (com H2 suportado para desenvolvimento simplificado). A infraestrutura e scripts para inicialização do banco são gerenciados no repositório `tedros-environment`.

---

## 🚀 Como Desenvolver um Novo Aplicativo

### 1. Scaffolding (Estrutura Inicial)
Evite a criação manual de diretórios e arquivos de configuração! Utilize o script Python de scaffolding localizado em `skills/core/scaffold.py`. Ele gerará todos os 5 projetos do seu aplicativo já pré-configurados com os `pom.xml` adequados.
* **Guia**: [`skills/core/project-scaffolding.md`](skills/core/project-scaffolding.md)

### 2. Ponto de Entrada e Módulos
Toda aplicação começa com uma classe `AppStart` anotada com `@TApplication`. Essa anotação é lida por reflexão, tornando o aplicativo visível na plataforma sem a necessidade de configurações de registro manuais. 
Dentro da `AppStart`, você registra classes filhas de `TModule` (anotadas com `@TView`), que definirão as abas e telas visíveis no menu lateral da aplicação.
* **Guia**: [`skills/applications-and-modules-overview.md`](skills/applications-and-modules-overview.md)

### 3. Camada de Dados e Regras de Negócio (Backend)
No módulo `-ejb`, você concentrará as regras de segurança e domínio:
* **Entidades**: Objetos JPA para abstração de banco de dados.
* **EAO (Entity Access Objects)**: Tratamento de eventos de ciclo de vida do banco (callbacks).
* **BO (Business Objects)**: Onde reside toda a lógica de negócios da sua aplicação (contexto CDI local).
* **Services**: EJBs `@Singleton` locais que gerenciam bloqueios (`@Lock`) e transações (`@TransactionAttribute`).
* **Controllers**: EJBs remotos (`@Stateless`) protegidos pelo interceptor `@TSecurityInterceptor`, expondo a API de forma segura.
* **Guias**: [`skills/data/`](skills/data/)

### 4. Interface Gráfica Declarativa (JavaFX)
O módulo `-fx` é o responsável por toda a interação do usuário. O Tedros possui um motor de UI declarativa massivo: você constrói telas inteiras apenas adicionando anotações (como `@TForm`, `@TTextField`, `@TComboBox`, etc.) sobre uma classe `TEntityModelView`.
A comunicação de binding entre os campos visuais e sua Entidade é resolvida automaticamente por reflexão.
* **Guias**: [`skills/ui/`](skills/ui/)

### 5. Integração Remota
Os módulos cliente (`-fx`) comunicam-se com os EJBs do servidor usando lookups JNDI fornecidos por interfaces padrão, repassando o `TAccessToken` para validação na raiz de cada método de negócio.
* **Guias**: [`skills/integration/`](skills/integration/)

### 6. Dando "Superpoderes" ao Agente de IA (Teros)
Qualquer aplicativo deste repositório pode expor ações para o agente **Teros** através de classes que estendem `TFunction` (ex.: `SearchEmployeeFunction` em `app-person-fx`, `GetRedmineIssueAiFunction` em `app-itsupport-tools-fx`). Esse é o caminho **client-side clássico**: a função roda dentro do próprio JavaFX e, se precisar de dados do servidor, chama o EJB via `TEjbServiceLocator` — exatamente como qualquer outra tela do Tedros.

> **Nota — Tool Relay:** desde a centralização da IA no backend (padrão *Tool Relay*, detalhado em [`skills/tedros-framework-overview.md`](skills/tedros-framework-overview.md#11-grupo-tdrs-ai--tool-relay-agente-de-ia-centralizado-no-backend)), a maioria das `TFunction`s deste repositório que só consultam dados (pessoas, estoque, serviços, Redmine, GitLab, documentos) também tem uma tool equivalente rodando no servidor, no módulo `Tedros/tedrosbox/tdrs-ai` — **fora deste repositório**. As classes aqui em `tedros-apps` **não são alteradas** por isso: com o relay desligado (`sys.ai.toolrelay.enabled=false`, default) elas continuam executando normalmente no cliente.
>
> **Regra de ouro ao criar uma nova `TFunction` que só lê dados do servidor:** dê a ela um `name` estável e, se for útil ao caso de uso corporativo, avise ao mantenedor do `tdrs-ai` para criar a equivalente no backend com o **mesmo nome** — em modo relay, a tool de backend sempre tem precedência, então nenhuma linha muda aqui. Funções que abrem telas, leem estado da UI ou geram arquivos locais **permanecem exclusivas do FE** e não devem ser portadas.

* **Guia de arquitetura completo**: [`skills/tedros-framework-overview.md`](skills/tedros-framework-overview.md) (seção "Tool Relay")

---

## 📚 Base de Conhecimento (Skills)

Para auxiliar programadores humanos e também **Agentes de Inteligência Artificial**, este repositório traz uma pasta `skills/` com rica documentação e padrões de projeto exigidos pela arquitetura Tedros:

*   **[`skills/tedros-framework-overview.md`](skills/tedros-framework-overview.md)**: Visão arquitetural ponta a ponta — inclui o módulo `tdrs-ai` (Tool Relay: IA centralizada no backend, tools de servidor, observabilidade).
*   **`skills/core/`**: Scripts de scaffolding e padrões de árvore de diretório.
*   **`skills/data/`**: Guias para construir sua camada de banco de dados e regras de negócio.
*   **`skills/ui/`**: Catálogo de controles visuais, layout avançado e gerenciamento de arquivos.
*   **`skills/integration/`**: Regras para se conectar e invocar os controladores remotos via EJB.

Sempre leia as orientações nos guias listados na pasta `skills/` antes de criar novos componentes para sua aplicação.

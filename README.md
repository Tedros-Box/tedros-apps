# Bem vindo ao Tedros Box

Seu gerenciador de aplicativos integrados, fornecemos uma solução cliente servidor para criação de aplicações simples e complexas com foco na qualidade, segurança, simplicidade no desenvolvimento visando uma drástica redução no tempo necessário para desenvolvimento e na quantidade de profissionais em um projeto. 

Você que é desenvolvendor Java, empresário ou um estudante com conhecimentos na tecnologia já parou para pensar quantas empresas, pequenos comerciantes, prestadores de serviços, ONGs e outros setores na sua cidade ou ate mesmo no seu bairro estão precisando de uma solução em software? 

Se for fazer uma pesquisa eu tenho a certeza de que ira identificar uma grande quantidade de ideias para aplicativos que vão atender, não apenas ao entrevistado  mas quem sabe até um setor inteiro! 

E em contrapartida existe você e outros profissionais e empresas de TI a procura destas oportunidades. 

O grande problema entre estes dois grupos interdependentes é conseguir achar uma solução que consiga mitigar os custos e os riscos de forma que o produto final atenda as necessidades de ambos os lados. Qualidade e custo benefício. 

A proposta Tedros com a plataforma Tedros Box é atender estas necessidades e mitigar os riscos disponibilizando aos desenvolvedores um framework completo para desenvolvimento de aplicativos simples e complexos de forma rápida fácil e customizavel gerando bons ganhos entregando ao cliente uma solução com qualidade, segurança e customizavel de fácil uso e manutenção. Segue alguns pontos:
 
 1. Foco na solução, isto é, no business core a ser desenvolvido para atender a necessidade do cliente. Nosso framework delega a parte visual e de segurança ao usuário final que pode escolher e modificar a aparência do seu aplicativo,  definir os níveis de segurança dos utilizadores, escolher o idioma e outras funcionalidades que foram abstraidas ao máximo para que o tempo de desenvolvimento da solução desejada seja o menor possível porém sem perder na qualidade final.
 
 2. Simplificação no desenvolvimento, não é preciso conhecer muitas linguagens, somente Java, e toda a estrutura de novos projetos podem ser gerados através de archetypes, por exemplo para o desenvolvimento de uma solução CRUD para uma entidade com diversos campos e listas pode ser realizado com apenas 6 classes. 
 
 3. Desenvolvimento declarativo usando anotações (Annotations)  para declaração de autorizações, componentes de visualização na tela, módulos e aplicações. Este modelo de desenvolvimento permite maior flexibilidade em tempo de desenvolvimento, testes, manutenção e refatoração. 
 
 4. Simplificação na customização e criação de novos componentes. 
 
 5. Utilização de modelo cliente servidor onde toda estrutura é montada através de um archetype completo com servidor de aplicação, base de dados e projeto para inicialização do sistema Tedros Box. 
 
 6. Total integração entre todos os aplicativos. A solução Tedros Box é um sistema gerenciador de aplicativos sendo assim podemos ter diversos aplicativos que se integram e se complementam. Por default  a solução já vem com as seguintes soluções e aplicativos que podem e devem ser integrados a outros que precisarem dos seus dados:

 *  **Ferramentas**, Este aplicativo possui um módulo para definir e editar Themas, Controle de Autorização, perfil e usuários, Preferências (Definições do sistema) e um módulo de notificação para envio e agendamento de emails a ser enviado. 
 
 * **Suporte**, neste aplicativo encontra-se alguns dos módulos básicos para integração com a maioria das soluções:
    - Artefatos Documentais para gerar ou guardar documentos (arquivos). Integrado ao módulo de notificação é possível enviar o documento por email. 
    - **Localização**, este item possui módulos para Gerenciar e linkar informações de Países, Estados, Cidades, Endereços e Lugares. Podendo estes dados serem importados através de um arquivo xls, xlsx ou csv. A solução já vem com estes arquivos para importação com uma grande base de dados e a edição de endereços pode ser acompanhada e visualizada através de um mapa. (Mapquest) 
    - Pessoas e Empresas, aqui encontra-se modulos para gerenciar todas as pessoas físicas e pessoas jurídicas podendo gerar relatórios dos mesmos. 
 
 * **Fornecimento de serviços**, este aplicativo possui dois modulos, um para gerenciar os serviços e outro para os planos. 
 
 Para mais detalhes sobre cada um destes aplicativos visite nossa wiki. 

A integração entre os aplicativos se dá pela utilização dos componentes ou serviços que estes fornecem ou até mesmo extendendo as suas entidades. Desta forma é possível desenvolver soluções integráveis entre si de forma simples sem precisar recriar bases redundantes de informação. 
 
Eu sei disto porque o Tedros nasceu apartir desta necessidade. 



Quando o sistema é desenvolvido sem a necessidade de integração com sistemas legado 

Mas se você já for um desenvolvedor com alguns anos de experiencia sabe bem que para desenvolver um software com qualidade não é assim uma tarefa fácil e que dependendo da quantidade de requisitos o tempo e o custo tendem a subir.  


# Files

StackEdit stores your files in your browser, which means all your files are automatically saved locally and are accessible **offline!**

## Create files and folders

The file explorer is accessible using the button in left corner of the navigation bar. You can create a new file by clicking the **New file** button in the file explorer. You can also create folders by clicking the **New folder** button.

## Switch to another file

All your files and folders are presented as a tree in the file explorer. You can switch from one to another by clicking a file in the tree.

## Rename a file

You can rename the current file by clicking the file name in the navigation bar or by clicking the **Rename** button in the file explorer.

## Delete a file

You can delete the current file by clicking the **Remove** button in the file explorer. The file will be moved into the **Trash** folder and automatically deleted after 7 days of inactivity.

## Export a file

You can export the current file by clicking **Export to disk** in the menu. You can choose to export the file as plain Markdown, as HTML using a Handlebars template or as a PDF.


# Synchronization

Synchronization is one of the biggest features of StackEdit. It enables you to synchronize any file in your workspace with other files stored in your **Google Drive**, your **Dropbox** and your **GitHub** accounts. This allows you to keep writing on other devices, collaborate with people you share the file with, integrate easily into your workflow... The synchronization mechanism takes place every minute in the background, downloading, merging, and uploading file modifications.

There are two types of synchronization and they can complement each other:

- The workspace synchronization will sync all your files, folders and settings automatically. This will allow you to fetch your workspace on any other device.
	> To start syncing your workspace, just sign in with Google in the menu.

- The file synchronization will keep one file of the workspace synced with one or multiple files in **Google Drive**, **Dropbox** or **GitHub**.
	> Before starting to sync files, you must link an account in the **Synchronize** sub-menu.

## Open a file

You can open a file from **Google Drive**, **Dropbox** or **GitHub** by opening the **Synchronize** sub-menu and clicking **Open from**. Once opened in the workspace, any modification in the file will be automatically synced.

## Save a file

You can save any file of the workspace to **Google Drive**, **Dropbox** or **GitHub** by opening the **Synchronize** sub-menu and clicking **Save on**. Even if a file in the workspace is already synced, you can save it to another location. StackEdit can sync one file with multiple locations and accounts.

## Synchronize a file

Once your file is linked to a synchronized location, StackEdit will periodically synchronize it by downloading/uploading any modification. A merge will be performed if necessary and conflicts will be resolved.

If you just have modified your file and you want to force syncing, click the **Synchronize now** button in the navigation bar.

> **Note:** The **Synchronize now** button is disabled if you have no file to synchronize.

## Manage file synchronization

Since one file can be synced with multiple locations, you can list and manage synchronized locations by clicking **File synchronization** in the **Synchronize** sub-menu. This allows you to list and remove synchronized locations that are linked to your file.


# Publication

Publishing in StackEdit makes it simple for you to publish online your files. Once you're happy with a file, you can publish it to different hosting platforms like **Blogger**, **Dropbox**, **Gist**, **GitHub**, **Google Drive**, **WordPress** and **Zendesk**. With [Handlebars templates](http://handlebarsjs.com/), you have full control over what you export.

> Before starting to publish, you must link an account in the **Publish** sub-menu.

## Publish a File

You can publish your file by opening the **Publish** sub-menu and by clicking **Publish to**. For some locations, you can choose between the following formats:

- Markdown: publish the Markdown text on a website that can interpret it (**GitHub** for instance),
- HTML: publish the file converted to HTML via a Handlebars template (on a blog for example).

## Update a publication

After publishing, StackEdit keeps your file linked to that publication which makes it easy for you to re-publish it. Once you have modified your file and you want to update your publication, click on the **Publish now** button in the navigation bar.

> **Note:** The **Publish now** button is disabled if your file has not been published yet.

## Manage file publication

Since one file can be published to multiple locations, you can list and manage publish locations by clicking **File publication** in the **Publish** sub-menu. This allows you to list and remove publication locations that are linked to your file.


# Markdown extensions

StackEdit extends the standard Markdown syntax by adding extra **Markdown extensions**, providing you with some nice features.

> **ProTip:** You can disable any **Markdown extension** in the **File properties** dialog.


## SmartyPants

SmartyPants converts ASCII punctuation characters into "smart" typographic punctuation HTML entities. For example:

|                |ASCII                          |HTML                         |
|----------------|-------------------------------|-----------------------------|
|Single backticks|`'Isn't this fun?'`            |'Isn't this fun?'            |
|Quotes          |`"Isn't this fun?"`            |"Isn't this fun?"            |
|Dashes          |`-- is en-dash, --- is em-dash`|-- is en-dash, --- is em-dash|


## KaTeX

You can render LaTeX mathematical expressions using [KaTeX](https://khan.github.io/KaTeX/):

The *Gamma function* satisfying $\Gamma(n) = (n-1)!\quad\forall n\in\mathbb N$ is via the Euler integral

$$
\Gamma(z) = \int_0^\infty t^{z-1}e^{-t}dt\,.
$$

> You can find more information about **LaTeX** mathematical expressions [here](http://meta.math.stackexchange.com/questions/5020/mathjax-basic-tutorial-and-quick-reference).


## UML diagrams

You can render UML diagrams using [Mermaid](https://mermaidjs.github.io/). For example, this will produce a sequence diagram:

```mermaid
sequenceDiagram
Alice ->> Bob: Hello Bob, how are you?
Bob-->>John: How about you John?
Bob--x Alice: I am good thanks!
Bob-x John: I am good thanks!
Note right of John: Bob thinks a long<br/>long time, so long<br/>that the text does<br/>not fit on a row.

Bob-->Alice: Checking with John...
Alice->John: Yes... John, how are you?
```

And this will produce a flow chart:

```mermaid
graph LR
A[Square Rect] -- Link text --> B((Circle))
A --> C(Round Rect)
B --> D{Rhombus}
C --> D
```

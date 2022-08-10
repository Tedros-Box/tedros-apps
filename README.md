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
 
# Use Case

O sistema Tedros Box esta em plena utilização pela ONG [Social Movement](http://www.somossocial.org.br)

Esta ONG começou durante a pandemia do Covid-19 que resultou no aumento da fome,  por conta do  lock-down, em muitas pessoas vulneráveis e moradores de rua que dependiam das doações de comida de restaurantes e de pessoas comuns, foi então que um grupo de amigos começaram a preparar refeições em casa e distribui-las nas ruas. A iniciativa deu certo e eles cresceram em voluntários, doações e refeições distribuídas. Com este crescimento veio a necessidade de seguir um processo de trabalho que os ajudassem no gerenciamento das ações, doações, entrada e saída de produtos em estoque, recrutamento e controle dos voluntários, mailing, termos de adesão e das informações do web site. Com o Tedros Box foi desenvolvida uma solução completa que atendesse a todas as necessidades do então projeto chamado Covid Sem Fome e de forma rápida e gradual a solução foi desenvolvida sem muito esforço, ouve varias iterações e mudanças nos aplicativos e no sistema foram 27 versões no total, mas todas foram realizadas de forma rápida e sem muitas complicações. Quando o projeto foi registrado oficialmente como uma ONG e com um novo nome, processo e web site a refatoração dos aplicativos e do web site foi realizado sem muito esforço e complicação.
O web site da ONG esta integrado aos serviços na camada  de negócios dos aplicativos do  sistema Tedros Box que é utilizado pelos diversos colaboradores da ONG, cada um com o perfil e permissões adequados a sua atribuição. 

Segue alguns prints do Tedros Box e aplicativos feitos para a ONG.

Tela do aplicativo da ONG:
![Tela 1](https://github.com/Tedros-Box/tedros-apps/blob/master/printscreen/somos1.png)

![Tela 2](https://github.com/Tedros-Box/tedros-apps/blob/master/printscreen/somos2.png)

![Tela 3](https://github.com/Tedros-Box/tedros-apps/blob/master/printscreen/somos3.png)

![Tela 4](https://github.com/Tedros-Box/tedros-apps/blob/master/printscreen/somos4.png)

![Tela 5](https://github.com/Tedros-Box/tedros-apps/blob/master/printscreen/somos5.png)

![Tela 6](https://github.com/Tedros-Box/tedros-apps/blob/master/printscreen/somos6.png)


**Print Screen**

Tela de login:
![Tela de login](https://github.com/Tedros-Box/tedros-apps/blob/master/printscreen/login.png)

Tela de configuração inicial:
![Tela configuração inicial](https://github.com/Tedros-Box/tedros-apps/blob/master/printscreen/config.png)

Tela principal:
![Tela principal](https://github.com/Tedros-Box/tedros-apps/blob/master/printscreen/tela1.png)

Tela para definir thema:
![Tela para definir thema](https://github.com/Tedros-Box/tedros-apps/blob/master/printscreen/thema1.png)

Thema Pedras:
![Thema Pedras](https://github.com/Tedros-Box/tedros-apps/blob/master/printscreen/thema2.png)

Thema Solidariedade:
![Thema Solidariedade](https://github.com/Tedros-Box/tedros-apps/blob/master/printscreen/thema3.png)

Tela Perfil:
![Tela Perfil](https://github.com/Tedros-Box/tedros-apps/blob/master/printscreen/perfil.png)

Tela de notificação com um email agendado para envio em uma data futura: :
![Tela de notificação](https://github.com/Tedros-Box/tedros-apps/blob/master/printscreen/notify.png)

Tela para edição de país:
![Tela pais](https://github.com/Tedros-Box/tedros-apps/blob/master/printscreen/pais.png)

Tela pessoa fisica:
![Tela pessoa fisica ](https://github.com/Tedros-Box/tedros-apps/blob/master/printscreen/pf_1.png)

Tela pessoa fisica utilizando componente de endereço do aplicativo Localização:
![Tela endereço](https://github.com/Tedros-Box/tedros-apps/blob/master/printscreen/pf_2.png)

Tela gerar relatório de pessoa fisica:
![Tela relatorio](https://github.com/Tedros-Box/tedros-apps/blob/master/printscreen/pf_rel1.png)

Tela PDF gerado:
![Tela pdf](https://github.com/Tedros-Box/tedros-apps/blob/master/printscreen/pf_rel2.png)






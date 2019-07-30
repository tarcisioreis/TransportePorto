# TransportePorto

Projeto usando API PoaTransporte - DataPoa

Descrição:

Api desenvolvida em Spring Boot persistindo dados com JPA Hibernate em banco de dados local MySQL, 
estrutura baseada em 3 camadas: Controller, Service e Repository, sendo a camada de banco Entity não sendo acessada diretamente 
mas, usando acesso via DTO(Data Transfer Object) pela camada Service.

Métodos implementados:

LinhaController:

1) list - via GET - listagem de linhas de ônibus - mostra id, codigo e nome;
2) buscarPorNome - via POST - parâmetro nome - filtra todas as linhas pelo nome informado - mostra id, codigo e nome;
3) create - via POST - parâmetro objeto DTO linhaDTO - inclui codigo e nome no banco - primeiro pesquisa por codigo e nome na api externa e por última verifica no banco se dados não foram incluidos anteriormente;
4) update - via PUT - parâmetro objeto DTO linhaDTO - altera codigo e nome no banco - primeiro verifica se dados existem na base e após isso faz UPDATE nos dados;
5) delete - via DELETE - parâmetro ID da linha de ônibus - verifica se existe o dado informado e após isso faz DELETE nos dados(exclusão fisica);

ItinerarioController:

1) buscarPorLinha - via POST - parâmetro idlinha - filtra todos os itinerario de uma linha informada - mostra os dados da linha e latitude e longitude;
2) create - via POST - parâmetro objeto itinerarioDTO - inclui idlinha, latitude e longitude no banco - primeiro pesquisa por idlinha na api externa, não existindo latitude e longitude, verifica no banco se já não existe as mesmas localizações para mesmo idlinha, não encontrando esse dados faz INSERT dos dados;
3) update - via PUT - parâmetro objeto DTO itinerarioDTO - altera idlinha, latitude e longitude no banco - primeiro verifica se dados existem na base e após isso faz UPDATE nos dados;
4) delete - via DELETE - parâmetro ID do itinerario - verifica se existe o dado informado e após isso faz DELETE nos dados(exclusão fisica);
5) buscarRotas - via GET - parâmetros latitude, longitude e raio - faz busca usando a API PoaDigital e retorna a existência de linhas de ônibus dentro do raio informado em KM.


Requisitos para funcionamento:

1) Ter o Eclipse/Intellij como IDE com Maven para isso somente importar como Projeto Maven;

2) Ter um banco MySQL local ou remoto, configurar no diretório src/main/resources/application.properties os dados para aceso 
   ao banco.


Para ver os Web Services instalei o SwaggerUI para interagir com a API.

Projeto pode ser testado online no endereço: https://apitransporteporto.herokuapp.com/swagger-ui.html

Contato: Tarcisio Machado dos Reis - e-mail: tarcisio.reis.ti@gmail.com ou whatsapp 051 9 8490-4355.

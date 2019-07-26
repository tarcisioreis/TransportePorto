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

ItinerarioController:

1) buscarPorLinha - via POST - parâmetro idlinha - filtra todos os itinerario de uma linha informada - mostra os dados da linha e latitude e longitude;


Requisitos para funcionamento:

1) Ter o Eclipse/Intellij como IDE com Maven para isso somente importar como Projeto Maven;

2) Ter um banco MySQL local ou remoto, configurar no diretório src/main/resources/application.properties os dados para aceso 
   ao banco.


Para ver os Web Services instalei o SwaggerUI para interagir com a API.



Contato: Tarcisio Machado dos Reis - e-mail: tarcisio.reis.ti@gmail.com ou whatsapp 051 9 8490-4355.

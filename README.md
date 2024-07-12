# Descrição do Projeto

Este foi um projeto criado com a finalidade de atualizar os nossos colaboradores acerca das O.S abertas no ERP interno, contemplando os canais : SMS, Whatsapp e E-mail.
O desenvolvimento conta com integração entre APIs com um fornecedor terceiro, chamado Zenvia. Ela nos fornece endpoints para os canais de SMS e Whatsapp.
A parte do e-mail é feita através do Outlook, com uma conta criada no domínio da empresa, e não necessita que se consuma uma API de terceiros.

Há também uma API que consulta nosso sistema de RH, o Senior, para reunir as informações de contato dos colaboradores, e então efetivar o envio das mensagens através dos canais citados.

Além do consumo de dados através de integração via API, o projeto utiliza o "jdbcTemplate" do Spring Data JPA para fazer queries no Banco de Dados do nosso ERP, o DB2. 

Por fim, o projeto conta com um banco de dados próprio, MySql, para armazenar algumas métricas referentes ao envio das mensagens.

O projeto é desenvolvido em Java Spring Boot, utilizando o Maven para gerenciar as suas dependências. 

O projeto foi desenvolvimento inteiramente por mim, se excluindo o conhecimento necessário para montar as queries ( dicionário de dados, tabelas e etc ...), que foi fornecida por um programador Sênior.





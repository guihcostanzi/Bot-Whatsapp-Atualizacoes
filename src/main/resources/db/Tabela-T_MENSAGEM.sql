/*CRIAÇÃO DAS TABELAS PARA O BOT WHATSAPP */

CREATE TABLE T_MENSAGEM (
	ID_MENSAGEM INT PRIMARY KEY AUTO_INCREMENT,
	SERVICO VARCHAR(30),
	DESTINATARIO VARCHAR(70) NOT NULL,
	NUMERO_OS INT NOT NULL,
	DATA_HORA_ENVIO DATETIME NOT NULL
);
package com.grupogbs.bot.entities.dtos.senior;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RespostaConsultaColabotadorDTO {
	
	private Integer empresa;
	private Integer matricula;
	private String nome;
	private String nomeCompleto;
	private Integer dddFixoA;
	private String telefoneFixoA;
	private Integer dddFixoB;
	private String telefoneFixoB;
	private String celular;
	private String emailParticular;
	private String emailEmpresa;

}

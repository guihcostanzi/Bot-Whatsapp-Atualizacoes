package com.grupogbs.bot.entities.dtos.delsoft;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemOrdemServicoDTO {
	
	private String relatoAbertura;
	private String usuarioExecucao;
	private String solucao;
	private String observacaoSolucao;
	
	public ItemOrdemServicoDTO(ConsultaOrdemServicoDTO consulta) {
		
		if(consulta.getMotivoAbertura() != null && !consulta.getMotivoAbertura().isBlank() && !consulta.getMotivoAbertura().equals("null")) 
			this.relatoAbertura = consulta.getMotivoAbertura().strip();
		else 
			this.relatoAbertura = "Não informado.";
		
		if(consulta.getUsuarioExecucao() != null) this.usuarioExecucao = consulta.getNomeUsuarioExecucao().strip();
		
		if(consulta.getResolucao() != null && !consulta.getResolucao().isBlank() && !consulta.getResolucao().equals("null")) 
			this.solucao = consulta.getResolucao().strip();
		else 
			this.solucao = "Não informado.";
		
		if(consulta.getObservacaoResolucao() != null && !consulta.getObservacaoResolucao().isBlank() && !consulta.getObservacaoResolucao().equals("null")) 
			this.observacaoSolucao = consulta.getObservacaoResolucao().strip();
		else 
			this.observacaoSolucao = "Não informado.";

	}

}

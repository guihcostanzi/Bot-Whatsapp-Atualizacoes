package com.grupogbs.bot.entities.dtos.delsoft;

import java.time.format.DateTimeFormatter;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrdemServicoDTO {

	private Long numeroOS;
	private Integer empresa;
	private String prefixoVeiculo;
	private String aberturaOS;
	private String encerramentoOS;
	private String matriculaUsuario;
	private String celularRequerente;
	private String emailRequerente;
	private String status;
	private List<ItemOrdemServicoDTO> itens;
	private String itensTexto;
	private String itensTextoWhatsApp;
	
	public OrdemServicoDTO(ConsultaOrdemServicoDTO consulta) {
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm");
		
		this.numeroOS = consulta.getNumeroOS();
		this.empresa = consulta.getEmpresa();
		this.prefixoVeiculo = consulta.getPrefixoVeiculo().strip();
		this.aberturaOS = consulta.getAberturaOS().format(dtf);
		this.encerramentoOS = consulta.getEncerramentoOS().format(dtf);
		this.matriculaUsuario = consulta.getMatriculaUsuario().strip();
		if(consulta.getCelularRequerente() != null && !consulta.getCelularRequerente().isBlank()) {
			this.celularRequerente = consulta.getCelularRequerente().strip();
		}
		if(consulta.getEmailRequerente() != null && !consulta.getEmailRequerente().isBlank()) {
			this.emailRequerente = consulta.getEmailRequerente().strip();
		}
		this.status = consulta.getStatus().strip();
		this.itens = List.of(new ItemOrdemServicoDTO(consulta));
		
	}
	
	@Override
	public String toString() {
		return "OrdemServicoDTO \n"
				+ "[ \n"
				+ " numeroOS= " + numeroOS + "\n"
				+ " empresa= " + empresa + "\n"
				+ " prefixoVeiculo= " + prefixoVeiculo + "\n"
				+ " aberturaOS= " + aberturaOS + "\n"
				+ " encerramentoOS= " + encerramentoOS + "\n"
				+ " matriculaUsuario= " + matriculaUsuario + "\n"
				+ " celularRequerente= " + celularRequerente + "\n"
				+ " emailRequerente= " + emailRequerente + "\n"
				+ " status= " + status + "\n\n"
				+ " itensTexto= \n\n" + itensTexto
				+ " itens= " + itens
				+ "\n" + "]";
	}
	
}

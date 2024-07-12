package com.grupogbs.bot.entities.dtos.whatsapp;

import com.grupogbs.bot.entities.dtos.delsoft.OrdemServicoDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FieldsWhatsappRequestDTO {
	private Long numero_os;
	private String prefixo_veiculo_os;
	private String abertura_os;
	private String encerramento_os;
	private String status_os;
	private String pontos_os;
	
	public FieldsWhatsappRequestDTO(OrdemServicoDTO os) {
		this.numero_os = os.getNumeroOS();
		this.prefixo_veiculo_os = os.getPrefixoVeiculo();
		this.abertura_os = os.getAberturaOS();
		this.encerramento_os = os.getEncerramentoOS();
		this.status_os = os.getStatus();
		this.pontos_os = os.getItensTextoWhatsApp();
	}
}

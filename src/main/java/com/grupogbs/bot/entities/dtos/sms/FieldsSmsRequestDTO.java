package com.grupogbs.bot.entities.dtos.sms;

import com.grupogbs.bot.entities.dtos.delsoft.OrdemServicoDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FieldsSmsRequestDTO {
	private Long numero_os;
	private String veiculo_os;
	private String abertura_os;
	private String encerramento_os;
	private String status_os;
	private String itens_os;
	
	public FieldsSmsRequestDTO(OrdemServicoDTO os) {
		this.numero_os = os.getNumeroOS();
		this.veiculo_os = os.getPrefixoVeiculo();
		this.abertura_os = os.getAberturaOS();
		this.encerramento_os = os.getEncerramentoOS();
		this.status_os = os.getStatus();
		this.itens_os = os.getItensTexto();
	}
}

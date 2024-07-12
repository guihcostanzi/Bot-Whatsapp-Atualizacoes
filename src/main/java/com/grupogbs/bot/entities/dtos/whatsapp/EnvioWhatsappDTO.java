package com.grupogbs.bot.entities.dtos.whatsapp;

import java.util.List;

import com.grupogbs.bot.entities.dtos.delsoft.OrdemServicoDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnvioWhatsappDTO {
	
	private String from;
	private String to;
	private List<ContentsWhatsappRequestDTO> contents;
	
	public EnvioWhatsappDTO(OrdemServicoDTO os, String sender, String templateId) {
		
		this.from = sender;
		this.to = os.getCelularRequerente();
		this.contents = List.of(new ContentsWhatsappRequestDTO(os, templateId));
	}

}

package com.grupogbs.bot.entities.dtos.sms;

import java.util.List;

import com.grupogbs.bot.entities.dtos.delsoft.OrdemServicoDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnvioSmsDTO {
	
	private String from;
	private String to;
	private List<ContentsSmsRequestDTO> contents;
	
	public EnvioSmsDTO(OrdemServicoDTO os, String sender, String templateId) {
		
		this.from = sender;
		this.to = os.getCelularRequerente();
		this.contents = List.of(new ContentsSmsRequestDTO(os, templateId));
	}

}

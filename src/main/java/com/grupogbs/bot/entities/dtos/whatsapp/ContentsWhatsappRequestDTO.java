package com.grupogbs.bot.entities.dtos.whatsapp;

import com.grupogbs.bot.entities.dtos.delsoft.OrdemServicoDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContentsWhatsappRequestDTO {
	private String type;
	private String templateId;
	private FieldsWhatsappRequestDTO fields;
	
	public ContentsWhatsappRequestDTO (OrdemServicoDTO os, String templateId) {
		this.type = "template";
		this.templateId = templateId;
		this.fields = new FieldsWhatsappRequestDTO(os);
	}
}

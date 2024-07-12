package com.grupogbs.bot.entities.dtos.sms;

import com.grupogbs.bot.entities.dtos.delsoft.OrdemServicoDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContentsSmsRequestDTO {
	private String type;
	private String templateId;
	private FieldsSmsRequestDTO fields;
	
	public ContentsSmsRequestDTO (OrdemServicoDTO os, String templateId) {
		this.type = "template";
		this.templateId = templateId;
		this.fields = new FieldsSmsRequestDTO(os);
	}
}

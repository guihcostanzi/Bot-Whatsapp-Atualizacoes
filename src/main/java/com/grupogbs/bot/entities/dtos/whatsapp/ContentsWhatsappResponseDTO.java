package com.grupogbs.bot.entities.dtos.whatsapp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContentsWhatsappResponseDTO {
	
	private String type;
	private String templateId;
	private FieldsWhatsappRequestDTO fields;
	
}

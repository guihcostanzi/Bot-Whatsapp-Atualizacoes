package com.grupogbs.bot.entities.dtos.sms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContentsSmsResponseDTO {
	
	private String type;
	private String templateId;
	private FieldsSmsRequestDTO fields;
	
}

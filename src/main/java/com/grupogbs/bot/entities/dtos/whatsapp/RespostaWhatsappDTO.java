package com.grupogbs.bot.entities.dtos.whatsapp;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RespostaWhatsappDTO {

	private String from;
	private String to;
	private List<ContentsWhatsappResponseDTO> contents;
	private String id;
	private String direction;
	
	@Override
	public String toString() {
		return "RespostaWhatsappDTO [from=" + from + ", to=" + to + ", contents=" + contents + ", id=" + id
				+ ", direction=" + direction + "]";
	}
	
	
}

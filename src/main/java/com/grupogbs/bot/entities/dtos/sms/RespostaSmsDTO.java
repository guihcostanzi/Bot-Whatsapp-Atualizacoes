package com.grupogbs.bot.entities.dtos.sms;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RespostaSmsDTO {

	private String from;
	private String to;
	private List<ContentsSmsResponseDTO> contents;
	private String id;
	private String direction;
	
	@Override
	public String toString() {
		return "RespostaSmsDTO [from=" + from + ", to=" + to + ", contents=" + contents + ", id=" + id + ", direction="
				+ direction + "]";
	}
	
}

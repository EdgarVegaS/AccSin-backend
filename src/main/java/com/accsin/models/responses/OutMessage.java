package com.accsin.models.responses;

import java.util.Date;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OutMessage {
	public static enum MessageTipe {
		OK,
		WARNING,
		ERROR
	}
	private MessageTipe messageTipe;
	private String message;
	private String detail;

	private Object object;
}

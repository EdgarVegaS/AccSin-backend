package com.accsin.models.responses;

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

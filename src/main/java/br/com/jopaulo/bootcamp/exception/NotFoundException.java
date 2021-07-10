package br.com.jopaulo.bootcamp.exception;

import br.com.jopaulo.bootcamp.util.MessageUtils;

public class NotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public NotFoundException() {
		super(MessageUtils.NO_RECORDS_FOUND);
	}
}

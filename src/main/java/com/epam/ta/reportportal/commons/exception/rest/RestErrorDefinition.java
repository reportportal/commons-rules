/*
 * Copyright 2016 EPAM Systems
 * 
 * 
 * This file is part of EPAM Report Portal.
 * https://github.com/epam/ReportPortal
 * 
 * Report Portal is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Report Portal is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Report Portal.  If not, see <http://www.gnu.org/licenses/>.
 */ 

package com.epam.ta.reportportal.commons.exception.rest;

import com.epam.ta.reportportal.commons.exception.message.ExceptionMessageBuilder;
import com.epam.ta.reportportal.ws.model.ErrorType;
import org.springframework.http.HttpStatus;

/**
 * REST Error template. Created to be able to configure error templates in
 * Spring's IoC container
 * 
 * @author Andrei Varabyeu
 * 
 */
public class RestErrorDefinition<T extends Exception> {

	private final HttpStatus httpStatus;
	private final ErrorType error;
	private final ExceptionMessageBuilder<T> exceptionMessageBuilder;

	public RestErrorDefinition(HttpStatus httpStatus, ErrorType error, ExceptionMessageBuilder<T> exceptionMessageBuilder) {
		super();
		this.httpStatus = httpStatus;
		this.error = error;
		this.exceptionMessageBuilder = exceptionMessageBuilder;
	}

	public RestErrorDefinition(int httpStatus, ErrorType error, ExceptionMessageBuilder<T> exceptionMessageBuilder) {
		this(HttpStatus.valueOf(httpStatus), error, exceptionMessageBuilder);
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public ErrorType getError() {
		return error;
	}

	public String getExceptionMessage(T e) {
		return exceptionMessageBuilder.buildMessage(e);
	}

	public ExceptionMessageBuilder<? extends Exception> getExceptionMessageBuilder() {
		return exceptionMessageBuilder;
	}
}
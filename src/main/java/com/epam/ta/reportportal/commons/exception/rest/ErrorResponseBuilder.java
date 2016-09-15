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

import com.epam.ta.reportportal.ws.model.ErrorRS;
import com.epam.ta.reportportal.ws.model.ErrorType;

/**
 * Error response builder
 * 
 * @author Andrei Varabyeu
 * 
 */
public class ErrorResponseBuilder {

	private RestErrorDefinition definition;

	private ErrorType error;
	private String message;
	private String stackTrace;

	public ErrorResponseBuilder(RestErrorDefinition definition) {
		this.error = definition.getError();
		this.definition = definition;
	}

	public ErrorResponseBuilder setMessage(Exception ex) {
		this.message = definition.getExceptionMessage(ex);
		return this;
	}

	public ErrorResponseBuilder setStackTrace(String stackTrace) {
		this.stackTrace = stackTrace;
		return this;
	}

	public ErrorRS build() {
		ErrorRS errorRS = new ErrorRS();
		errorRS.setMessage(message);
		errorRS.setStackTrace(stackTrace);
		errorRS.setErrorType(error);
		return errorRS;
	}

}
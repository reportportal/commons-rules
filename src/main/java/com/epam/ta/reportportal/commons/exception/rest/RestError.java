/*
 * Copyright 2016 EPAM Systems
 * 
 * 
 * This file is part of EPAM Report Portal.
 * https://github.com/reportportal/commons-rules
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
import org.springframework.http.HttpStatus;

/**
 * Rest Error representation. Contains rest error template and real exception
 * data
 * 
 * @author Andrei Varabyeu
 * 
 */
public class RestError {

	private final ErrorRS errorRS;

	/** HTTP Status */
	private final HttpStatus httpStatus;

	public RestError(HttpStatus httpStatus, ErrorRS errorRS) {
		this.httpStatus = httpStatus;
		this.errorRS = errorRS;
	}

	public ErrorRS getErrorRS() {
		return errorRS;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	/**
	 * Builder for Rest Error
	 * 
	 * @author Andrei Varabyeu
	 * 
	 */
	public static class Builder {

		private HttpStatus status;
		private ErrorType error;
		private String message;
		private String stackTrace;

		public Builder setStatus(HttpStatus status) {
			this.status = status;
			return this;
		}

		public Builder setError(ErrorType error) {
			this.error = error;
			return this;
		}

		public Builder setMessage(String message) {
			this.message = message;
			return this;
		}

		public Builder setStackTrace(String stackTrace) {
			this.stackTrace = stackTrace;
			return this;
		}

		public RestError build() {
			ErrorRS errorRS = new ErrorRS();
			errorRS.setMessage(message);
			errorRS.setStackTrace(stackTrace);
			errorRS.setErrorType(error);

			return new RestError(status, errorRS);
		}
	}
}
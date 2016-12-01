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

import com.epam.ta.reportportal.exception.ReportPortalException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

/**
 * Resolver for ReportPortal Exceptions
 * 
 * @author Andrei Varabyeu
 * 
 */
public class ReportPortalExceptionResolver implements ErrorResolver {
	private static final Logger LOGGER = LoggerFactory.getLogger(ReportPortalExceptionResolver.class);

	private ErrorResolver defaultErrorResolver;

	public ReportPortalExceptionResolver(ErrorResolver defaultErrorResolver) {
		this.defaultErrorResolver = defaultErrorResolver;
	}

	@Override
	public RestError resolveError(Exception ex) {

		LOGGER.error("ReportPortalExceptionResolver > " + ex.getMessage(), ex);

		if (ReportPortalException.class.isAssignableFrom(ex.getClass())) {
			ReportPortalException currentException = (ReportPortalException) ex;
			RestError.Builder builder = new RestError.Builder();
			builder.setMessage(currentException.getMessage())
					// .setStackTrace(errors.toString())
					.setStatus(StatusCodeMapping.getHttpStatus(currentException.getErrorType(), HttpStatus.INTERNAL_SERVER_ERROR))
					.setError(currentException.getErrorType());

			return builder.build();
		} else {
			return defaultErrorResolver.resolveError(ex);
		}
	}
}
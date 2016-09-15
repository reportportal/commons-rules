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

package com.epam.ta.reportportal.ws.exception.rest;

import com.epam.ta.reportportal.commons.ExceptionMappings;
import com.epam.ta.reportportal.commons.exception.rest.DefaultErrorResolver;
import com.epam.ta.reportportal.commons.exception.rest.ErrorResolver;
import com.epam.ta.reportportal.commons.exception.rest.RestError;
import com.epam.ta.reportportal.exception.ReportPortalException;
import com.epam.ta.reportportal.ws.model.ErrorType;
import com.google.common.base.Strings;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

/**
 * Error Resolver Tests
 *
 * @author Andrei Varabyeu
 */
@RunWith(Parameterized.class)
public class ExceptionHandlingTest {

	private static final String EXCEPTION_MESSAGE = "Some exception";

	private ErrorResolver errorResolver = new DefaultErrorResolver(ExceptionMappings.DEFAULT_MAPPING);

	private Exception exception;

	private ErrorType errorType;

	private HttpStatus httpStatus;

	private String errorMessage;

	public ExceptionHandlingTest(Exception exception, ErrorType errorType, HttpStatus httpStatus, String errorMessage) {
		this.exception = exception;
		this.errorType = errorType;
		this.httpStatus = httpStatus;
		this.errorMessage = errorMessage;
	}

	@Parameterized.Parameters(name = "{index}:{0},{1},{2}")
	public static List<Object[]> getParameters() {
		return Arrays.asList(new Object[][] { { new ReportPortalException(EXCEPTION_MESSAGE), ErrorType.UNCLASSIFIED_REPORT_PORTAL_ERROR,
				HttpStatus.INTERNAL_SERVER_ERROR, EXCEPTION_MESSAGE },
				{ new RuntimeException(EXCEPTION_MESSAGE), ErrorType.UNCLASSIFIED_ERROR, HttpStatus.INTERNAL_SERVER_ERROR,
						EXCEPTION_MESSAGE },
				{ new HttpMessageNotReadableException(EXCEPTION_MESSAGE), ErrorType.INCORRECT_REQUEST, HttpStatus.BAD_REQUEST,
						EXCEPTION_MESSAGE },
				{ new MissingServletRequestParameterException("test", "test"), ErrorType.INCORRECT_REQUEST, HttpStatus.BAD_REQUEST,
						"Required test parameter 'test' is not present" } });
	}

	@Test
	public void testErrorHandlingException()
			throws IllegalArgumentException, SecurityException, InstantiationException, IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {
		RestError restError = errorResolver.resolveError(exception);
		validate(restError, errorType, httpStatus, errorMessage);
	}

	private void validate(RestError restError, ErrorType errorType, HttpStatus httpStatus, String exceptionMessage) {
		Assert.assertThat(restError.getErrorRS().getMessage(),
				Matchers.containsString(Strings.isNullOrEmpty(exceptionMessage) ? EXCEPTION_MESSAGE : exceptionMessage));
		Assert.assertEquals(errorType, restError.getErrorRS().getErrorType());
		Assert.assertEquals(httpStatus, restError.getHttpStatus());
	}

}
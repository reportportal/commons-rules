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
package com.epam.ta.reportportal.commons;

import com.epam.ta.reportportal.commons.exception.message.ArgumentNotValidMessageBuilder;
import com.epam.ta.reportportal.commons.exception.message.DefaultExceptionMessageBuilder;
import com.epam.ta.reportportal.commons.exception.message.ExceptionMessageBuilder;
import com.epam.ta.reportportal.commons.exception.rest.RestErrorDefinition;
import com.epam.ta.reportportal.exception.ReportPortalException;
import com.epam.ta.reportportal.ws.model.ErrorType;
import com.google.common.collect.ImmutableMap;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.mongodb.InvalidMongoDbApiUsageException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import java.util.Map;

/**
 * Set of default exception mappings
 *
 * @author Andrei Varabyeu
 */
public final class ExceptionMappings {

	private static ReloadableResourceBundleMessageSource MESSAGE_SOURCE = new ReloadableResourceBundleMessageSource(){
		{
			setBasename("classpath:ValidationMessages");
			setDefaultEncoding("UTF-8");
		}
	};

	private static final ExceptionMessageBuilder<Exception> DEFAULT_MESSAGE_BUILDER = new DefaultExceptionMessageBuilder();

	public static Map<Class<? extends Throwable>, RestErrorDefinition> DEFAULT_MAPPING = ImmutableMap
			.<Class<? extends Throwable>, RestErrorDefinition>builder()
			.put(MethodArgumentNotValidException.class, new RestErrorDefinition<>(400, ErrorType.INCORRECT_REQUEST,
					new ArgumentNotValidMessageBuilder(MESSAGE_SOURCE)))

			.put(HttpMessageNotReadableException.class, new RestErrorDefinition<>(400, ErrorType.INCORRECT_REQUEST, DEFAULT_MESSAGE_BUILDER))
			.put(MissingServletRequestPartException.class, new RestErrorDefinition<>(400, ErrorType.INCORRECT_REQUEST, DEFAULT_MESSAGE_BUILDER))
			.put(MissingServletRequestParameterException.class, new RestErrorDefinition<>(400, ErrorType.INCORRECT_REQUEST, DEFAULT_MESSAGE_BUILDER))
			.put(AccessDeniedException.class, new RestErrorDefinition<>(403, ErrorType.ACCESS_DENIED, DEFAULT_MESSAGE_BUILDER))
			.put(BadCredentialsException.class, new RestErrorDefinition<>(401, ErrorType.ACCESS_DENIED, DEFAULT_MESSAGE_BUILDER))
			.put(LockedException.class, new RestErrorDefinition<>(403, ErrorType.ADDRESS_LOCKED, DEFAULT_MESSAGE_BUILDER))
			.put(ReportPortalException.class, new RestErrorDefinition<>(500, ErrorType.UNCLASSIFIED_REPORT_PORTAL_ERROR, DEFAULT_MESSAGE_BUILDER))

			//TODO move to DAO
			.put(InvalidMongoDbApiUsageException.class, new RestErrorDefinition<>(400, ErrorType.INCORRECT_FILTER_PARAMETERS, DEFAULT_MESSAGE_BUILDER))
			.put(RestClientException.class, new RestErrorDefinition<>(400, ErrorType.UNABLE_INTERACT_WITH_EXTRERNAL_SYSTEM, DEFAULT_MESSAGE_BUILDER))

			.put(Throwable.class, new RestErrorDefinition<>(500, ErrorType.UNCLASSIFIED_ERROR, DEFAULT_MESSAGE_BUILDER))
				.build();
}
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

import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Default implementation of ErrorResolver
 * 
 * @author Andrei Varabyeu
 * 
 */
public class DefaultErrorResolver implements ErrorResolver {
	private static final Logger LOGGER = LoggerFactory.getLogger(DefaultErrorResolver.class);

	private Map<Class<? extends Throwable>, RestErrorDefinition> exceptionMappingDefinitions;

	public DefaultErrorResolver(Map<Class<? extends Throwable>, RestErrorDefinition> exceptionMappingDefinitions) {
		Preconditions.checkNotNull(exceptionMappingDefinitions, "Exceptions mappings should't be null");
		this.exceptionMappingDefinitions = exceptionMappingDefinitions;
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.epam.ta.reportportal.ws.exception.ErrorResolver#resolveError(java
	 * .lang.Exception)
	 */
	@Override
	public RestError resolveError(Exception ex) {
		RestErrorDefinition errorDefinition = getRestErrorDefinition(ex);
		if (null == errorDefinition) {
			return null;
		}

		LOGGER.error(ex.getMessage(), ex);

		RestError.Builder errorBuilder = new RestError.Builder();
		errorBuilder.setError(errorDefinition.getError())
				.setMessage(new StringBuilder(errorDefinition.getError().getDescription()).append(" [")
						.append(errorDefinition.getExceptionMessage(ex)).append("]").toString())
				// .setStackTrace(errors.toString())
				.setStatus(errorDefinition.getHttpStatus());

		return errorBuilder.build();

	}

	/**
	 * Returns the config-time 'template' RestErrorDefinition instance
	 * configured for the specified Exception, or {@code null} if a match was
	 * not found.
	 * <p/>
	 * The config-time template is used as the basis for the RestError
	 * constructed at runtime.
	 * 
	 * @param ex
	 * @return the template to use for the RestError instance to be constructed.
	 */
	private RestErrorDefinition getRestErrorDefinition(Exception ex) {
		Map<Class<? extends Throwable>, RestErrorDefinition> mappings = this.exceptionMappingDefinitions;
		if (mappings.isEmpty()) {
			return null;
		}
		RestErrorDefinition template = null;
		int deepest = Integer.MAX_VALUE;
		for (Map.Entry<Class<? extends Throwable>, RestErrorDefinition> entry : mappings.entrySet()) {
			Class<? extends Throwable> key = entry.getKey();
			int depth = getDepth(key, ex);
			if (depth >= 0 && depth < deepest) {
				deepest = depth;
				template = entry.getValue();
			}
		}
		return template;
	}

	/**
	 * Return the depth to the superclass matching.
	 * <p>
	 * 0 means ex matches exactly. Returns -1 if there's no match. Otherwise,
	 * returns depth. Lowest depth wins.
	 */
	protected int getDepth(Class<? extends Throwable> exceptionMapping, Exception ex) {
		return getDepth(exceptionMapping, ex.getClass(), 0);
	}

	private int getDepth(Class<? extends Throwable> exceptionMapping, Class<?> exceptionClass, int depth) {
		if (exceptionClass.equals(exceptionMapping)) {
			// Found it!
			return depth;
		}
		// If we've gone as far as we can go and haven't found it...
		if (exceptionClass.equals(Throwable.class)) {
			return -1;
		}
		return getDepth(exceptionMapping, exceptionClass.getSuperclass(), depth + 1);
	}

}
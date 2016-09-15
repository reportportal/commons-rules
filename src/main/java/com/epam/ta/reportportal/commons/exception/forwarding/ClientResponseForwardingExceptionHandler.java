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
package com.epam.ta.reportportal.commons.exception.forwarding;

import com.google.common.io.ByteStreams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * {@link HandlerExceptionResolver}
 * Checks of exception contains response of downstream service
 * and copies it to upstream response
 *
 * @author Andrei Varabyeu
 */
public class ClientResponseForwardingExceptionHandler implements HandlerExceptionResolver, Ordered {

	private static final Logger LOGGER = LoggerFactory.getLogger(ClientResponseForwardingExceptionHandler.class);

	private int order;

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		/* just forward upstream */
		if (ResponseForwardingException.class.isAssignableFrom(ex.getClass())) {
			ResponseForwardingException forwardingException = ((ResponseForwardingException) ex);
			try (ByteArrayInputStream dataStream = new ByteArrayInputStream(forwardingException.getBody())) {

				//copy status
				response.setStatus(forwardingException.getStatus().value());

				//copy headers
				response.setContentType(forwardingException.getHeaders().getContentType().toString());

				//copy body
				ByteStreams.copy(dataStream, response.getOutputStream());

				// return empty model and view to short circuit the
				// iteration and to let
				// Spring know that we've rendered the view ourselves:
				return new ModelAndView();

			} catch (IOException e) {
				LOGGER.error("Cannot forward exception", e);
				return null;
			}
		}

		return null;
	}

	@Override
	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}
}
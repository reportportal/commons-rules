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
 
package com.epam.ta.reportportal.commons.exception.message;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;

/**
 * {@link MethodArgumentNotValidException} message builder
 * 
 * @author Andrei Varabyeu
 * 
 */
public class ArgumentNotValidMessageBuilder implements ExceptionMessageBuilder<MethodArgumentNotValidException> {

	private ReloadableResourceBundleMessageSource messageSource;

	public ArgumentNotValidMessageBuilder() {
	}

	public ArgumentNotValidMessageBuilder(ReloadableResourceBundleMessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@Override
	public String buildMessage(MethodArgumentNotValidException e) {
		StringBuilder sb = new StringBuilder();
		for (ObjectError error : e.getBindingResult().getAllErrors()) {
			sb.append("[").append(messageSource.getMessage(error, LocaleContextHolder.getLocale())).append("] ");
		}
		return sb.toString();
	}

	public ReloadableResourceBundleMessageSource getMessageSource() {
		return messageSource;
	}

	public void setMessageSource(ReloadableResourceBundleMessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	

}
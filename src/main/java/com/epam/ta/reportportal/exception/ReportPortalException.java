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

package com.epam.ta.reportportal.exception;

import com.epam.ta.reportportal.commons.validation.Suppliers;
import com.epam.ta.reportportal.ws.model.ErrorType;

import static com.epam.ta.reportportal.commons.validation.Suppliers.trimMessage;

/**
 * Base Report Portal Exception
 *
 * @author Andrei Varabyeu
 */
public class ReportPortalException extends RuntimeException {
	public static final int MAX_ERROR_MESSAGE_LENGTH = 10000;

	private static final long serialVersionUID = -7599195984281555977L;

	private ErrorType errorType;
	private Object[] parameters; //NOSONAR

	public ReportPortalException(String message) {
		super(message);
	}

	public ReportPortalException(ErrorType errorType, Object... parameters) {
		super(trimMessage(Suppliers.formattedSupplier(errorType.getDescription(), parameters).get(), MAX_ERROR_MESSAGE_LENGTH));
		this.errorType = errorType;
		this.parameters = parameters;
	}

	public ReportPortalException(String message, Throwable e) {
		super(trimMessage(message, MAX_ERROR_MESSAGE_LENGTH), e);
	}

	public ErrorType getErrorType() {
		return errorType;
	}

	public Object[] getParameters() {
		return parameters;
	}

}
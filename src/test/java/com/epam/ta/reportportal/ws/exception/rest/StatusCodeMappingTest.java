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

import com.epam.ta.reportportal.commons.exception.rest.StatusCodeMapping;
import com.epam.ta.reportportal.ws.model.ErrorType;
import org.junit.Assert;
import org.junit.Test;

/**
 * Status Code mapping test
 * 
 * @author Andrei Varabyeu
 * 
 */
public class StatusCodeMappingTest {

	@Test
	public void testMapping() {
		StringBuilder errors = new StringBuilder();
		for (ErrorType errorType : ErrorType.values()) {
			if (!StatusCodeMapping.getHttpStatus(errorType).isPresent()) {
				errors.append("Error type ").append(errorType).append(" is not mapped").append("\n");
			}
		}

		Assert.assertTrue(errors.toString(), errors.length() == 0);
	}
}
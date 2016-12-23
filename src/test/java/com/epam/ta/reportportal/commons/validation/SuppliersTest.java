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
 
package com.epam.ta.reportportal.commons.validation;

import com.epam.ta.reportportal.exception.ReportPortalException;
import com.epam.ta.reportportal.ws.model.ErrorType;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

/**
 * Several tests for {@link Suppliers}
 * 
 * @author Andrei Varabyeu
 * 
 */
public class SuppliersTest {

	@Test
	public void testSimpleSupplier() {
		String demoString = "demo string";
		String suppliedString = Suppliers.stringSupplier(demoString).get();
		Assert.assertEquals("Initial and supplied strings are not equal", demoString, suppliedString);
	}

	@Test
	public void testFormattedSupplier() {
		String demoString = "here is {} parameter";
		String suppliedString = Suppliers.formattedSupplier(demoString, "first").get();
		Assert.assertEquals("Initial and supplied strings are not equal", "here is first parameter", suppliedString);
	}

	@Test
	public void checkNoParamsInMessage() {
		String demoString = "here is parameter {}";
		String suppliedString = Suppliers.formattedSupplier(demoString).get();
		Assert.assertThat("Incorrect message builder", suppliedString, Matchers.is("here is parameter"));
	}

	@Test
	public void checkParamsInMessage() {
		String demoString = "here is parameter {}";
		String suppliedString = Suppliers.formattedSupplier(demoString, ":hello world").get();
		Assert.assertThat("Incorrect message builder", suppliedString, Matchers.is("here is parameter :hello world"));
	}

}
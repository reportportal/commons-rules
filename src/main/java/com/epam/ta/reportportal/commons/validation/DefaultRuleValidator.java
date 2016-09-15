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
 
package com.epam.ta.reportportal.commons.validation;

import com.epam.ta.reportportal.exception.ReportPortalException;

import java.util.function.Predicate;
import java.util.function.Supplier;

public class DefaultRuleValidator<T> extends RuleValidator<T> {

    private final Supplier<String> message;

    public DefaultRuleValidator(T target, Predicate<T> predicate,
            Supplier<String> message) {
        super(target, predicate);
        this.message = message;
    }

    /**
     * Verifies predicate and throws {@link BusinessRuleViolationException} is
     * there is violation
     * 
     * @throws BusinessRuleViolationException
     */
    public void verify() throws BusinessRuleViolationException {
        if (!predicate.test(target)) {
            throw new BusinessRuleViolationException(message.get());
        }
    }

    /**
     * Verifies predicate and throws {@link ReportPortalException} if there is
     * violation
     * 
     * @param t
     *            - exception to thrown instead of {@link ReportPortalException}
     */
    public void verify(Class<? extends ReportPortalException> t) {
        if (!predicate.test(target)) {
            ReportPortalException toBeThrowed;
            try {
                toBeThrowed = t.getConstructor(String.class).newInstance(
                        message.get());
            } catch (Exception e) {
                throw new ReportPortalException(message.get(), e);
            }
            throw toBeThrowed;
        }
    }

}
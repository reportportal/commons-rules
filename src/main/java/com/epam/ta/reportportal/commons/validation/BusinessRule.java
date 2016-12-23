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


import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Domain objects verification utility, based on predicates as business rules.
 *
 * @author Dzianis Shlychkou
 */
public class BusinessRule {

    private BusinessRule() {
        //should be create by factory-methods only
    }

    /**
     * Create rule from object to be verified, predicate and error message in
     * case of violation
     *
     * @param <T> Type of object being checked
     * @param object    Object to be validated
     * @param predicate Validation predicate
     * @param message   Error message
     * @return Validator
     * @deprecated in favor of {@link #expect(Object, Predicate, Supplier)}<br>
     * This approach would be better in case you need to concatenate
     * some error string
     */
    @Deprecated
    public static <T> DefaultRuleValidator<T> expect(T object, Predicate<T> predicate, String message) {
        return new DefaultRuleValidator<>(object, predicate, Suppliers.stringSupplier(message));
    }

    /**
     * Create rule from object to be verified, predicate and error message in
     * case of violation
     *
     * @param <T> Type of object being checked
     * @param object          Object to be validated
     * @param predicate       Validation predicate
     * @param messageSupplier Error message supplier
     * @return Validator
     */
    public static <T> DefaultRuleValidator<T> expect(T object, Predicate<T> predicate,
            Supplier<String> messageSupplier) {
        return new DefaultRuleValidator<>(object, predicate, messageSupplier);
    }

    /**
     * Create rule from object to be verified, predicate
     *
     * @param <T> Type of object being checked
     * @param object    Object to be validated
     * @param predicate Validation predicate
     * @return Validator
     */
    public static <T> ErrorTypeBasedRuleValidator<T> expect(T object, Predicate<T> predicate) {
        return new ErrorTypeBasedRuleValidator<>(object, predicate);
    }

    /**
     * For cases where we are going to fail something
     *
     * @return {@link AlwaysFailRuleValidator}
     */
    public static AlwaysFailRuleValidator fail() {
        return new AlwaysFailRuleValidator();
    }
}

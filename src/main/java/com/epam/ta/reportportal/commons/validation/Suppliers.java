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

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.CharMatcher;
import com.google.common.base.Strings;
import org.slf4j.helpers.MessageFormatter;

import java.util.function.Supplier;

import static com.google.common.base.Strings.isNullOrEmpty;

/**
 * Several useful suppliers.
 *
 * @author Andrei Varabyeu
 */
public class Suppliers {

    private static final CharMatcher REPLACEMENT_SYMBOLS = CharMatcher.anyOf("{}");

    private Suppliers() {

    }

    /**
     * Preemptive supplier for string. Do not do any actions with string
     *
     * @param string String to be supplied
     * @return Supplied String
     */
    public static Supplier<String> stringSupplier(final String string) {
        return () -> string;
    }

    /**
     * Formatted supplier. Applies {@link MessageFormatter} to
     * be able to pass formatted string with parameters as we did int slf4j.
     * Good approach to avoid string concatenation before we really need it
     *
     * @param string String to be supplied
     * @param parameters Formatter parameters
     * @return Supplied String
     */
    public static Supplier<String> formattedSupplier(final String string, final Object... parameters) {
        return new Supplier<String>() {
            @Override
            public String get() {
                return clearPlaceholders(MessageFormatter.arrayFormat(string, parameters).getMessage());
            }

            @Override
            public String toString() {
                return get();
            }
        };
    }

    /**
     * Clears placeholders in the message
     *
     * @param message Message to be cleared
     * @return Cleared string
     */
    @VisibleForTesting
    public static String clearPlaceholders(String message) {
        String cleared = message;
        if (formattedMessage(message)) {
            cleared = REPLACEMENT_SYMBOLS.removeFrom(message).trim();
        }
        return cleared;
    }

    /**
     * Checks whether placeholder characters are present in the string
     * @param str String to check
     * @return TRUE if at least one placeholder symbol is present
     */
    public static boolean formattedMessage(String str) {
        return !isNullOrEmpty(str) && REPLACEMENT_SYMBOLS.matchesAnyOf(str);
    }
}

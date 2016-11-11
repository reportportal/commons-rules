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

import com.epam.ta.reportportal.ws.model.ErrorType;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * {@link ErrorType} to {@link HttpStatus} mapping
 * 
 * @author Andrei Varabyeu
 * 
 */
public class StatusCodeMapping {

	private StatusCodeMapping() {

	}

	private static final Map<ErrorType, HttpStatus> MAPPING = new HashMap<ErrorType, HttpStatus>() {
		private static final long serialVersionUID = 1L;

		{
			put(ErrorType.PROJECT_NOT_FOUND, HttpStatus.NOT_FOUND);
			put(ErrorType.LAUNCH_NOT_FOUND, HttpStatus.NOT_FOUND);
			put(ErrorType.TEST_ITEM_NOT_FOUND, HttpStatus.NOT_FOUND);
			put(ErrorType.LOG_NOT_FOUND, HttpStatus.NOT_FOUND);
			put(ErrorType.ROLE_NOT_FOUND, HttpStatus.NOT_FOUND);
			put(ErrorType.USER_NOT_FOUND, HttpStatus.NOT_FOUND);
			put(ErrorType.WIDGET_NOT_FOUND, HttpStatus.NOT_FOUND);
			put(ErrorType.WIDGET_NOT_FOUND_IN_DASHBOARD, HttpStatus.NOT_FOUND);
			put(ErrorType.DASHBOARD_NOT_FOUND, HttpStatus.NOT_FOUND);
			put(ErrorType.USER_FILTER_NOT_FOUND, HttpStatus.NOT_FOUND);
			put(ErrorType.TEST_SUITE_NOT_FOUND, HttpStatus.NOT_FOUND);
			put(ErrorType.ACTIVITY_NOT_FOUND, HttpStatus.NOT_FOUND);
			put(ErrorType.ISSUE_TYPE_NOT_FOUND, HttpStatus.NOT_FOUND);
			put(ErrorType.PROJECT_SETTINGS_NOT_FOUND, HttpStatus.NOT_FOUND);

			put(ErrorType.RESOURCE_ALREADY_EXISTS, HttpStatus.CONFLICT);
			put(ErrorType.PROJECT_ALREADY_EXISTS, HttpStatus.CONFLICT);
			put(ErrorType.USER_ALREADY_EXISTS, HttpStatus.CONFLICT);
			put(ErrorType.USER_FILTER_ALREADY_EXISTS, HttpStatus.CONFLICT);
			put(ErrorType.ROLE_ALREADY_EXISTS_ERROR, HttpStatus.CONFLICT);

			put(ErrorType.FINISH_TIME_EARLIER_THAN_START_TIME, HttpStatus.NOT_ACCEPTABLE);
			put(ErrorType.CHILD_START_TIME_EARLIER_THAN_PARENT, HttpStatus.NOT_ACCEPTABLE);
			put(ErrorType.INCORRECT_FINISH_STATUS, HttpStatus.NOT_ACCEPTABLE);
			put(ErrorType.LAUNCH_IS_NOT_FINISHED, HttpStatus.NOT_ACCEPTABLE);
			put(ErrorType.TEST_ITEM_IS_NOT_FINISHED, HttpStatus.NOT_ACCEPTABLE);
			put(ErrorType.FINISH_LAUNCH_NOT_ALLOWED, HttpStatus.NOT_ACCEPTABLE);
			put(ErrorType.START_ITEM_NOT_ALLOWED, HttpStatus.NOT_ACCEPTABLE);
			put(ErrorType.FINISH_ITEM_NOT_ALLOWED, HttpStatus.NOT_ACCEPTABLE);
			put(ErrorType.LOGGING_IS_NOT_ALLOWED, HttpStatus.NOT_ACCEPTABLE);
			put(ErrorType.REPORTING_ITEM_ALREADY_FINISHED, HttpStatus.NOT_ACCEPTABLE);
			put(ErrorType.UNABLE_TO_CREATE_WIDGET, HttpStatus.NOT_ACCEPTABLE);
			put(ErrorType.UNABLE_LOAD_TEST_ITEM_HISTORY, HttpStatus.CONFLICT);

			put(ErrorType.DASHBOARD_UPDATE_ERROR, HttpStatus.CONFLICT);
			put(ErrorType.BAD_SAVE_USER_FILTER_REQUEST, HttpStatus.BAD_REQUEST);
			put(ErrorType.BAD_SAVE_LOG_REQUEST, HttpStatus.BAD_REQUEST);
			put(ErrorType.UNSUPPORTED_TEST_ITEM_TYPE, HttpStatus.BAD_REQUEST);
			put(ErrorType.BAD_SAVE_WIDGET_REQUEST, HttpStatus.BAD_REQUEST);
			put(ErrorType.BAD_UPDATE_WIDGET_REQUEST, HttpStatus.BAD_REQUEST);
			put(ErrorType.FAILED_TEST_ITEM_ISSUE_TYPE_DEFINITION, HttpStatus.BAD_REQUEST);
			put(ErrorType.BAD_UPDATE_PREFERENCE_REQUEST, HttpStatus.BAD_REQUEST);

			put(ErrorType.UNABLE_LOAD_WIDGET_CONTENT, HttpStatus.CONFLICT);

			put(ErrorType.INCORRECT_FILTER_PARAMETERS, HttpStatus.BAD_REQUEST);
			put(ErrorType.INCORRECT_SORTING_PARAMETERS, HttpStatus.BAD_REQUEST);

			// ExternalSystem related
			put(ErrorType.EXTERNAL_SYSTEM_NOT_FOUND, HttpStatus.NOT_FOUND);
			put(ErrorType.EXTERNAL_SYSTEM_ALREADY_EXISTS, HttpStatus.CONFLICT);
			put(ErrorType.PROJECT_NOT_CONFIGURED, HttpStatus.NOT_FOUND);
			put(ErrorType.INCORRECT_AUTHENTICATION_TYPE, HttpStatus.BAD_REQUEST);
			put(ErrorType.INCORRECT_EXTERNAL_SYSTEM_NAME, HttpStatus.BAD_REQUEST);
			put(ErrorType.UNABLE_INTERACT_WITH_EXTRERNAL_SYSTEM, HttpStatus.CONFLICT);
			// ======================

			// Server Settings related
			put(ErrorType.SERVER_SETTINGS_NOT_FOUND, HttpStatus.NOT_FOUND);
			put(ErrorType.SERVER_SETTINGS_ALREADY_EXISTS, HttpStatus.CONFLICT);
			// ======================

			/* Authentication related */
			put(ErrorType.ACCESS_DENIED, HttpStatus.FORBIDDEN);
			put(ErrorType.ADDRESS_LOCKED, HttpStatus.FORBIDDEN);
			put(ErrorType.SESSION_EXPIRED, HttpStatus.UNAUTHORIZED);

			put(ErrorType.INCORRECT_REQUEST, HttpStatus.BAD_REQUEST);
			put(ErrorType.BAD_REQUEST_ERROR, HttpStatus.BAD_REQUEST);
			put(ErrorType.AMBIGUOUS_TEST_ITEM_STATUS, HttpStatus.BAD_REQUEST);
			put(ErrorType.UNCLASSIFIED_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
			put(ErrorType.UNCLASSIFIED_REPORT_PORTAL_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
			put(ErrorType.BINARY_DATA_CANNOT_BE_SAVED, HttpStatus.BAD_REQUEST);
			put(ErrorType.UNABLE_POST_TICKET, HttpStatus.BAD_REQUEST);
			put(ErrorType.FORBIDDEN_OPERATION, HttpStatus.BAD_REQUEST);
			put(ErrorType.UNABLE_ADD_TO_FAVORITE, HttpStatus.BAD_REQUEST);
			put(ErrorType.UNABLE_MODIFY_SHARABLE_RESOURCE, HttpStatus.UNPROCESSABLE_ENTITY);
			put(ErrorType.UNABLE_REMOVE_FROM_FAVORITE, HttpStatus.BAD_REQUEST);
			put(ErrorType.PROJECT_DOESNT_CONTAIN_USER, HttpStatus.UNPROCESSABLE_ENTITY);
			put(ErrorType.UNABLE_ASSIGN_UNASSIGN_USER_TO_PROJECT, HttpStatus.UNPROCESSABLE_ENTITY);
			put(ErrorType.UNABLE_TO_UPDATE_YOURSELF_ROLE, HttpStatus.UNPROCESSABLE_ENTITY);
			put(ErrorType.PROJECT_UPDATE_NOT_ALLOWED, HttpStatus.UNPROCESSABLE_ENTITY);
			put(ErrorType.TICKET_NOT_FOUND, HttpStatus.NOT_FOUND);

		}
	};

	public static HttpStatus getHttpStatus(ErrorType errorType, HttpStatus defaultStatus) {
		return getHttpStatus(errorType).orElse(defaultStatus);
	}

	public static Optional<HttpStatus> getHttpStatus(ErrorType errorType) {
		return Optional.ofNullable(MAPPING.get(errorType));
	}

}

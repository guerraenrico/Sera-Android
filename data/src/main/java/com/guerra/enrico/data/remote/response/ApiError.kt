package com.guerra.enrico.data.remote.response

/**
 * Created by enrico
 * on 17/08/2018.
 */
data class ApiError(val code: String, val message: String) {
  companion object {
      const val UNKNOWN = "unknown"
      const val NOT_AUTHORIZED = "not_authorized"
      const val EXPIRED_SESSION = "expired_session"
      const val INVALID_SESSION_TOKEN = "invalid_session_token"

      const val AUTH_INVALID_PAYLOAD = "auth_invalid_payload"
      const val AUTH_INVALID_CODE = "auth_invalid_code"
      const val ERROR_CREATE_USER = "error_create_user"
      const val ERROR_CREATE_SESSION = "error_create_session"
      const val AUTH_ERROR_UNKNOWN = "auth_error_unknown"

      const val ERROR_READ_CATEGORY = "error_read_category"
      const val ERROR_SEARCH_CATEGORY = "error_search_category"
      const val ERROR_INSERT_CATEGORY = "error_insert_category"
      const val ERROR_DELETE_CATEGORY = "error_delete_category"
      const val ERROR_READ_TASK = "error_read_task"
      const val ERROR_INSERT_TASK = "error_insert_task"
      const val ERROR_DELETE_TASK = "error_delete_task"
      const val ERROR_UPDATE_TASK = "error_update_task"
      const val ERROR_UPDATE_SESSION = "error_update_session"

      const val ERROR_READ_TASK_RESULTS = "error_read_task_results"

      const val INVALID_AUTH_CODE = "invalid_auth_code"
      const val INVALID_CATEGORY_PARAMETERS = "invalid_Category_parameters"
      const val INVALID_CATEGORY_ID = "invalid_category_id"
      const val INVALID_TASK_PARAMETERS = "invalid_task_parameters"
      const val INVALID_TASK_ID = "invalid_task_id"

      const val VALID_TOKEN_REQUIRED = "valid_token_required"
      const val USER_NOT_FOUND = "user_not_found"

      fun unknown() = ApiError(UNKNOWN, "Something went wrong")
      fun notAuthorized() = ApiError(NOT_AUTHORIZED, "Not authorized. Try login again")
  }
}
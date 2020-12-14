package com.edeja.edejaEducation;

import java.util.ResourceBundle;

public enum Messages {

  /**
   * There is no delivery with requested deliveryId.
   */
  ERROR_NO_DELIVERY("1001", "error.no.delivery"),
  /**
   * There is no file with requested hash value.
   */
  ERROR_NO_FILE("1002", "error.no.file"),
  /**
   * Start datetime is after end datetime.
   */
  ERROR_START_DATE_AFTER_END_DATE("1003", "error.start.date.time.after.end"),
  /**
   * Solr server is unreachable.
   */
  ERROR_UNREACHABLE_SOLR_SERVER("1004", "error.unreachable.solr.server"),

  /**
   * A fatal error happened within the Sender Cockpit. Further details must be taken from the log file.
   */

  ERROR_GENERAL("9000", "error.general"),

  /**
   * A fatal error happened within the Sender Cockpit. Further details must be taken from the log file.
   */

  PERSON_NOT_FOUND("9001", "error.person_not_found"),

  /**
   * A fatal error happened within the Sender Cockpit. Further details must be taken from the log file.
   */
  INVALID_USERNAME_OR_PASSWORD("9002", "error.invalid_username_or_password"),

  /**
   * A fatal error happened within the Sender Cockpit. Further details must be taken from the log file.
   */

  PERSON_IS_INACTIVE("9003", "error.person_is_inactive"),

  /**
   * A fatal error happened with jwt token.
   */
  INVALID_OR_MISSING_TOKEN("9004", "error.token_is_not_valid"),

  /**
   * The user role from jwt token is not valid.
   */
  USER_ROLE_IS_NOT_VALID("9005", "error.user_role_not_valid"),

  /**
   * The user does not have access rights to the content.
   */
  USER_ACCESS_RIGHTS_TO_CONTENT("9006", "error.user_access_rights_to_content"),

  INVALID_EMAIL("9007", "error.invalid_email");




  private static final ResourceBundle errorMessageBundle = ResourceBundle.getBundle("messages");

  private final String identification;
  private final String messagePropertyKey;
  private final Object[] formatArguments;

  Messages(String identification, String messagePropertyKey, Object... formatArgs) {
    this.identification = identification;
    this.messagePropertyKey = messagePropertyKey;
    this.formatArguments = formatArgs;
  }

  public String getIdentification() {
    return identification;
  }

  public String getMessage() {
    return String.format(errorMessageBundle.getString(messagePropertyKey), formatArguments);
  }

}

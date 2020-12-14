package com.edeja.edejaEducation.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

public class ErrorDTO {

  @ApiModelProperty(notes = "error code")
  private final String errorCode;

  @ApiModelProperty(notes = "error message")
  private final String errorMessage;

  public ErrorDTO(
      @JsonProperty("errorCode") String errorCode,
      @JsonProperty("errorMsg") String errorMsg) {
    this.errorCode = errorCode;
    this.errorMessage = errorMsg;
  }

  public String getErrorCode() {
    return this.errorCode;
  }

  public String getErrorMessage() {
    return this.errorMessage;
  }

}

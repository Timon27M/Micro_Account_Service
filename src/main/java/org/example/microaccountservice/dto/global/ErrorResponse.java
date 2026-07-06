package org.example.microaccountservice.dto.global;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
  private HttpStatusCode status;
  @Getter private String message;
  private LocalDateTime timestamp;

  public ErrorResponse(HttpStatusCode status, String message) {
    this.status = status;
    this.message = message;
    this.timestamp = LocalDateTime.now();
  }
}

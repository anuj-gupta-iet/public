package com.example3.demo.validation.groups;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.groups.Default;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.Data;

@SpringBootApplication
public class SpringValidationEx3Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringValidationEx3Application.class, args);
	}

}

@RestController
class MyController {

	@PostMapping("/saveNormalUser")
	public ResponseEntity<String> saveNormalUser(
			@Valid @RequestBody UserDto userDto) { // it is equivalent to @Validated(Default.class)
		// in case of normal user username cant be blank
		return ResponseEntity.ok("saveNormalUser valid");
	}
	@PostMapping("/savePriviledgedUser")
	public ResponseEntity<String> savePriviledgedUser(
			@Validated({Default.class, PrivilegedUserMarker.class}) @RequestBody UserDto userDto) {
		// in case of priviledged user username and priviledgedUserType cant be
		// blank
		return ResponseEntity.ok("savePriviledgedUser valid");
	}
	@PostMapping("/saveAdminUser")
	public ResponseEntity<String> saveAdminUser(
			@Validated({Default.class, AdminUserMarker.class}) @RequestBody UserDto userDto) {
		// in case of admin user username and adminUserType cant be blank
		return ResponseEntity.ok("saveAdminUser valid");
	}

}

@Data
class UserDto {

	@NotBlank
	// no group means this validation will run when Default.class is provided in
	// @Validated or simple @Valid
	private String userName;

	@NotBlank(groups = {PrivilegedUserMarker.class})
	// this validation will run when PrivilegedUserMarker.class is provided in
	// @Validated
	private String priviledgedUserType;

	@NotBlank(groups = {AdminUserMarker.class})
	// this validation will run when AdminUserMarker.class is provided in
	// @Validated
	private String adminUserType;

}

interface PrivilegedUserMarker {
}
interface AdminUserMarker {
}

@ControllerAdvice
class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<List<ValidationError>> handleUserException(
			MethodArgumentNotValidException ex) {
		List<ValidationError> validationErrors = ex.getBindingResult()
				.getAllErrors().stream().map(e -> {
					String fieldName = ((FieldError) e).getField();
					String errorMessage = e.getDefaultMessage();
					ValidationError validationError = new ValidationError(
							fieldName, errorMessage);
					return validationError;
				}).collect(Collectors.toList());
		return new ResponseEntity<List<ValidationError>>(validationErrors,
				HttpStatus.BAD_REQUEST);
	}

}

@Data
@AllArgsConstructor
class ValidationError {
	private String fieldName;
	private String errorMessage;
}
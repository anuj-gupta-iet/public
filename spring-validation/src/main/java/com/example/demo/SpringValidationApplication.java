package com.example.demo;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.Data;

@SpringBootApplication
public class SpringValidationApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringValidationApplication.class, args);
	}

}

@RestController
class MyController {

	@PostMapping("/save")
	public String save(@Valid @RequestBody AccountDto accountDto) {
		System.out.println("Saving AccountDto: " + accountDto);
		return "Account Dto saved Successfully";
	}

	@PostMapping("/saveAsDraft")
	public String saveAsDraft(@RequestBody AccountDto accountDto) {
		System.out.println("Saving AccountDto: " + accountDto);
		return "Account Dto saved Successfully";
	}
}

@Data
class AccountDto {
	@NotBlank(message = "Id is Required Field")
	private String id;
	@NotBlank(message = "Name is Required Field")
	@Size(max = 10)
	private String name;
	@Email
	private String email;
	@PhoneNumberConstraint
	private Long phoneNumber;

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

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Constraint(validatedBy = PhoneNumberValidator.class)
@interface PhoneNumberConstraint {
	String message() default "Invalid phone number";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}

class PhoneNumberValidator
		implements
			ConstraintValidator<PhoneNumberConstraint, Long> {

	@Override
	public boolean isValid(Long value, ConstraintValidatorContext context) {
		if (String.valueOf(value).length() == 10) {
			return true;
		} else {
			return false;
		}
	}

}
package com.example2.demo.custom.validator;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.Data;

@RestController
class TransferManagerActionBean {

	@Autowired
	private TransferManagerValidator validator;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(validator);
	}

	@PostMapping("/doSubmit")
	public ResponseEntity<String> doSubmit(
			@Valid @RequestBody TransferDto transferDto, BindingResult result)
			throws MethodArgumentNotValidException {
		verifyErrors(result);
		return ResponseEntity.ok("Valid");
	}

	@PostMapping("/doApprove")
	public ResponseEntity<String> doApprove(
			@Valid @RequestBody TransferDto transferDto, BindingResult result)
			throws MethodArgumentNotValidException {
		verifyErrors(result);
		return ResponseEntity.ok("Valid");
	}
	@PostMapping("/doReject")
	public ResponseEntity<String> doReject(
			@Valid @RequestBody OtherTransferDto otherTransferDto,
			BindingResult result) throws MethodArgumentNotValidException {
		verifyErrors(result);
		return ResponseEntity.ok("Valid");
	}

	private void verifyErrors(BindingResult result)
			throws MethodArgumentNotValidException {
		if (result.hasErrors()) {
			throw new MethodArgumentNotValidException(null, result);
		}
	}
}

@Component
class TransferManagerValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return TransferDto.class.equals(clazz)
				|| OtherTransferDto.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		if (target instanceof TransferDto) {
			System.out.println("111111");
			TransferDto transferDto = (TransferDto) target;
			if (transferDto.getId() > 5) {
				errors.reject("id", "can not be more than 5");
			}
		} else if (target instanceof OtherTransferDto) {
			System.out.println("22222222");
			OtherTransferDto otherTransferDto = (OtherTransferDto) target;
			if (otherTransferDto.getName().startsWith("a")) {
				errors.reject("name", "can not start with 'a'");
			}
		}
	}

}

@Data
@AllArgsConstructor
class TransferDto {

	@NotNull
	private Long id;
	@Valid
	private List<MixedUnit> mixedUnits;

}

@Data
@AllArgsConstructor
class OtherTransferDto {
	@NotNull
	@Size(max = 10)
	private String name;
	private Integer age;
}

@Data
@AllArgsConstructor
class MixedUnit {
	@NotNull
	@Min(2)
	private BigDecimal new_quantity;
	@NotNull
	@Max(value = 10)
	private Long account_id;
}

@ControllerAdvice
class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<List<ValidationError>> handleMethodArgumentNotValidException(
			MethodArgumentNotValidException ex) {
		List<ValidationError> validationErrors = ex.getBindingResult()
				.getAllErrors().stream().map(e -> {
					String fieldName = "";
					String errorMessage = "";
					if (e instanceof FieldError) {
						fieldName = ((FieldError) e).getField();
						errorMessage = e.getDefaultMessage();
					} else if (e instanceof ObjectError) {
						ObjectError o = (ObjectError) e;
						fieldName = o.getCode();
						errorMessage = e.getDefaultMessage();
						//System.out.println(o.getCode());
						//System.out.println(o.getDefaultMessage());
					}
					ValidationError validationError = new ValidationError(
							fieldName, errorMessage);
					return validationError;
				}).collect(Collectors.toList());
		return new ResponseEntity<List<ValidationError>>(validationErrors,
				HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleMethodArgumentNotValidException(
			Exception ex) {
		return new ResponseEntity<String>(ExceptionUtils.getStackTrace(ex),
				HttpStatus.BAD_REQUEST);
	}

}

@Data
@AllArgsConstructor
class ValidationError {
	private String fieldName;
	private String errorMessage;
}

@SpringBootApplication
public class SpringValidationEx2Application {

	@PostConstruct
	private void init() throws JsonProcessingException {
		List<MixedUnit> mixedUnits = Arrays.asList(
				new MixedUnit(BigDecimal.TEN, 1l),
				new MixedUnit(BigDecimal.ONE, 2l));
		TransferDto transferDto = new TransferDto(22l, mixedUnits);
		ObjectMapper mapper = new ObjectMapper();
		// System.out.println(mapper.writerWithDefaultPrettyPrinter()
		// .writeValueAsString(transferDto));
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringValidationEx2Application.class, args);
	}

}

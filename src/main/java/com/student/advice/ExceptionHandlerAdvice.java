package com.student.advice;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.student.exception.StudentNotFoundException;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(
			MethodArgumentNotValidException ex) {
		Map<String, String> errorMap = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach(error ->{
			errorMap.put("errorMsg", error.getDefaultMessage());
		});
		
		return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);

	}
	@ExceptionHandler(StudentNotFoundException.class)
	public ResponseEntity<Map<String, String>> handleStudentNotFoundException(
			StudentNotFoundException ex) {
		Map<String, String> errorMap = new HashMap<>();
		
			errorMap.put("errorMsg", ex.getMessage());
		
		return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);

	}
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<Map<String, String>> handleRuntimeException(
			RuntimeException ex) {
		Map<String, String> errorMap = new HashMap<>();
		errorMap.put("errorMsg", ex.getMessage());
		return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
	}
}

package com.student.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.student.entity.Student;
import com.student.service.StudentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class StudentController {

	@Autowired
	private StudentService studentService;

	@PostMapping("/students")
	public ResponseEntity<?> createStudent(@Valid @RequestBody Student student) {
		Student newStudent = studentService.createStudent(student);
		return ResponseEntity.ok(newStudent);
	}

	@PutMapping("/students/{id}")
	public ResponseEntity<Student> updateStudentMarks(@PathVariable Long id, @RequestBody Student student) {
		student.setId(id);
		Student response = studentService.updateStudentMarks(student);
		return ResponseEntity.ok(response);
	}
}

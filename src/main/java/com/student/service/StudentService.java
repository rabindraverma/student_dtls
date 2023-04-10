package com.student.service;

import com.student.entity.Student;
import com.student.exception.StudentNotFoundException;

public interface StudentService {

	public Student createStudent(Student student);
	
	public Student updateStudentMarks(Student student) throws StudentNotFoundException;
}

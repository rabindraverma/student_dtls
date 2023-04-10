
package com.student.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.Arrays;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.student.entity.Student;
import com.student.exception.StudentNotFoundException;
import com.student.repository.StudentRepository;
@Service
public class StudentServiceImpl implements StudentService {

	@Autowired
	private StudentRepository studentRepository;

	@Override
	public Student createStudent(Student student) {
		// checking that student's age is greater than 15 years and less than or equal
		// to 20 years
		LocalDate dob = student.getDob();
		LocalDate now = LocalDate.now();
		int age = Period.between(dob, now).getYears();
		if (age <= 15 || age > 20) {
			throw new RuntimeException("Age must be between 15 to 20");
		}

		// Check that the section field has a valid value of A, B, or C
		String section = student.getSection();
		if (!Arrays.asList("A", "B", "C").contains(section)) {
			throw new RuntimeException("Section must have a valid value of A, B, or C");
		}

		// Check that the gender field has a valid value of M or F
		String gender = student.getGender();
		if (!Arrays.asList("M", "F").contains(gender)) {
			throw new RuntimeException("Gender must have a valid value of M or F");
		}

		// Calculate total, average, and result
		int totalMarks = (student.getMarks1() != 0 ? student.getMarks1() : 0)
				+ (student.getMarks2() != 0 ? student.getMarks2() : 0)
				+ (student.getMarks3() != 0 ? student.getMarks3() : 0);
		double averageMarks = totalMarks / 3.0;
		boolean result = (student.getMarks1() != 0 && student.getMarks1() >= 35)
				&& (student.getMarks2() != 0 && student.getMarks2() >= 35)
				&& (student.getMarks3() != 0 && student.getMarks3() >= 35);

		// Create a new student object
		Student newStudent = new Student();
		newStudent.setFirstName(student.getFirstName());
		newStudent.setLastName(student.getLastName());
		newStudent.setDob(student.getDob());
		newStudent.setSection(student.getSection());
		newStudent.setGender(student.getGender());
		newStudent.setMarks1(student.getMarks1());
		newStudent.setMarks2(student.getMarks2());
		newStudent.setMarks3(student.getMarks3());
		newStudent.setTotal(totalMarks);
		newStudent.setAverage(averageMarks);
		if (result) {
			newStudent.setResult("PASS");
		} else {
			newStudent.setResult("FAIL");
		}

		// Save to database
		return studentRepository.save(newStudent);

	}

	@Override
	public Student updateStudentMarks(Student student) throws StudentNotFoundException {
		Optional<Student> optionalStudent = studentRepository.findById(student.getId());
		if (optionalStudent.isPresent()) {
			// Calculate total, average, and result
			int totalMarks = student.getMarks1() + student.getMarks2() + student.getMarks3();
			double averageMarks = totalMarks / 3.0;
			boolean result = student.getMarks1() >= 35 && student.getMarks2() >= 35 && student.getMarks3() >= 35;

			// Update student object
			Student updatedStudent = optionalStudent.get();
			updatedStudent.setMarks1(student.getMarks1());
			updatedStudent.setMarks2(student.getMarks2());
			updatedStudent.setMarks3(student.getMarks3());
			updatedStudent.setTotal(totalMarks);
			updatedStudent.setAverage(averageMarks);
			if (result) {
				updatedStudent.setResult("PASS");
			} else {
				updatedStudent.setResult("FAIL");
			}

			// Save to database
			return studentRepository.save(updatedStudent);
		}
		throw new StudentNotFoundException("student not found with id : "+student.getId());
	}

}

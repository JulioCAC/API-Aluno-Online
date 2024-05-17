package br.com.alunoonline.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import br.com.alunoonline.api.dtos.CreateStudentRequest;
import br.com.alunoonline.api.model.Course;
import br.com.alunoonline.api.model.Student;
import br.com.alunoonline.api.repository.CourseRepository;
import br.com.alunoonline.api.repository.StudentFinanceRepository;
import br.com.alunoonline.api.repository.StudentRepository;

@Service
public class StudentService {
	@Autowired
	StudentRepository studentRepository;

	@Autowired
	StudentFinanceRepository studentFinanceRepository;

	@Autowired
	CourseRepository courseRepository;

	public void create(CreateStudentRequest createStudentRequest) {
		Course course = courseRepository.findById(createStudentRequest.getCourseId())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso não encontrado"));
		Student stundetSave = studentRepository.save(
				new Student(
						null, 
						createStudentRequest.getName(), 
						createStudentRequest.getEmail(), 
						course
						)
				);
				
	}
	
	public List<Student> findAll(){
		return studentRepository.findAll();
		
	}
	public Optional<Student> findById(Long id){
		return studentRepository.findById(id);
	}

}

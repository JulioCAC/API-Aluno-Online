package br.com.alunoonline.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import br.com.alunoonline.api.dtos.CreateStudentRequest;
import br.com.alunoonline.api.enums.FinanceStatusEnum;
import br.com.alunoonline.api.model.Course;
import br.com.alunoonline.api.model.Student;
import br.com.alunoonline.api.model.StudentFinance;
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
		Student stundetSave = studentRepository
				.save(new Student(null, createStudentRequest.getName(), createStudentRequest.getEmail(), course, null));
		createStudentFinance(stundetSave, createStudentRequest);

	}

	public List<Student> findAll() {
		return studentRepository.findAll();

	}

	public Optional<Student> findById(Long id) {
		return studentRepository.findById(id);
	}

	public void update(Long id, Student student) {
		Optional<Student> studentFromDb = findById(id);

		if (studentFromDb.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno não encontrado");
		}

		Student studentUpdated = studentFromDb.get();

		studentUpdated.setName(student.getName());
		studentUpdated.setEmail(student.getEmail());
		studentRepository.save(studentUpdated);
	}

	public void deleteById(Long id) {
		studentRepository.deleteById(id);
	}

	public void createStudentFinance(Student student, CreateStudentRequest createStudentRequest) {
		StudentFinance studentFinance = new StudentFinance(null, student, createStudentRequest.getDiscount(),
				createStudentRequest.getDueDate(), FinanceStatusEnum.EM_DIA);
		
		studentFinanceRepository.save(studentFinance);

	}

}

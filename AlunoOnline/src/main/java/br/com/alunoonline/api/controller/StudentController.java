package br.com.alunoonline.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.alunoonline.api.dtos.CreateStudentRequest;
import br.com.alunoonline.api.model.Student;
import br.com.alunoonline.api.service.StudentService;

@RestController
@RequestMapping("/student")
public class StudentController {

	@Autowired
	StudentService studentService;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void create(@RequestBody CreateStudentRequest createStudentRequest) {
		studentService.create(createStudentRequest);
	}

	@GetMapping("/all")
	@ResponseStatus(HttpStatus.OK)
	public List<Student> findAll() {
		return studentService.findAll();

	}
	
	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Optional<Student> findById(@PathVariable Long id){
		return studentService.findById(id);
	}
	
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void update(@RequestBody Student student, @PathVariable Long id) {
		studentService.update(id, student);
	}
	

}

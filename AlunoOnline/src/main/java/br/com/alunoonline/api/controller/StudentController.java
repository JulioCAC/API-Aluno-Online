package br.com.alunoonline.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.alunoonline.api.dtos.CreateStudentRequest;
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
}

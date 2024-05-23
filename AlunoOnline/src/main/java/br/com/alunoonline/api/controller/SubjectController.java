package br.com.alunoonline.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.alunoonline.api.model.Subject;
import br.com.alunoonline.api.service.SubjectService;

@RestController
@RequestMapping("/subject")
public class SubjectController {
	@Autowired
	SubjectService subjectService;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void create(@RequestBody Subject subject) {
		subjectService.create(subject);
	}
	
	@GetMapping("/all")
	@ResponseStatus(HttpStatus.OK)
	public List<Subject> findAll(){
		return subjectService.findAll();
	}
	
	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Optional<Subject> findById(@PathVariable Long id){
		return subjectService.findById(id);
	}
	
	@GetMapping("/professor/{professorId}")
	@ResponseStatus(HttpStatus.OK)
	public List<Subject> findByProfessorId(@PathVariable Long professorId){
		return subjectService.findByProfessorId(professorId);
	}
	
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void update(@PathVariable Long id, @RequestBody Subject subject) {
		subjectService.update(id, subject);
	}
	

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteById(@PathVariable Long id) {
		subjectService.deleteById(id);
	}
}

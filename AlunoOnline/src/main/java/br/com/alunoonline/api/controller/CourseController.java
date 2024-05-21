package br.com.alunoonline.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.alunoonline.api.model.Course;
import br.com.alunoonline.api.service.CourseService;

@RestController
@RequestMapping("/course")
public class CourseController {
	@Autowired
	CourseService courseService;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void create(@RequestBody Course course) {
		courseService.create(course);
	}
	
	

}

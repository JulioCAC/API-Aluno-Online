package br.com.alunoonline.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.alunoonline.api.model.Course;
import br.com.alunoonline.api.repository.CourseRepository;

@Service
public class CourseService {
	@Autowired
	CourseRepository courseRepository;
	
	public void create(Course course) {
		courseRepository.save(course);
	}

}

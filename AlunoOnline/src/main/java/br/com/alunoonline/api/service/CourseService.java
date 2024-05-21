package br.com.alunoonline.api.service;

import java.util.List;
import java.util.Optional;

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
	
	public List<Course> findAll() {
		return courseRepository.findAll();
	}
	
	public Optional<Course> findByid(Long id){
		return courseRepository.findById(id);
	}

}

package br.com.alunoonline.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

	public Optional<Course> findById(Long id) {
		return courseRepository.findById(id);
	}

	public void update(Long id, Course course) {
		Optional<Course> courseFromDb = findById(id);

		if (courseFromDb.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso não encontrado no banco de dados");
		}

		Course courseUpdated = courseFromDb.get();

		courseUpdated.setName(course.getName());
		courseUpdated.setTuition(course.getTuition());
		courseUpdated.setType(course.getType());
		courseRepository.save(courseUpdated);

	}

	public void deleteById(Long id) {
		courseRepository.deleteById(id);
	}

}

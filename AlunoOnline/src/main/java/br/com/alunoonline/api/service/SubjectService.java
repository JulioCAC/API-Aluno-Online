package br.com.alunoonline.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.alunoonline.api.model.Subject;
import br.com.alunoonline.api.repository.SubjectRepository;

@Service
public class SubjectService {
	@Autowired
	SubjectRepository subjectRepository;

	public void create(Subject subject) {
		subjectRepository.save(subject);
	}

	public List<Subject> findAll() {
		return subjectRepository.findAll();
	}

	public List<Subject> findByProfessorId(Long professorId) {
		return subjectRepository.findByProfessorId(professorId);
	}

	public Optional<Subject> findById(Long id) {
		return subjectRepository.findById(id);
	}

	public void update(Long id, Subject subject) {
		Optional<Subject> subjectFromDb = findById(id);
		
		if (subjectFromDb.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Disciplina não encontrada no banco de dados");
		}
		
		Subject subjectUpdated = subjectFromDb.get();
		subjectUpdated.setName(subject.getName());
		subjectUpdated.setProfessor(subject.getProfessor());
		subjectRepository.save(subjectUpdated);
	}
	
	public void deleteById(Long id) {
		subjectRepository.deleteById(id);
	}

}

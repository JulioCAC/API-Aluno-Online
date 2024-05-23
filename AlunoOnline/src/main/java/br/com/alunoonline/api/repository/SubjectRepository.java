package br.com.alunoonline.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.alunoonline.api.model.Subject;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long>{
	List<Subject> findByProfessorId(Long professorId);

}

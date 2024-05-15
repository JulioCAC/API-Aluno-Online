package br.com.alunoonline.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.alunoonline.api.model.StudentFinance;

@Repository
public interface StudentFinanceRepository extends JpaRepository<StudentFinance, Long>{

}

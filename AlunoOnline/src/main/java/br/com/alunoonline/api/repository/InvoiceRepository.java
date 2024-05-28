
package br.com.alunoonline.api.repository;


import br.com.alunoonline.api.model.Invoice;
import br.com.alunoonline.api.model.StudentFinance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    boolean existsByStudentFinancialAndDueDate(StudentFinance studentFinance, LocalDateTime dueDate);
}
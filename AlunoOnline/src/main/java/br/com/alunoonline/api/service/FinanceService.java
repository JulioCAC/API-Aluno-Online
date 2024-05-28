package br.com.alunoonline.api.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import br.com.alunoonline.api.model.Invoice;
import br.com.alunoonline.api.model.StudentFinance;
import br.com.alunoonline.api.repository.InvoiceRepository;
import br.com.alunoonline.api.repository.StudentFinanceRepository;

@Service
public class FinanceService {
	
	private static final Integer QUANTITY_OF_DAYS_BEFORE_GENERATE = 10;

    private static final Logger logger = LoggerFactory.getLogger(FinanceService.class);
    
    @Autowired
    InvoiceRepository invoiceRepository;
    
    @Autowired 
    StudentFinanceRepository studentFinanceRepository;
    
    @Scheduled(cron = "0 0 0 * * ?")
    public void invoiceGeneration() {
        logger.info("Iniciando a geração de faturas...");

        LocalDateTime currentDate = LocalDateTime.now();
        LocalDateTime generationDeadline = currentDate.plusDays(QUANTITY_OF_DAYS_BEFORE_GENERATE);

      
        List<StudentFinance> studentFinances = studentFinanceRepository.findAll();
        
        
        for (StudentFinance studentFinance : studentFinances) {
            Integer dueDay = studentFinance.getDueDate();

            if (dueDay != null) {
               
                LocalDate dueDateCurrentMonth = calculateDueDate(dueDay, currentDate.getYear(), currentDate.getMonthValue());

               
                if (dueDateCurrentMonth.isBefore(currentDate.toLocalDate())) {
                    dueDateCurrentMonth = calculateDueDate(dueDay, currentDate.getYear(), currentDate.getMonthValue() + 1);
                }

              
                if (dueDateCurrentMonth != null && (dueDateCurrentMonth.isBefore(generationDeadline.toLocalDate()) || dueDateCurrentMonth.isEqual(generationDeadline.toLocalDate()))) {
                    
                    if (invoiceRepository.existsByStudentFinancialAndDueDate(studentFinance, dueDateCurrentMonth.atTime(LocalTime.MIDNIGHT))) {
                        // logger.info("Fatura já existe para o aluno: {} com data de vencimento: {}", financeiroAluno.getId(), dueDateCurrentMonth);
                        continue;
                    }
                    logger.info("Gerando fatura para o aluno: {}", studentFinance.getId());

             
                    Invoice invoice = new Invoice();
                    invoice.setStudentFinance(studentFinance);
                    invoice.setDueDate(dueDateCurrentMonth.atTime(LocalTime.MIDNIGHT));
                    invoice.setCreatedAt(currentDate);

                   
                    invoiceRepository.save(invoice);

                    logger.info("Fatura gerada para o aluno: {} com data de vencimento: {}", studentFinance.getId(), dueDateCurrentMonth);

                }
            }
            logger.info("Geração de faturas concluída.");
        }
        
    }
    
    private LocalDate calculateDueDate(Integer dueDay, int year, int month) {
        YearMonth yearMonth = YearMonth.of(year, month);

       
        int dayOfMonth = Math.min(dueDay, yearMonth.lengthOfMonth());

        return LocalDate.of(year, month, dayOfMonth);
    }
       
            

}

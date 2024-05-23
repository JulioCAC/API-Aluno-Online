package br.com.alunoonline.api.dtos;

import br.com.alunoonline.api.enums.EnrollmentStatusEnum;
import lombok.Data;

@Data
public class StudentSubjectResponse {
	private String subjectName;
    private String professorName;
    private Double grade1;
    private Double grade2;
    private Double average;
    private EnrollmentStatusEnum status;

}

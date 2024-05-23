package br.com.alunoonline.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.alunoonline.api.dtos.UpdateGradesRequest;
import br.com.alunoonline.api.enums.EnrollmentStatusEnum;
import br.com.alunoonline.api.model.Enrollment;
import br.com.alunoonline.api.repository.EnrollmentRepository;

@Service
public class EnrollmentService {

	public static final double GRADE_AVG_TO_APROVE = 7.0;

	@Autowired
	EnrollmentRepository enrollmentRepository;

	public void create(Enrollment enrollment) {
		enrollment.setStatus(EnrollmentStatusEnum.MATRICULADO);
		enrollmentRepository.save(enrollment);
	}

	public void updateGrades(Long enrollmentId, UpdateGradesRequest updateGradesRequest) {
		Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Matrícula não Encontrada"));
		updateStudentGrade(enrollment, updateGradesRequest);
		updateStudentStatus(enrollment);

		enrollmentRepository.save(enrollment);
	}

	public void updateStudentGrade(Enrollment enrollment, UpdateGradesRequest updateGradesRequest) {
		if (updateGradesRequest.getGrade1() != null) {
			enrollment.setGrade1(updateGradesRequest.getGrade1());
		}
		if (updateGradesRequest.getGrade2() != null) {
			enrollment.setGrade2(updateGradesRequest.getGrade2());
		}
	}

	public void updateStudentStatus(Enrollment enrollment) {
		if (enrollment.getGrade1() != null && enrollment.getGrade2() != null) {
			double average = calculateAverage(enrollment);
			enrollment.setStatus(
					average >= GRADE_AVG_TO_APROVE ? EnrollmentStatusEnum.APROVADO : EnrollmentStatusEnum.REPROVADO);
		}
	}
	
	public void updateStatusToDropped(Long enrollmentId) {
		Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Matrícula não Encontrada"));
		if(!EnrollmentStatusEnum.MATRICULADO.equals(enrollment.getStatus())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Só é possível trancar uma matrícula comn o status MATRICULADO");
		}
		changeStatus(enrollment, EnrollmentStatusEnum.TRANCADO);
	}
	public void changeStatus(Enrollment enrollment, EnrollmentStatusEnum enrollmentStatusEnum) {
		enrollment.setStatus(enrollmentStatusEnum);
		enrollmentRepository.save(enrollment);
	}

	public Double calculateAverage(Enrollment enrollment) {

		Double grade1 = enrollment.getGrade1();
		Double grade2 = enrollment.getGrade2();

		if (grade1 == null || grade2 == null) {
			return null;
		}

		Double average = (grade1 + grade2) / 2.0;

		return average;
	}

}

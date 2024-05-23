package br.com.alunoonline.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.alunoonline.api.dtos.StudentSubjectResponse;
import br.com.alunoonline.api.dtos.StudentTranscriptResponse;
import br.com.alunoonline.api.dtos.UpdateGradesRequest;
import br.com.alunoonline.api.enums.EnrollmentStatusEnum;
import br.com.alunoonline.api.model.Enrollment;
import br.com.alunoonline.api.model.Subject;
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
	
	public StudentTranscriptResponse getAcademicTranscript(Long alunoId) {
		List<Enrollment> studentEnrollment = enrollmentRepository.findByStudentId(alunoId);
		
		if(studentEnrollment.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Esse aluno não possui matrícula ativa");
		}
		
		StudentTranscriptResponse transcript = new StudentTranscriptResponse();
		transcript.setStudentName(studentEnrollment.get(0).getStudent().getName());
		transcript.setStudentEmail(studentEnrollment.get(0).getStudent().getEmail());
		
		List<StudentSubjectResponse> subjectList = new ArrayList<>();
		
		for (Enrollment enrollment : studentEnrollment) {
			StudentSubjectResponse studentSubjectResponse = new StudentSubjectResponse();
			studentSubjectResponse.setSubjectName(enrollment.getSubject().getName());
			studentSubjectResponse.setProfessorName(enrollment.getSubject().getProfessor().getName());
			studentSubjectResponse.setGrade1(enrollment.getGrade1());
			studentSubjectResponse.setGrade2(enrollment.getGrade2());
			
			Double average = calculateAverage(enrollment);
			
			if (average != null) {
				studentSubjectResponse.setAverage(average);
			}
			
			studentSubjectResponse.setStatus(enrollment.getStatus());
			subjectList.add(studentSubjectResponse);
			
		}
		
		transcript.setStudentSubjectResponsesList(subjectList);
		
		return transcript;
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

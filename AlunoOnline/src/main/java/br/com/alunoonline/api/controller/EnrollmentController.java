package br.com.alunoonline.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.alunoonline.api.dtos.StudentTranscriptResponse;
import br.com.alunoonline.api.dtos.UpdateGradesRequest;
import br.com.alunoonline.api.model.Enrollment;
import br.com.alunoonline.api.service.EnrollmentService;

@RestController
@RequestMapping("/enrollment")
public class EnrollmentController {
	
	@Autowired
	EnrollmentService enrollmentService;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void create(@RequestBody Enrollment enrollment) {
		enrollmentService.create(enrollment);
	}
	
	@PatchMapping("/update-grade/{enrollmentId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updateGrades(@RequestBody UpdateGradesRequest updateGradesRequest, @PathVariable Long enrollmentId){
		enrollmentService.updateGrades(enrollmentId, updateGradesRequest);
	}
	
	@PatchMapping("/update-status-to-dropped/{enrollmentId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updateStatusToDropped(@PathVariable Long enrollmentId){
		enrollmentService.updateStatusToDropped(enrollmentId);
	}
	
	@GetMapping("/academic-transcript/{alunoId}")
	@ResponseStatus(HttpStatus.OK)
	public StudentTranscriptResponse getAcademicTranscript(@PathVariable Long alunoId) {
		return enrollmentService.getAcademicTranscript(alunoId);
	}
	

}

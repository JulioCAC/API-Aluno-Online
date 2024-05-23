package br.com.alunoonline.api.dtos;

import java.util.List;

import lombok.Data;

@Data
public class StudentTranscriptResponse {
	private String studentName;
	private String studentEmail;
	private List<StudentSubjectResponse> studentSubjectResponsesList;

}

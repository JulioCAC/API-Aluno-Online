package br.com.alunoonline.api.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Student implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	private String email;

	@ManyToOne
	@JoinColumn(name = "course_id")
	private Course course;
	
	@OneToOne(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
    private StudentFinance studentFinance;


}

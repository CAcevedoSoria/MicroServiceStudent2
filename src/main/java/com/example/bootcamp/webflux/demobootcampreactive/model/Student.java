package com.example.bootcamp.webflux.demobootcampreactive.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/** The type Student. */
@Getter
@Setter
@NoArgsConstructor
@Document(collection = "Student")
public class Student {

  @Id private String id;


  private String fullName;
  @NotEmpty
  private String gender;

  @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
  private LocalDate birthday;
  private String address;
  private String academicPeriod;
  private String typeDocument;
  @Size(min = 8, max = 8)
  private String document;

  /**
   * Instantiates a new Student.
   *
   * @param fullName the full name
   * @param gender the gender
   * @param birthday the birthday
   * @param address the address
   * @param academicPeriod the academic period
   * @param typeDocument the type document
   * @param document the document
   */
  public Student(
      String fullName,
      String gender,
      LocalDate birthday,
      String address,
      String academicPeriod,
      String typeDocument, String document) {
    this.fullName = fullName;
    this.gender = gender;
    this.birthday = birthday;
    this.address = address;
    this.academicPeriod = academicPeriod;
    this.typeDocument = typeDocument;
    this.document = document;
  }
}




package com.example.bootcamp.webflux.demobootcampreactive.service.impl;

import com.example.bootcamp.webflux.demobootcampreactive.model.Student;
import com.example.bootcamp.webflux.demobootcampreactive.repository.StudentRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.reactivestreams.Publisher;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class StudentServiceImplTest {

  @Mock
  private StudentRepository studentRepository;

  @InjectMocks
  private StudentServiceImpl studentService;
  @Test
  public void findAll() {

    Student student = new Student();
    student.setFullName("cristohper");
    student.setGender("male");
    student.setBirthday(LocalDate.of(1965, 01, 25));
    student.setAddress("jwe2932");
    student.setAcademicPeriod("3 Cycle");
    student.setTypeDocument("dni");
    student.setDocument("736723727");

    when(studentService.findAll()).thenReturn(Flux.just(student));

    Flux<Student> actual = studentService.findAll();

    assertResults(actual, student);
  }


  @Test
  public void findById() {
    Student student = new Student();
    student.setFullName("cristohper");
    student.setGender("male");
    student.setBirthday(LocalDate.of(1965, 01, 25));
    student.setAddress("jwe2932");
    student.setAcademicPeriod("3 Cycle");
    student.setTypeDocument("dni");
    student.setDocument("736723727");

    when(studentRepository.findById(student.getId())).thenReturn(Mono.just(student));
    Mono<Student> actual = studentService.findById(student.getId());

    assertResults(actual, student);

  }

  @Test
  public void findById_when_id_exist() {
    Student student = new Student();
    student.setFullName("cristohper");
    student.setGender("male");
    student.setBirthday(LocalDate.of(1965, 01, 25));
    student.setAddress("jwe2932");
    student.setAcademicPeriod("3 Cycle");
    student.setTypeDocument("dni");
    student.setDocument("736723727");

    when(studentRepository.findById(student.getId())).thenReturn(Mono.empty());
    Mono<Student> actual = studentService.findById(student.getId());

    assertResults(actual);
  }

  @Test
  public void findById_when_ID_NO_exist() {
    Student student = new Student();
    student.setFullName("cristohper");
    student.setGender("male");
    student.setBirthday(LocalDate.of(1965, 01, 25));
    student.setAddress("jwe2932");
    student.setAcademicPeriod("3 Cycle");
    student.setTypeDocument("dni");
    student.setDocument("736723727");

    when(studentRepository.findById(student.getId())).thenReturn(Mono.empty());
    Mono<Student> actual = studentService.findById(student.getId());

    assertResults(actual);
  }
  @Test
  public void save() {



    Student student = new Student();
    student.setFullName("cristohper");
    student.setGender("male");
    student.setBirthday(LocalDate.of(1965, 01, 25));
    student.setAddress("jwe2932");
    student.setAcademicPeriod("3 Cycle");
    student.setTypeDocument("dni");
    student.setDocument("736723727");

    when(studentRepository.save(student)).thenReturn(Mono.just(student));

    Mono<Student> actual = studentService.save(student);

    assertResults(actual, student);
  }

  @Test
  public void delete() {

    Student student = new Student();
    student.setFullName("cristohper");
    student.setGender("male");
    student.setBirthday(LocalDate.of(1965, 01, 25));
    student.setAddress("jwe2932");
    student.setAcademicPeriod("3 Cycle");
    student.setTypeDocument("dni");
    student.setDocument("736723727");
    when(studentRepository.delete(student)).thenReturn(Mono.empty());
  }

  @Test
  public void findByDocument() {

    Student student = new Student();
    student.setFullName("cristohper");
    student.setGender("male");
    student.setBirthday(LocalDate.of(1965, 01, 25));
    student.setAddress("jwe2932");
    student.setAcademicPeriod("3 Cycle");
    student.setTypeDocument("dni");
    student.setDocument("736723727");


    when(studentRepository.findByDocument("736723727")).thenReturn(Mono.just(student));

    Mono<Student> actual = studentService.findByDocument("736723727");

    assertResults(actual, student);
  }


  @Test
  public void findFullName() {
    Student student = new Student();
    student.setFullName("cristohper");
    student.setGender("male");
    student.setBirthday(LocalDate.of(1965, 01, 25));
    student.setAddress("jwe2932");
    student.setAcademicPeriod("3 Cycle");
    student.setTypeDocument("dni");
    student.setDocument("736723727");


    when(studentRepository.findByFullName("cristohper")).thenReturn(Mono.just(student));

    Mono<Student> actual = studentService.findFullName("cristohper");

    assertResults(actual, student);
  }


  @Test
  public void findBybirthdayBetween() {
    Student student = new Student();
    student.setFullName("cristohper");
    student.setGender("male");
    student.setBirthday(LocalDate.of(1965, 01, 25));
    student.setAddress("jwe2932");
    student.setAcademicPeriod("3 Cycle");
    student.setTypeDocument("dni");
    student.setDocument("736723727");

    when(studentRepository.findBybirthdayBetween(
      LocalDate.of(1600, 03, 02), LocalDate.of(2008, 01, 11)))
      .thenReturn(Flux.just(student));

    Flux<Student> actual =
      studentRepository.findBybirthdayBetween(
        LocalDate.of(1600, 03, 02), LocalDate.of(2008, 01, 11));

    assertResults(actual, student);
  }

  private void assertResults(Publisher<Student> publisher, Student... expectedProducts) {
    StepVerifier.create(publisher).expectNext(expectedProducts).verifyComplete();
  }
}
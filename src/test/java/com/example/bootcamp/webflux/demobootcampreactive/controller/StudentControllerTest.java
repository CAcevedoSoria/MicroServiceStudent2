package com.example.bootcamp.webflux.demobootcampreactive.controller;

import com.example.bootcamp.webflux.demobootcampreactive.model.Student;
import com.example.bootcamp.webflux.demobootcampreactive.service.StudentService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/** The type Student controller test. */
@RunWith(SpringRunner.class)
@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class StudentControllerTest {

  @Autowired private WebTestClient client;

  @Autowired private StudentService studentService;

  /** Create. */
  @Test
  public void create() {

    Student parent =
        new Student(
             " Alberto Acevedo Soria", "male", LocalDate.of(1993,02,22), "Cordova 189", "7vo", "dni", "98765654");

    client
        .post()
        .uri("/api/v1.0/students")
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .accept(MediaType.APPLICATION_JSON_UTF8)
        .body(Mono.just(parent), Student.class)
        .exchange()
        .expectStatus()
        .isCreated()
        .expectHeader()
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .expectBodyList(Student.class);
  }

  @Test
  public void findAll() {

    client
        .get()
        .uri("/api/v1.0/students")
        .accept(MediaType.APPLICATION_JSON_UTF8)
        .exchange()
        .expectStatus()
        .isOk()
        .expectHeader()
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .expectBodyList(Student.class)
        .consumeWith(
            response -> {
              List<Student> parents = response.getResponseBody();
              parents.forEach(
                  p -> {
                    System.out.println(p.getFullName());
                  });

              Assertions.assertThat(parents.size() > 0).isTrue();
            });
  }

  @Test
  public void findById() {

    Student parent = studentService.findById("5d84031e36ec183504da04e1").block();
    client
        .get()
        .uri("/api/v1.0/students" + "/{id}", Collections.singletonMap("id", parent.getId()))
        .accept(MediaType.APPLICATION_JSON_UTF8)
        .exchange()
        .expectStatus()
        .isOk()
        .expectHeader()
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .expectBody(Student.class)
        .consumeWith(
            response -> {
              Student p = response.getResponseBody();
              Assertions.assertThat(p.getId()).isNotEmpty();
              Assertions.assertThat(p.getId().length() > 0).isTrue();
            });
  }

  @Test
  public void update() {

    Student parent = studentService.findFullName("hOLIWI").block();

 Student studentedit = new Student("ABCS","Male",LocalDate.of(1998,03,21),"ostopa 599","1 cycle","dni","87267252");



    client
        .put()
        .uri("/api/v1.0/students" + "/{id}", Collections.singletonMap("id", parent.getId()))
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .accept(MediaType.APPLICATION_JSON_UTF8)
        .body(Mono.just(studentedit), Student.class)
        .exchange()
        .expectStatus()
        .isCreated()
        .expectHeader()
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .expectBody()
        .jsonPath("$.id")
        .isNotEmpty()
        .jsonPath("$.id")
        .isEqualTo("5d84049436ec18661417e191");
  }

  @Test
  public void findByDocument() {

    Student parent = studentService.findByDocument("98765677").block();
    client
      .get()
      .uri("/api/v1.0/students" + "/document/{document}", Collections.singletonMap("document", parent.getDocument()))
      .accept(MediaType.APPLICATION_JSON_UTF8)
      .exchange()
      .expectStatus()
      .isOk()
      .expectHeader()
      .contentType(MediaType.APPLICATION_JSON_UTF8)
      .expectBody(Student.class)
      .consumeWith(
        response -> {
          Student p = response.getResponseBody();
          Assertions.assertThat(p.getDocument()).isNotEmpty();
          Assertions.assertThat(p.getDocument().length() > 0).isTrue();
        });
  }

  @Test
  public void findByFullName() {

    Student student = studentService.findFullName("hOLIWI").block();
    client
      .get()
      .uri(
        "/api/v1.0/parents" + "/name/{name}",
        Collections.singletonMap("name", student.getFullName()))
      .accept(MediaType.APPLICATION_JSON_UTF8)
      .exchange()
      .expectStatus()
      .isOk()
      .expectHeader()
      .contentType(MediaType.APPLICATION_JSON_UTF8)
      .expectBody(Student.class)
      .consumeWith(
        response -> {
          Student p = response.getResponseBody();
          Assertions.assertThat(p.getFullName()).isNotEmpty();
          Assertions.assertThat(p.getFullName().length() > 0).isTrue();
        });
  }
  @Test
  public void eliminar() {

    Student parent = studentService.findById("5d8407b436ec1822102a2aba").block();
    client
        .delete()
        .uri("/api/v1.0/students" + "/{id}", Collections.singletonMap("id", parent.getId()))
        .exchange()
        .expectStatus()
        .isNoContent()
        .expectBody()
        .isEmpty();

    client
        .get()
        .uri("/api/v1.0/students" + "/{id}", Collections.singletonMap("id", parent.getId()))
        .exchange()
        .expectStatus()
        .isNotFound()
        .expectBody()
        .isEmpty();
  }
}

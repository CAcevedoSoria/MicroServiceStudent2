package com.example.bootcamp.webflux.demobootcampreactive.controller;

import com.example.bootcamp.webflux.demobootcampreactive.model.Student;
import com.example.bootcamp.webflux.demobootcampreactive.service.StudentService;
import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;



/** The type Student controller. */
@RestController
@RequestMapping("/api/v1.0")
public class StudentController {

  @Autowired private StudentService studentService;

  /**
   * Create mono.
   *
   * @param studentMono the student mono
   * @return the mono
   */
  @PostMapping
  public Mono<ResponseEntity<Map<String, Object>>> create(
      @Valid @RequestBody Mono<Student> studentMono) {

    Map<String, Object> respuesta = new HashMap<String, Object>();

    return studentMono.flatMap(
        student -> {
          if (student.getDateOfBirth() == null) {
            student.setDateOfBirth(new Date());
          }

          return studentService
              .save(student)
              .map(
                  p -> {
                    respuesta.put("producto", p);
                    respuesta.put("mensaje", "Producto creado con éxito");
                    respuesta.put("timestamp", new Date());
                    return ResponseEntity.created(URI.create("/api/v1.0".concat(p.getId())))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .body(respuesta);
                  });
        });
  }

  /**
   * Find all mono.
   *
   * @return the mono
   */
  @GetMapping("/students")
  public Mono<ResponseEntity<Flux<Student>>> findAll() {
    return Mono.just(
        ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .body(studentService.findAll()));
  }

  /**
   * Find by id mono.
   *
   * @param id the id
   * @return the mono
   */
  @GetMapping("/{id}")
  public Mono<ResponseEntity<Student>> findById(@PathVariable String id) {
    return studentService
        .findById(id)
        .map(p -> ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON_UTF8).body(p))
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  /**
   * Update mono.
   *
   * @param student the student
   * @param id the id
   * @return the mono
   */
  @PutMapping("/{id}")
  public Mono<ResponseEntity<Student>> update(
      @RequestBody Student student, @PathVariable String id) {
    return studentService
        .findById(id)
        .flatMap(
            p -> {
              p.setFullName(student.getFullName());
              p.setGender(student.getGender());
              p.setAddress(student.getAddress());
              p.setAcademicPeriod(student.getAcademicPeriod());
              p.setTypeDocument(student.getTypeDocument());
              p.setDocument(student.getDocument());

              return studentService.save(p);
            })
        .map(
            p ->
                ResponseEntity.created(URI.create("/api/v1.0".concat(p.getId())))
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .body(p))
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  /**
   * Eliminar mono.
   *
   * @param id the id
   * @return the mono
   */
  @DeleteMapping("/{id}")
  public Mono<ResponseEntity<Void>> eliminar(@PathVariable String id) {
    return studentService
        .findById(id)
        .flatMap(
            p -> {
              return studentService
                  .delete(p)
                  .then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)));
            })
        .defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
  }

  /**
   * Find by document mono.
   *
   * @param document the document
   * @return the mono
   */
  @GetMapping("document/{document}")
  public Mono<ResponseEntity<Student>> findByDocument(@PathVariable String document) {
    return studentService
        .findByDocument(document)
        .map(p -> ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON_UTF8).body(p))
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  /**
   * Find by full name mono.
   *
   * @param name the name
   * @return the mono
   */
  @GetMapping("name/{name}")
  public Mono<ResponseEntity<Student>> findByFullName(@PathVariable String name) {
    return studentService
        .findFullName(name)
        .map(p -> ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON_UTF8).body(p))
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }
}

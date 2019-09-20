package com.example.bootcamp.webflux.demobootcampreactive.service.impl;

import com.example.bootcamp.webflux.demobootcampreactive.model.Student;
import com.example.bootcamp.webflux.demobootcampreactive.repository.StudentRepository;
import com.example.bootcamp.webflux.demobootcampreactive.service.StudentService;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


/** The type Student service. */
@Service
public class StudentServiceImpl implements StudentService {

  @Autowired private StudentRepository studentRepository;

  @Override
  public Flux<Student> findAll() {
    return studentRepository.findAll();
  }

  @Override
  public Mono<Student> findById(String id) {
    return studentRepository.findById(id);
  }

  @Override
  public Mono<Student> save(Student producto) {
    return studentRepository.save(producto);
  }

  @Override
  public Mono<Void> delete(Student producto) {
    return studentRepository.delete(producto);
  }

  @Override
  public Mono<Student> findByDocument(String document) {
    return studentRepository.findByDocument(document);
  }

  @Override
  public Mono<Student> findByFullName(String name) {
    return studentRepository.findByFullName(name);
  }


  @Override
  public Flux<Student> findBybirthdayBetween(LocalDate date1, LocalDate date2) {
    return studentRepository.findBybirthdayBetween(date1, date2);
  }


}

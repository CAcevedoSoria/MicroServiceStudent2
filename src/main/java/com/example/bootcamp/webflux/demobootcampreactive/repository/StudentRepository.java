package com.example.bootcamp.webflux.demobootcampreactive.repository;

import com.example.bootcamp.webflux.demobootcampreactive.model.Student;
import java.time.LocalDate;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


/** The interface Student repository. */
public interface StudentRepository extends ReactiveMongoRepository<Student, String> {

  /**
   * Find by full name mono.
   *
   * @param name the name
   * @return the mono
   */
  Mono<Student> findByFullName(String name);

  /**
   * Find by document mono.
   *
   * @param document the document
   * @return the mono
   */
  Mono<Student> findByDocument(String document);

  /**
   * Find bybirthday between flux.
   *
   * @param date1 the date 1
   * @param date2 the date 2
   * @return the flux
   */
  Flux<Student> findBybirthdayBetween(LocalDate date1, LocalDate date2);
}

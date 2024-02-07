package br.com.bahcor.springbootauditsample.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

import br.com.bahcor.springbootauditsample.model.entity.Person;

@Repository
public interface PersonRepository extends RevisionRepository<Person, Long, Long>, JpaRepository<Person, Long> {

}

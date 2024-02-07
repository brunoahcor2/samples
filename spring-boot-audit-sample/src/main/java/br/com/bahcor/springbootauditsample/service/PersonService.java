package br.com.bahcor.springbootauditsample.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.history.Revision;
import org.springframework.stereotype.Service;

import br.com.bahcor.springbootauditsample.model.dto.PersonDTO;
import br.com.bahcor.springbootauditsample.model.entity.Person;
import br.com.bahcor.springbootauditsample.repository.PersonAuditRepository;
import br.com.bahcor.springbootauditsample.repository.PersonRepository;

@Service
public class PersonService {

    @Autowired
    private ModelMapper mm;

    @Autowired
    private PersonAuditRepository personAuditRepository;
    @Autowired
    private PersonRepository repository;

    public PersonDTO findById(Long id) {
        return repository.findById(id)
                .map(p -> mm.map(p,PersonDTO.class))
                .orElse(null);
    }

    public List<PersonDTO> findAll() {
        return repository.findAll().stream()
                .map(p -> mm.map(p, PersonDTO.class))
                .collect(Collectors.toList());
    }

    public PersonDTO findLastRevisionByPersonId(Long id) {
        return repository.findLastChangeRevision(id)
                .map(p -> convert(p))
                .orElse(null);
    }

    public List<PersonDTO> findAllRevisionsByPersonId(Long id) {
        return repository.findRevisions(id)
                .stream()
                .map(p -> convert(p))
                .collect(Collectors.toList());
    }

    public List<PersonDTO> findAllChangesRevisionByPersonId(Long id) {
        return personAuditRepository.findAllChangesRevisionByPersonId(id);
    }

    public PersonDTO findChangesLastRevisionByPersonId(Long id) {
        PersonDTO dto = repository.findLastChangeRevision(id)
                .map(p -> convert(p))
                .orElse(null);

        return personAuditRepository.findChangesLastRevisionByPersonId(id, dto.getRevisionNumber());
    }


    private PersonDTO convert(Revision p){
        PersonDTO pdto = mm.map(p.getEntity(), PersonDTO.class);
        // TODO: gambiarra por conta do problema da versao do SPRING BOOT 3.x
        Long revisionNumber = Long.valueOf( String.valueOf(p.getRequiredRevisionNumber()) );
        pdto.setRevisionNumber(revisionNumber);
        pdto.setRevisionType(p.getMetadata().getRevisionType().name());
        return pdto;
    }

    public PersonDTO save(PersonDTO request) {
        return persist(request);
    }

    private PersonDTO persist(PersonDTO request) {
        Person person = mm.map(request, Person.class);
        repository.save(person);
        return mm.map(person, PersonDTO.class);
    }

}

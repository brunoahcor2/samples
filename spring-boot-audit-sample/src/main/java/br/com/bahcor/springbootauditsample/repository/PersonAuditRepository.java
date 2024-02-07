package br.com.bahcor.springbootauditsample.repository;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.RevisionType;
import org.hibernate.envers.query.AuditEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.bahcor.springbootauditsample.model.dto.PersonDTO;
import br.com.bahcor.springbootauditsample.model.entity.Person;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class PersonAuditRepository {

    @Autowired
    private ModelMapper mm;

    @PersistenceContext
    private EntityManager entityManager;

    private AuditReader getAuditReader() {
        return AuditReaderFactory.get(entityManager);
    }

    public List<PersonDTO> findAllChangesRevisionByPersonId(Long personId) {

        List results = (List<Person>) getAuditReader().createQuery()
                .forRevisionsOfEntityWithChanges(Person.class, false)
                .add(AuditEntity.id().eq(personId))
                .getResultList();

        if (results == null || results.size() < 1){
            return null;
        }

        List<PersonDTO> persons = new ArrayList<>();

        for (Object row : results) {

            Object[] rowArray = (Object[]) row;
            Person person = new Person();
            final Person entity = (Person) rowArray[0];
            final RevisionType revisionType = (RevisionType) rowArray[2];
            final Set<String> propertiesChanged = (Set<String>) rowArray[3];

            boolean personChanged = false;
            for (String propertyName : propertiesChanged) {

                Field[] fields = Person.class.getDeclaredFields();
                for (Field f : fields) {
                    if (propertyName.equals(f.getName())) {
                        try {
                            f.setAccessible(true);
                            f.set(person, f.get(entity));
                            personChanged = true;
                        } catch (IllegalArgumentException e) {
                            log.info("Field ["+f.getName()+"], IllegalArgumentException - message = "+e.getMessage());
                        } catch (IllegalAccessException e) {
                            log.info("Field ["+f.getName()+"], IllegalArgumentException - message = "+e.getMessage());
                        }
                    }
                }
            }

            if(personChanged){
                PersonDTO dto = mm.map(person, PersonDTO.class);
                persons.add(dto);
            }
        }

        return persons;
    }

    public PersonDTO findChangesLastRevisionByPersonId(Long personId, Long revisionId) {

        List results = (List<Person>) getAuditReader().createQuery()
                .forRevisionsOfEntityWithChanges(Person.class, false)
                .add(AuditEntity.id().eq(personId))
                .add(AuditEntity.revisionNumber().eq(revisionId))
                .getResultList();

        if (results == null || results.size() < 1){
            return null;
        }

        Person person = new Person();

        Object[] rowArray = (Object[]) results.get(0);
        final Person entity = (Person) rowArray[0];
        final RevisionType revisionType = (RevisionType) rowArray[2];
        final Set<String> propertiesChanged = (Set<String>) rowArray[3];

        boolean personChanged = false;
        for (String propertyName : propertiesChanged) {

            Field[] fields = Person.class.getDeclaredFields();
            for (Field f : fields) {
                if (propertyName.equals(f.getName())) {
                    try {
                        f.setAccessible(true);
                        f.set(person, f.get(entity));
                        personChanged = true;
                    } catch (IllegalArgumentException e) {
                        log.info("Field ["+f.getName()+"], IllegalArgumentException - message = "+e.getMessage());
                    } catch (IllegalAccessException e) {
                        log.info("Field ["+f.getName()+"], IllegalArgumentException - message = "+e.getMessage());
                    }
                }
            }
        }

        if(!personChanged){
            return null;
        }

        PersonDTO dto = mm.map(person, PersonDTO.class);
        dto.setRevisionNumber(revisionId);
        return dto;

    }


}

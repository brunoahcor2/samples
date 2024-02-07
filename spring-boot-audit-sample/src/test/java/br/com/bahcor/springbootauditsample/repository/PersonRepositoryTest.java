package br.com.bahcor.springbootauditsample.repository;

import java.lang.reflect.Field;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import br.com.bahcor.springbootauditsample.model.entity.Person;

// @SpringBootTest
public class PersonRepositoryTest {

    // @Test
    // void test() throws Exception {

    //     Person person = Person.builder().name("teste").lastname("lastname").build();
    //     System.out.println("### Person inicio = "+person);
    //     for (Field field : person.getClass().getDeclaredFields()) {
    //         field.setAccessible(true);
    //         if(!"serialVersionUID".equals(field.getName())){

    //             field.set(person, null);
    //         }

    //     }

    //     System.out.println("### Person final = "+person);


        
    // }


    // @Autowired
    // private PersonRepository repository;

    // @Test
    // void list(){
    //     List<Person> list = repository.findAll();
    //     System.out.println("### list = "+list);
    // }

    // @Test
    // void save(){
    //     Person person = Person.builder().name("Bruno Rocha").build();
    //     Person response = repository.save(person);

    //     List<Person> list = repository.findAll();
    //     System.out.println("### list = "+list);

    //     assertEquals(person, response, "It's diferent!");
    // }

}

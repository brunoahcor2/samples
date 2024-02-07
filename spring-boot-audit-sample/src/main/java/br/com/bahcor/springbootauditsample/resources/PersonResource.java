package br.com.bahcor.springbootauditsample.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.bahcor.springbootauditsample.model.dto.PersonDTO;
import br.com.bahcor.springbootauditsample.service.PersonService;

@RestController
@RequestMapping("persons")
public class PersonResource {

    @Autowired
    private PersonService service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> listAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping(value = "/{id}/revisions", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findAllRevisionsByPersonId(@PathVariable Long id) {
        return ResponseEntity.ok(service.findAllRevisionsByPersonId(id));
    }

    @GetMapping(value = "/{id}/changes-revisions", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findAllChangesRevisionByPersonId(@PathVariable Long id) {
        return ResponseEntity.ok(service.findAllChangesRevisionByPersonId(id));
    }

    @GetMapping(value = "/{id}/changes-last-revision", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findChangesLastRevisionByPersonId(@PathVariable Long id) {
        return ResponseEntity.ok(service.findChangesLastRevisionByPersonId(id));
    }

//    @GetMapping(value = "/{id}/revisions", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<?> findAllRevisionsByPersonId2(@PathVariable Long id) {
//        return ResponseEntity.ok(service.findAllRevisionsByPersonId(id));
//    }
//
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@RequestBody PersonDTO request) {
        return ResponseEntity.ok(service.save(request));
    }


//    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody PersonDTO request) {
//
//        return null;
//
////        return ResponseEntity.ok(repository.findById(id)
////                .map(p -> {
////                    p.setName(request.getName());
////                    p.setLastname(request.getLastname());
////                    p.setContact(request.getContact());
////                    repository.save(p);
////                    return p;
////                })
////                .orElseThrow(() -> new RuntimeException("Not Found")));
//    }

}

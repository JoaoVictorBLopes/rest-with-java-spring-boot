package br.com.projetos.controller;

import br.com.projetos.controller.docs.personControllerDocs;
import br.com.projetos.data.dto.PersonDTO;
import br.com.projetos.services.PersonServices;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/person/v1")
@Tag(name = "People", description =  "Endpoints for Managing People")
public class personController implements personControllerDocs {

    @Autowired
    private PersonServices service;

    @Override
    @GetMapping(value = "/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE,
                        MediaType.APPLICATION_XML_VALUE,
                        MediaType.APPLICATION_YAML_VALUE})
    public PersonDTO findById(@PathVariable("id") Long id) {
        return service.findById(id);
    }

    @Override
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE,
                            MediaType.APPLICATION_XML_VALUE,
                            MediaType.APPLICATION_YAML_VALUE})

    public List<PersonDTO> findAll() {
        return service.findAll();
    }

    @Override
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE,
                            MediaType.APPLICATION_XML_VALUE,
                            MediaType.APPLICATION_YAML_VALUE},
                produces = {MediaType.APPLICATION_JSON_VALUE,
                            MediaType.APPLICATION_XML_VALUE,
                            MediaType.APPLICATION_YAML_VALUE})
    public PersonDTO create(@RequestBody PersonDTO person) {
        return service.create(person);
    }

    @Override
    @PutMapping(consumes = {MediaType.APPLICATION_JSON_VALUE,
                            MediaType.APPLICATION_XML_VALUE,
                            MediaType.APPLICATION_YAML_VALUE},
                produces = {MediaType.APPLICATION_JSON_VALUE,
                            MediaType.APPLICATION_XML_VALUE,
                            MediaType.APPLICATION_YAML_VALUE})
    public PersonDTO update(@RequestBody PersonDTO person) {
        return service.update(person);
    }

    @Override
    @DeleteMapping (value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

package br.com.projetos.services;

import br.com.projetos.controller.personController;
import br.com.projetos.data.dto.PersonDTO;
import br.com.projetos.exception.RequiredObjectIsNullException;
import br.com.projetos.exception.ResourceNotFoundException;
import br.com.projetos.model.Person;
import br.com.projetos.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import static br.com.projetos.mapper.ObjectMapper.parseObjects;
import static br.com.projetos.mapper.ObjectMapper.parseListObjects;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class PersonServices {

    private Logger logger = LoggerFactory.getLogger(PersonServices.class.getName());

    @Autowired
    PersonRepository repository;

    public List<PersonDTO> findAll() {
        var persons = parseListObjects(repository.findAll(), PersonDTO.class);
        persons.forEach(this::addHateoasLinks);
        return persons;
    }

    public PersonDTO findById(Long id) {
        logger.info("Finding one Person!");
        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        var dto = parseObjects(entity, PersonDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    public PersonDTO create(PersonDTO person) {

        if (person == null) throw new RequiredObjectIsNullException();

        logger.info("Create one Person!");
        var entity = parseObjects(person, Person.class);
        var dto = parseObjects(repository.save(entity), PersonDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    public PersonDTO update(PersonDTO person) {

        if (person == null) throw new RequiredObjectIsNullException();

        logger.info("Updating one Person!");
        Person entity = repository.findById(person.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        var dto = parseObjects(repository.save(entity), PersonDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    public void delete(Long id) {

        logger.info("Deleting one Person!");
        Person entity =repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        repository.delete(entity);
    }

    private void addHateoasLinks(PersonDTO dto) {
        dto.add(linkTo(methodOn(personController.class).findById(dto.getId())).withSelfRel().withType("GET"));
        dto.add(linkTo(methodOn(personController.class).findAll()).withRel("findAll").withType("GET"));
        dto.add(linkTo(methodOn(personController.class).create(dto)).withRel("create").withType("POST"));
        dto.add(linkTo(methodOn(personController.class).update(dto)).withRel("update").withType("PUT"));
        dto.add(linkTo(methodOn(personController.class).delete(dto.getId())).withRel("delete").withType("DELETE"));
    }
}

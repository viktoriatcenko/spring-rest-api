package ru.maxima.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.thymeleaf.util.StringUtils;
import ru.maxima.dto.PersonDTO;
import ru.maxima.exceptions.PersonNotCreatedException;
import ru.maxima.exceptions.PersonNotFoundException;
import ru.maxima.exceptions.PersonNotFoundResponse;
import ru.maxima.models.Person;
import ru.maxima.service.PeopleService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/people")
public class PeopleController {

    private final PeopleService peopleService;

    @Autowired
    public PeopleController(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @GetMapping()
    public List<PersonDTO> getAllPeople () {
        List<Person> allPeople = peopleService.getAllPeople();
        List<PersonDTO> result = new ArrayList<>();
        allPeople.forEach(person -> result.add(peopleService.convertToPersonDTO(person)));
        return result; // Jackson конвертирует все объекты в Json
    }

    @GetMapping("/{id}")
    public PersonDTO getPerson(@PathVariable Long id) {
        return peopleService.convertToPersonDTO(peopleService.findById(id));
    }


    @PostMapping()
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid PersonDTO personDTO, BindingResult bindingResult) {
        checkErrors(bindingResult);

        Person person = peopleService.convertToPerson(personDTO);
        peopleService.save(person);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@PathVariable("id") Long id,
                                             @RequestBody @Valid Person person,
                                             BindingResult bindingResult) {
        checkErrors(bindingResult);

        Person updatedPerson = peopleService.findById(id);
        peopleService.update(id, updatedPerson);

        peopleService.save(person);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id) {
        peopleService.deleteById(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    private void checkErrors(BindingResult bindingResult) {
        OrderController.checkErrors(bindingResult);
    }


    @ExceptionHandler({ PersonNotFoundException.class })
    public ResponseEntity<Object> handlePersonNotFoundException() {
        return new ResponseEntity<>(
                "Access denied message here",  HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ PersonNotCreatedException.class })
    public ResponseEntity<Object> handlePersonNotCreatedException(PersonNotCreatedException ex) {
        return new ResponseEntity<>(
                ex.getMessage(), HttpStatus.BAD_REQUEST);
    }


}

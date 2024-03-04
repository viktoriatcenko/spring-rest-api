package ru.maxima.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.maxima.dto.PersonDTO;
import ru.maxima.exceptions.PersonNotFoundException;
import ru.maxima.models.Person;
import ru.maxima.repositories.PeopleRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {

    private final PeopleRepository repository;
    private final ModelMapper modelMapper;

    @Autowired
    public PeopleService(PeopleRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }


    public List<Person> getAllPeople() {
        return repository.findAll();
    }

    public Person findById(Long id) {
        Optional<Person> byId = repository.findById(id);
        return byId.orElseThrow(PersonNotFoundException::new);
    }

    @Transactional
    public void save(Person person) {
        person.setAdmin(false);
        repository.save(person);
    }

    @Transactional
    public void update(Long id, Person editedPerson) {
        editedPerson.setId(id);
        repository.save(editedPerson);
    }

    @Transactional
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Transactional
    public void de(Long id) {
        repository.deleteById(id);
    }

    @Transactional
    public void upgradeToAdmin(Person person) {
        person.setAdmin(true);
        repository.save(person);
    }

    public PersonDTO convertToPersonDTO(Person person) {
        return modelMapper.map(person, PersonDTO.class);
    }

    public Person convertToPerson(PersonDTO personDTO) {
        Person person = modelMapper.map(personDTO, Person.class);

        enrichData(person);

        return person;
    }

    private void enrichData(Person person) {
        person.setCreatedAt(LocalDateTime.now());
        person.setUpdatedAt(LocalDateTime.now());
        person.setCreatedByPerson("ADMIN");
        person.setAdmin(false);
    }
}

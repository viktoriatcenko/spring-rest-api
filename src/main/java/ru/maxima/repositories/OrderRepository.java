package ru.maxima.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.maxima.models.Order;
import ru.maxima.models.Person;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByItemName(String itemName);

    List<Order> findByOwner(Person person);
}

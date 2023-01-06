package ru.fotushin.RESTSensor.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.fotushin.RESTSensor.models.Sensor;
@Repository
public interface SensorRepo extends JpaRepository<Sensor, Integer> {
    Sensor findByName(String name);
}

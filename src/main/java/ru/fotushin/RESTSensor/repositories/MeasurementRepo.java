package ru.fotushin.RESTSensor.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.fotushin.RESTSensor.models.Measurement;
@Repository
public interface MeasurementRepo extends JpaRepository<Measurement, Integer> {
}

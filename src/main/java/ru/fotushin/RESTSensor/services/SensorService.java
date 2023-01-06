package ru.fotushin.RESTSensor.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.fotushin.RESTSensor.models.Sensor;
import ru.fotushin.RESTSensor.repositories.SensorRepo;

import java.util.List;
import java.util.Optional;

@Service
public class SensorService {
    private final SensorRepo sensorRepo;

    @Autowired
    public SensorService(SensorRepo sensorRepo) {
        this.sensorRepo = sensorRepo;
    }

    public Sensor getSensor(int id) {
        Optional<Sensor> sensor = sensorRepo.findById(id);
        return sensor.orElse(null);
    }

    public List<Sensor> getSensors() {
        return sensorRepo.findAll();
    }

    public Sensor findByName(String name){
        return sensorRepo.findByName(name);
    }

    @Transactional
    public void createSensor(Sensor sensor) {
        sensorRepo.save(sensor);
    }

    @Transactional
    public void updateSensor(int id, Sensor updatedSensor) {
        updatedSensor.setId(id);
        sensorRepo.save(updatedSensor);
    }

    @Transactional
    public void deleteSensor(Sensor sensor) {
        sensorRepo.delete(sensor);
    }


}

package ru.fotushin.RESTSensor.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.fotushin.RESTSensor.models.Measurement;
import ru.fotushin.RESTSensor.models.Sensor;
import ru.fotushin.RESTSensor.repositories.MeasurementRepo;
import ru.fotushin.RESTSensor.util.MeasurementsWasNotAddedException;

import java.util.List;
import java.util.Optional;

@Service
public class MeasurementService {
    private final MeasurementRepo measurementRepo;
    private final SensorService sensorService;
@Autowired
    public MeasurementService(MeasurementRepo measurementRepo, SensorService sensorService) {
        this.measurementRepo = measurementRepo;
        this.sensorService = sensorService;
}

    public Measurement getMeasurement(int id){
        Optional<Measurement> measurement = measurementRepo.findById(id);
    return measurement.orElse(null);
    }

    public List<Measurement> getMeasurements(){
        return measurementRepo.findAll();
    }
    @Transactional
    public void createMeasurement(Measurement measurement){
        enrichMeasurement(measurement);
        measurementRepo.save(measurement);
    }

    public void enrichMeasurement(Measurement measurement){
        Sensor sensor = sensorService.findByName(measurement.getSensor().getName());
        if(sensor == null){
            throw new MeasurementsWasNotAddedException("There is no such sensor");
        }else {
            measurement.setSensor(sensor);
        }

    }
    @Transactional
    public void updateMeasurement(int id, Measurement updatedMeasurement){
        updatedMeasurement.setId(id);
        measurementRepo.save(updatedMeasurement);
    }
    @Transactional
    public void deleteMeasurement(Measurement measurement){
        measurementRepo.delete(measurement);
    }


}

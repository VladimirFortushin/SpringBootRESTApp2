package ru.fotushin.RESTSensor.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.fotushin.RESTSensor.dto.SensorDTO;
import ru.fotushin.RESTSensor.models.Sensor;
import ru.fotushin.RESTSensor.services.SensorService;
import ru.fotushin.RESTSensor.util.SensorWasNotAddedException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sensors")
public class SensorController {

    private final SensorService sensorService;
    private final ModelMapper modelMapper;

    @Autowired
    public SensorController(SensorService sensorService, ModelMapper modelMapper) {
        this.sensorService = sensorService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<SensorDTO> getSensors() {
        List<Sensor> sensors = sensorService.getSensors();
        return sensors.stream()
                .map(x -> modelMapper.map(x, SensorDTO.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public SensorDTO getSensor(@PathVariable("id") int id){
        Sensor sensor = sensorService.getSensor(id);
        return modelMapper.map(sensor, SensorDTO.class);
    }

    @PostMapping("/new")
    private ResponseEntity<HttpStatus> createSensor(@RequestBody @Valid Sensor sensor,
                                                    BindingResult bindingResult){
        if(bindingResult.hasErrors()){

            StringBuilder errorBldr = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            errors
                    .forEach(x ->
                            errorBldr
                                    .append(x.getField())
                                    .append(" -- ")
                                    .append(x.getDefaultMessage())
                                    .append(";"));
            throw new SensorWasNotAddedException(errorBldr.toString());

        }
        sensorService.createSensor(sensor);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

}

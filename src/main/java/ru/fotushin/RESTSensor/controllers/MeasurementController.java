package ru.fotushin.RESTSensor.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.fotushin.RESTSensor.dto.MeasurementDTO;
import ru.fotushin.RESTSensor.models.Measurement;
import ru.fotushin.RESTSensor.services.MeasurementService;
import ru.fotushin.RESTSensor.util.MeasurementErrorResponse;
import ru.fotushin.RESTSensor.util.MeasurementsWasNotAddedException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/measurements")
public class MeasurementController {
    private final MeasurementService measurementService;
    private final ModelMapper modelMapper;


    @Autowired
    public MeasurementController(MeasurementService measurementService, ModelMapper modelMapper) {
        this.measurementService = measurementService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<MeasurementDTO> getMeasurements(){
        List<Measurement> measurements = measurementService.getMeasurements();
        return measurements.stream()
                .map(measurement -> modelMapper.map(measurement, MeasurementDTO.class))
                .collect(Collectors.toList());
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addMeasurements(@RequestBody @Valid Measurement measurement,

                                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            errors
                    .forEach(x -> errorMessage
                            .append(x.getField())
                            .append(" -- ")
                            .append(x.getDefaultMessage())
                            .append(";"));

            throw new MeasurementsWasNotAddedException(errorMessage.toString());
        }


        measurementService.createMeasurement(measurement);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<MeasurementErrorResponse> handleException(MeasurementsWasNotAddedException e) {
        MeasurementErrorResponse measurementErrorResponse = new MeasurementErrorResponse(
                "Sensor with such name was not found");
        return new ResponseEntity<>(measurementErrorResponse, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/rainyDays")
    public List<MeasurementDTO> getRainyDays() {
        return measurementService.getMeasurements()
                .stream()
                .filter(Measurement::isRaining)
                .map(rainyDays -> modelMapper.map(rainyDays, MeasurementDTO.class))
                .collect(Collectors.toList());
    }
}

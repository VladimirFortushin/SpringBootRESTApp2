package ru.fotushin.RESTSensor.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import ru.fotushin.RESTSensor.models.Sensor;

public class MeasurementDTO {
    @NotEmpty(message = "The temperature can't be empty")
    @Min(value = -100, message = "The temperature can't be below -100 degrees")
    @Max(value = 100, message = "The temperature can't be greater than 100 degrees")
    @Column(name = "value")
    private double value;
    @NotEmpty(message = "Please, point whether it's raining")
    @Column(name = "raining")
    private boolean raining;
    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "sensor_name", referencedColumnName = "name")
    private Sensor sensor;

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public boolean isRaining() {
        return raining;
    }

    public void setRaining(boolean raining) {
        this.raining = raining;
    }

    public Sensor getSensor() {

        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }
}

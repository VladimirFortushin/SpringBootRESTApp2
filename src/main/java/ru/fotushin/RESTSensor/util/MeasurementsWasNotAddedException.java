package ru.fotushin.RESTSensor.util;

public class MeasurementsWasNotAddedException extends RuntimeException {
    public MeasurementsWasNotAddedException(String message){
        super(message);
    }
}

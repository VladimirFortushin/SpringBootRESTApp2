package ru.fotushin.RESTSensor.util;




public class SensorWasNotAddedException extends RuntimeException {
    public SensorWasNotAddedException(String message){
        super(message);
    }

}

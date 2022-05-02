package edu.school21.models;


import java.io.InputStream;

@OrmEntity(table = "car_table")
public class Car {
    @OrmColumn(name = "model", length = 10)
    private String model;
    @OrmColumn(name = "year", length = 10)
    private Integer year;
    @OrmColumn(name = "maxSpeed")
    private Integer maxSpeed;

    public Car(String model, Integer year, Integer maxSpeed) {
        this.model = model;
        this.year = year;
        this.maxSpeed = maxSpeed;
    }
}

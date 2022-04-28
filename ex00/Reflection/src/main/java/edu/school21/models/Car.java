package edu.school21.models;

public class Car {

    private String model;
    private Integer speed;
    private Double length;
    private Boolean good;
    private Long width;

    public Car() {
    }

    public Car(String model, Integer speed, Double length, Boolean good, Long width) {
        this.model = model;
        this.speed = speed;
        this.length = length;
        this.good = good;
        this.width = width;
    }

    public int addSpeed(int value) {
        this.speed += value;
        return speed;
    }

    public String modelVersion(String verison) {
        this.model += " " + verison;
        return model;
    }

    @Override
    public String toString() {
        return "Car{" +
                "model='" + model + '\'' +
                ", speed=" + speed +
                ", length=" + length +
                ", isGood=" + good +
                ", width=" + width +
                '}';
    }
}

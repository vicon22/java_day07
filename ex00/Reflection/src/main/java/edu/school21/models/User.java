package edu.school21.models;

public class User {
    private String firstName;
    private String lastName;
    private Integer height;
    private Double aDouble;
    private Long aLong;
    private Boolean aBoolean;

    public String getNicknameInGame(String nickname, String game) {
        return firstName + " " + lastName + " is " + nickname + " in " + game;
    }

    public int grow(int value) {
        this.height += value;
        return height;
    }

    public User() {
    }

    public User(String firstName, String lastName, Integer height, Double aDouble, Long aLong, Boolean aBoolean) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.height = height;
        this.aDouble = aDouble;
        this.aLong = aLong;
        this.aBoolean = aBoolean;
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", height=" + height +
                ", aDouble=" + aDouble +
                ", aLong=" + aLong +
                ", aBoolean=" + aBoolean +
                '}';
    }
}

package com.jorgebarrios.expensetracker.user.input;

public class SignUpInput {
    private String name;
    private String lastName;
    private String email;
    private String password;
    private String gender;

    public SignUpInput(String name, String lastName, String email, String password, String gender) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.gender = gender.toLowerCase();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Byte getGender() {
        return switch (gender) {
            case "1" -> 1;
            case "2" -> 2;
            case "3" -> 0;
            default -> 3;
        };
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}

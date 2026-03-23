package com.fahym.tas.data.models;

import java.util.Objects;

public final class UserData {
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String password;

    public UserData(String firstName, String lastName, String email, String password) {
        this.firstName = Objects.requireNonNull(firstName, "firstName");
        this.lastName = Objects.requireNonNull(lastName, "lastName");
        this.email = Objects.requireNonNull(email, "email");
        this.password = Objects.requireNonNull(password, "password");
    }

    public String firstName() { return firstName; }
    public String lastName() { return lastName; }
    public String email() { return email; }
    public String password() { return password; }
}
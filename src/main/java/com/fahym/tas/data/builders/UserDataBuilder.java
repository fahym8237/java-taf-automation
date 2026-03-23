package com.fahym.tas.data.builders;

import com.fahym.tas.data.generation.Unique;
import com.fahym.tas.data.models.UserData;

public final class UserDataBuilder {
    private String firstName = "Test";
    private String lastName = "User";
    private String email = Unique.email("test.user");
    private String password = "P@ssw0rd12345";

    private UserDataBuilder() {}

    public static UserDataBuilder aUser() {
        return new UserDataBuilder();
    }

    public UserDataBuilder firstName(String v) { this.firstName = v; return this; }
    public UserDataBuilder lastName(String v) { this.lastName = v; return this; }
    public UserDataBuilder email(String v) { this.email = v; return this; }
    public UserDataBuilder password(String v) { this.password = v; return this; }

    public UserData build() {
        return new UserData(firstName, lastName, email, password);
    }
}
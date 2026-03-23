package com.fahym.tas.data.factories;

import com.fahym.tas.data.builders.UserDataBuilder;
import com.fahym.tas.data.generation.Unique;
import com.fahym.tas.data.models.UserData;

public final class UserDataFactory {
    private UserDataFactory() {}

    /** Valid synthetic user for registration flows */
    public static UserData validRegistrationUser() {
        return UserDataBuilder.aUser()
                .firstName("Reg")
                .lastName("User")
                .email(Unique.email("reg.user"))
                .password("P@ssw0rd12345")
                .build();
    }

    /** Minimal invalid user (for negative tests) */
    public static UserData emptyUser() {
        return new UserData("", "", "", "");
    }
}
package com.fahym.tas.data;

import com.fahym.tas.data.factories.UserDataFactory;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TestDataSmokeTest {

    @Test
    void shouldGenerateUniqueEmails() {
        var u1 = UserDataFactory.validRegistrationUser();
        var u2 = UserDataFactory.validRegistrationUser();

        assertThat(u1.email()).isNotBlank();
        assertThat(u2.email()).isNotBlank();
        assertThat(u1.email()).isNotEqualTo(u2.email());
        assertThat(u1.email()).contains("@example.test");
    }
}
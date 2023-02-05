package com.techeule.cms.hugo.pages.control;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ConfigurationsTest {

    @ParameterizedTest
    @CsvSource(",--config,--config , --config = ")
    void loadConfigWithoutConfigFileValue(final String config) {
        final var configurations = new Configurations(new String[]{config});
        configurations.load();

        assertThat(configurations.getAllConfigurations()).isEmpty();
    }

    @AfterEach
    void tearDown() {
        System.clearProperty("Hello");
    }

    @Test
    void loadConfigFromFileOnly() {
        final var configFile = ConfigurationsTest.class.getClassLoader()
                                                       .getResource("hugo-page-visits.properties")
                                                       .getPath();

        for (final var config : List.of("--config ", "--config", "--config = ", "--config=")) {
            final var configurations = new Configurations(new String[]{config + configFile});
            configurations.load();
            assertThat(configurations.getAllConfigurations()).isNotEmpty();
            assertThat(configurations.get("Hello")).isEqualTo("World");
        }
    }

    @Test
    void loadConfigFromFileAndJvmOptions() {
        final var configFile = ConfigurationsTest.class.getClassLoader()
                                                       .getResource("hugo-page-visits.properties")
                                                       .getPath();

        for (final var config : List.of("--config ", "--config", "--config = ", "--config=")) {
            final var configurations = new Configurations(new String[]{config + configFile});
            System.setProperty("Hello", "Welt");
            configurations.load();
            assertThat(configurations.getAllConfigurations()).isNotEmpty();
            assertThat(configurations.get("Hello")).isEqualTo("Welt");
        }
    }

    @Test
    void loadConfigFromFileAndJvmOptionsAndEnvironment() {
        final var configFile = ConfigurationsTest.class.getClassLoader()
                                                       .getResource("hugo-page-visits.properties")
                                                       .getPath();

        for (final var config : List.of("--config ", "--config", "--config = ", "--config=")) {
            final var configurations = new Configurations(new String[]{config + configFile});
            System.setProperty("HOME", "CC73A96B-24A6-4600-8564-B6C31D527826-Paul");
            configurations.load();
            assertThat(configurations.getAllConfigurations()).isNotEmpty();
            assertThat(configurations.get("HOME"))
                    .isNotNull()
                    .isNotBlank()
                    .isNotEqualTo("CC73A96B-24A6-4600-8564-B6C31D527826")
                    .isNotEqualTo("CC73A96B-24A6-4600-8564-B6C31D527826-Paul");
        }
    }
}
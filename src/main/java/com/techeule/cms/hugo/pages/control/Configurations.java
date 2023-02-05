package com.techeule.cms.hugo.pages.control;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Pattern;

public class Configurations {
    private static final Set<String> configKeys = Set.of(
            "matomo_auth_token",
            "matomo_siteId",
            "matomo_baseUri",
            "hugo_pagesList",
            "hugo_dataFile"
    );
    public static final String CONFIG_FILE_ARGUMENT = "--config";
    private static final Pattern CONFIG_FILE_PATTERN = Pattern.compile(CONFIG_FILE_ARGUMENT + "\\s*(=\\s*)?");
    private final Map<String, String> config = new HashMap<>(1);
    private final String[] args;

    public Configurations(final String[] args) {
        this.args = args;
    }

    public void load() {
        for (final var arg : args) {
            loadConfig(arg);
        }
        loadConfigFromJvmOptions();
        loadConfigFromEnvironment();
    }

    private void loadConfigFromJvmOptions() {
        final var keys = new HashSet<>(config.keySet());
        keys.addAll(configKeys);
        for (final var key : keys) {
            final var jvmOptConfigValue = System.getProperty(key);
            if (jvmOptConfigValue != null) {
                config.put(key, jvmOptConfigValue);
            }
        }
    }

    private void loadConfigFromEnvironment() {
        final var keys = new HashSet<>(config.keySet());
        keys.addAll(configKeys);
        for (final var key : keys) {
            final var envConfigValue = System.getenv(key);
            if (envConfigValue != null) {
                config.put(key, envConfigValue);
            }
        }
    }

    private void loadConfig(final String arg) {
        if ((arg != null) && !arg.isBlank()) {
            var trimmed = arg.trim();
            if (trimmed.startsWith(CONFIG_FILE_ARGUMENT)) {
                trimmed = CONFIG_FILE_PATTERN.matcher(trimmed).replaceFirst("");
                if (!trimmed.isBlank()) {
                    loadConfig(Path.of(trimmed));
                }
            }
        }
    }

    private void loadConfig(final Path configFilePath) {
        try (final var configFileStream = Files.newInputStream(configFilePath)) {
            final var prop = new Properties();
            prop.load(configFileStream);
            prop.forEach((key, value) -> config.put(key.toString(), value.toString()));
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String get(final String configKey) {
        return config.get(configKey);
    }

    public Map<String, String> getAllConfigurations() {
        return Map.copyOf(config);
    }

    public String getMatomoAuthToken() {
        return get("matomo_auth_token");
    }

    public String getMatomoSiteId() {
        return get("matomo_siteId");
    }

    public URI getMatomoBaseUri() {
        return URI.create(get("matomo_baseUri"));
    }

    public String getHugoPagesList() {
        return get("hugo_pagesList");
    }

    public String getHugoDataFile() {
        return get("hugo_dataFile");
    }
}
package com.techeule.cms.hugo.pages.boundary;

import java.io.OutputStream;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;

public class StatisticsWriter {
    private static final Jsonb JSONB = JsonbBuilder.newBuilder().build();

    public void write(final OutputStream outputStream,
                      final Object data) {
        JSONB.toJson(data, outputStream);
    }
}

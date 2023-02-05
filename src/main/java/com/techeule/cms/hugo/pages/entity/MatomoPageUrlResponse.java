package com.techeule.cms.hugo.pages.entity;

import jakarta.json.bind.annotation.JsonbProperty;
import lombok.Data;

@Data
public class MatomoPageUrlResponse {
    @JsonbProperty("label")
    private String label;
    @JsonbProperty("nb_visits")
    private long numberOfVisits;
    @JsonbProperty("nb_hits")
    private long numberOfHits;
    @JsonbProperty("url")
    private String url;
}

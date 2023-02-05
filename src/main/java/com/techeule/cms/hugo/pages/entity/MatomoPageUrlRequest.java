package com.techeule.cms.hugo.pages.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class MatomoPageUrlRequest {
    private String tokenAuth;
    private String format;
    private String module;
    private String method;
    private String pageUrl;
    private String idSite;
    private String period;
    private String date;
}

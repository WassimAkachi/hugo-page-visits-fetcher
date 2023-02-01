package com.techeule.cms.hugo.pages.control.matono;

import jakarta.ws.rs.QueryParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class MatomoPageUrlRequest {
    @QueryParam("token_auth")
    private String tokenAuth;
    @QueryParam("format")
    private String format;
    @QueryParam("module")
    private String module;
    @QueryParam("method")
    private String method;
    @QueryParam("pageUrl")
    private String pageUrl;
    @QueryParam("idSite")
    private String idSite;
    @QueryParam("period")
    private String period;
    @QueryParam("date")
    private String date;
}

package com.techeule.cms.hugo.pages.boundary;

import java.util.ArrayList;
import java.util.List;

import com.techeule.cms.hugo.pages.control.matono.Actions;
import com.techeule.cms.hugo.pages.control.matono.MatomoPageUrlRequest;
import com.techeule.cms.hugo.pages.control.matono.MatomoPageUrlResponse;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.ws.rs.core.Response;

public class PageStatisticFetcher {
    private static final Jsonb JSONB = JsonbBuilder.newBuilder().build();
    private final Actions actions;
    private final String authToken;

    public PageStatisticFetcher(final Actions actions,
                                final String authToken) {
        this.actions = actions;
        this.authToken = authToken;
    }

    public List<MatomoPageUrlResponse> fetch(final String siteId,
                                             final String url,
                                             final String period,
                                             final String date) {
        final var matomoPageUrlRequest = MatomoPageUrlRequest.builder()
                                                             .module("API")
                                                             .method("Actions.getPageUrl")
                                                             .format("JSON")
                                                             .idSite(siteId)
                                                             .period(period)
                                                             .date(date)
                                                             .pageUrl(url)
                                                             .tokenAuth(authToken)
                                                             .build();

        final Response pageUrl = actions.getPageUrl(matomoPageUrlRequest);
        return JSONB.fromJson(pageUrl.readEntity(String.class), ListOfMatomoPageUrlResponse.class.getGenericSuperclass());
    }

    private static class ListOfMatomoPageUrlResponse extends ArrayList<MatomoPageUrlResponse> {
    }
}

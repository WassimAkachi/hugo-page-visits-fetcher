package com.techeule.cms.hugo.pages.boundary;

import java.util.List;

import com.techeule.cms.hugo.pages.control.matono.MatomoPageUrlRequest;
import com.techeule.cms.hugo.pages.control.matono.MatomoPageUrlResponse;
import com.techeule.cms.hugo.pages.control.matono.PlainJavaActions;

public class PageStatisticFetcher {
    private final PlainJavaActions actions;
    private final String authToken;

    public PageStatisticFetcher(final PlainJavaActions actions,
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

        return actions.getPageUrl(matomoPageUrlRequest);
    }
}

package com.techeule.cms.hugo.pages.control;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.Supplier;

import com.techeule.cms.hugo.pages.entity.LineItem;
import com.techeule.cms.hugo.pages.entity.MatomoPageUrlRequest;
import com.techeule.cms.hugo.pages.entity.MatomoPageUrlResponse;

public class PageStatisticFetcher {
    private final MatomoActionsClient actionsClient;
    private final String authToken;
    private final String siteId;
    private final Supplier<ZonedDateTime> dateTimeSupplier;

    public PageStatisticFetcher(final MatomoActionsClient actionsClient,
                                final String authToken,
                                final String siteId) {
        this(actionsClient, authToken, siteId, ZonedDateTime::now);
    }

    PageStatisticFetcher(final MatomoActionsClient actionsClient,
                         final String authToken,
                         final String siteId,
                         final Supplier<ZonedDateTime> dateTimeSupplier) {
        this.actionsClient = actionsClient;
        this.authToken = authToken;
        this.siteId = siteId;
        this.dateTimeSupplier = dateTimeSupplier;
    }

    public List<MatomoPageUrlResponse> fetch(final LineItem lineItem) {
        final var matomoPageUrlRequest = MatomoPageUrlRequest.builder()
                                                             .module("API")
                                                             .method("Actions.getPageUrl")
                                                             .format("JSON")
                                                             .idSite(siteId)
                                                             .period("range")
                                                             .date(getRange(lineItem))
                                                             .pageUrl(lineItem.getPermalink())
                                                             .tokenAuth(authToken)
                                                             .build();
        return actionsClient.getPageUrl(matomoPageUrlRequest);
    }

    private String getRange(final LineItem lineItem) {
        final var from = DateTimeFormatter.ISO_LOCAL_DATE.format(lineItem.getDate());
        // Plus 1 day in case the application is running at 12:59 pm
        final var until = DateTimeFormatter.ISO_LOCAL_DATE.format(dateTimeSupplier.get().plusDays(1));
        return from + ',' + until;
    }
}

package com.techeule.cms.hugo.pages.boundary;

import java.net.URI;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.techeule.cms.hugo.pages.control.matono.MatomoRestClientFactory;

class PageStatisticFetcherTest {
    private final PageStatisticFetcher pageStatisticFetcher;

    PageStatisticFetcherTest() {
        final var matomoRestClientFactory = new MatomoRestClientFactory(URI.create("https://wat.techeule.de"));
        final var actions = matomoRestClientFactory.crearPlainJavaActions();
        final var authToken = System.getenv("MATOMO_TOKEN");
        pageStatisticFetcher = new PageStatisticFetcher(actions, authToken);
    }

    @Test
    @Disabled("Run only manually")
    void name() {
        final var matomoPageUrlResponses = pageStatisticFetcher
                .fetch(
                        "2",
                        "https://techeule.com/posts/20230121-001_hello-world/",
                        "range",
                        "1992-01-01,2100-12-31");

        matomoPageUrlResponses.forEach(System.out::println);
    }
}
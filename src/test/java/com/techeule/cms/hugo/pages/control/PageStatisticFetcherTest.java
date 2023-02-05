package com.techeule.cms.hugo.pages.control;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import com.techeule.cms.hugo.pages.entity.LineItem;
import com.techeule.cms.hugo.pages.entity.MatomoPageUrlRequest;
import com.techeule.cms.hugo.pages.entity.MatomoPageUrlResponse;

class PageStatisticFetcherTest {
    private final String authToken = UUID.randomUUID().toString();
    private final MatomoActionsClient actionsMock = mock(MatomoActionsClient.class);
    private final String siteId = UUID.randomUUID().toString();

    @Test
    void name() {
        final List<MatomoPageUrlResponse> responseListMock = mock(List.class);
        when(actionsMock.getPageUrl(any())).thenReturn(responseListMock);
        final var now = ZonedDateTime.now().plusDays(10);
        final var pageStatisticFetcher = new PageStatisticFetcher(actionsMock, authToken, siteId, () -> now);
        final var lineItem = new LineItem();
        lineItem.setPermalink("https://some." + UUID.randomUUID() + ".localhost");
        lineItem.setDate(ZonedDateTime.now().minusDays(10));

        final var matomoPageUrlResponses = pageStatisticFetcher.fetch(lineItem);

        assertThat(matomoPageUrlResponses).isSameAs(responseListMock);
        final var argumentCaptor = ArgumentCaptor.forClass(MatomoPageUrlRequest.class);
        verify(actionsMock).getPageUrl(argumentCaptor.capture());
        final var urlRequest = argumentCaptor.getValue();
        assertThat(urlRequest.getModule()).isEqualTo("API");
        assertThat(urlRequest.getMethod()).isEqualTo("Actions.getPageUrl");
        assertThat(urlRequest.getFormat()).isEqualTo("JSON");
        assertThat(urlRequest.getIdSite()).isEqualTo(siteId);
        assertThat(urlRequest.getPeriod()).isEqualTo("range");
        assertThat(urlRequest.getPageUrl()).isEqualTo(lineItem.getPermalink());
        assertThat(urlRequest.getTokenAuth()).isEqualTo(authToken);
        assertThat(urlRequest.getDate()).isEqualTo(
                ISO_LOCAL_DATE.format(lineItem.getDate()) +
                        ',' +
                        ISO_LOCAL_DATE.format(now.plusDays(1))
        );
    }
}
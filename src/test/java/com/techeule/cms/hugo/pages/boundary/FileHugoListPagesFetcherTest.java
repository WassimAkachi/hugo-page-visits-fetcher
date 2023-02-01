package com.techeule.cms.hugo.pages.boundary;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.techeule.cms.hugo.pages.control.OpenCsvHugoListParser;

class FileHugoListPagesFetcherTest {
    private final OpenCsvHugoListParser openCsvHugoListParserMock = mock(OpenCsvHugoListParser.class);
    private final FileHugoListPagesFetcher fileHugoListPagesFetcher = new FileHugoListPagesFetcher(openCsvHugoListParserMock);

    @Test
    void readFileTest() {
        final var lineItemsMock = mock(List.class);

        when(openCsvHugoListParserMock.parse(anyString())).thenReturn(lineItemsMock);

        final var path = UUID.randomUUID().toString();
        final var lineItems = fileHugoListPagesFetcher.readAll(path);

        assertThat(lineItems).isSameAs(lineItemsMock);
    }
}
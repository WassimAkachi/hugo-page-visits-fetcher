package com.techeule.cms.hugo.pages;

import java.io.FileOutputStream;
import java.net.URI;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;

import com.techeule.cms.hugo.pages.boundary.FileHugoListPagesFetcher;
import com.techeule.cms.hugo.pages.boundary.PageStatisticFetcher;
import com.techeule.cms.hugo.pages.boundary.StatisticsWriter;
import com.techeule.cms.hugo.pages.control.OpenCsvHugoListParser;
import com.techeule.cms.hugo.pages.control.matono.MatomoPageUrlResponse;
import com.techeule.cms.hugo.pages.control.matono.MatomoRestClientFactory;
import com.techeule.cms.hugo.pages.entity.LineItem;

public class Application {

    private static final FileHugoListPagesFetcher fileHugoListPagesFetcher = new FileHugoListPagesFetcher(new OpenCsvHugoListParser());
    private static PageStatisticFetcher pageStatisticFetcher;
    private static final StatisticsWriter statisticsWriter = new StatisticsWriter();

    public static void main(final String[] args) {
        final Config config = ConfigProvider.getConfig();
        final var authToken = config.getValue("matomo.auth_token", String.class);
        final var siteId = config.getValue("matomo.siteId", String.class);
        final var baseUri = config.getValue("matomo.baseUri", String.class);
        final var hugoPagesFile = config.getValue("hugo.pagesList", String.class);
        final var hugoDataFile = config.getValue("hugo.dataFile", String.class);

        final var matomoRestClientFactory = new MatomoRestClientFactory(URI.create(baseUri));
        pageStatisticFetcher = new PageStatisticFetcher(matomoRestClientFactory.crearPlainJavaActions(), authToken);

        final var lineItems = fileHugoListPagesFetcher.readAll(hugoPagesFile);
        final var statistics = getStatistics(siteId, lineItems);
        writeStatistics(hugoDataFile, statistics);
    }

    private static Map<String, MatomoPageUrlResponse> getStatistics(final String siteId,
                                                                    final Collection<LineItem> lineItems) {
        final Map<String, MatomoPageUrlResponse> statistics = new HashMap<>(lineItems.size());
        lineItems.forEach(li -> {
            System.out.println("fetching statistics for " + li.getPermalink());
            final var matomoPageUrlResponses = pageStatisticFetcher.fetch(siteId,
                                                                          li.getPermalink(),
                                                                          "range",
                                                                          "1992-01-01,2100-12-31");

            if ((matomoPageUrlResponses != null) && !matomoPageUrlResponses.isEmpty()) {
                statistics.put(li.getPermalink(), matomoPageUrlResponses.get(0));
            }
        });
        return statistics;
    }

    private static void writeStatistics(final String hugoDataFile,
                                        final Map<String, MatomoPageUrlResponse> statistics) {
        System.out.println("start writing statistics in to " + hugoDataFile);
        try (final var output = new FileOutputStream(hugoDataFile)) {
            statisticsWriter.write(output, statistics);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

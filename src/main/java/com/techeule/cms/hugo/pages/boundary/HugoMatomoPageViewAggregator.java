package com.techeule.cms.hugo.pages.boundary;

import java.io.FileOutputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.techeule.cms.hugo.pages.control.Configurations;
import com.techeule.cms.hugo.pages.control.MatomoActionsClient;
import com.techeule.cms.hugo.pages.control.OpenCsvHugoListParser;
import com.techeule.cms.hugo.pages.control.PageStatisticFetcher;
import com.techeule.cms.hugo.pages.entity.LineItem;
import com.techeule.cms.hugo.pages.entity.MatomoPageUrlResponse;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;

public class HugoMatomoPageViewAggregator {

    private static final Jsonb JSONB = JsonbBuilder.newBuilder().build();
    private final Configurations configurations;
    private final MatomoActionsClient matomoActionsClient;
    private final PageStatisticFetcher pageStatisticFetcher;
    private final OpenCsvHugoListParser openCsvHugoListParser = new OpenCsvHugoListParser();
    private final String hugoPagesFile;
    private final String hugoDataFile;
    private final String siteId;

    public HugoMatomoPageViewAggregator(final String[] arguments) {
        configurations = new Configurations(arguments);
        configurations.load();
        final var authToken = configurations.getMatomoAuthToken();
        final var baseUri = configurations.getMatomoBaseUri();
        hugoPagesFile = configurations.getHugoPagesList();
        hugoDataFile = configurations.getHugoDataFile();
        siteId = configurations.getMatomoSiteId();
        matomoActionsClient = new MatomoActionsClient(baseUri);
        pageStatisticFetcher = new PageStatisticFetcher(matomoActionsClient, authToken, siteId);
    }

    public void generate() {
        final var lineItems = openCsvHugoListParser.parse(hugoPagesFile);
        final var statistics = getStatistics(lineItems);
        writeStatistics(statistics);
    }

    private Map<String, MatomoPageUrlResponse> getStatistics(final Collection<LineItem> lineItems) {
        final Map<String, MatomoPageUrlResponse> statistics = new HashMap<>(lineItems.size());
        lineItems.forEach(li -> {
            final var matomoPageUrlResponses = pageStatisticFetcher.fetch(li);

            if ((matomoPageUrlResponses != null) && !matomoPageUrlResponses.isEmpty()) {
                statistics.put(li.getPermalink(), matomoPageUrlResponses.get(0));
            }
        });
        return statistics;
    }

    private void writeStatistics(final Map<String, MatomoPageUrlResponse> statistics) {
        try (final var output = new FileOutputStream(hugoDataFile)) {
            JSONB.toJson(statistics, output);
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }
}

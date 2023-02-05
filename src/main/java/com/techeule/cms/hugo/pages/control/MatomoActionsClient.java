package com.techeule.cms.hugo.pages.control;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import com.techeule.cms.hugo.pages.entity.ListOfMatomoPageUrlResponse;
import com.techeule.cms.hugo.pages.entity.MatomoPageUrlRequest;
import com.techeule.cms.hugo.pages.entity.MatomoPageUrlResponse;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;

public class MatomoActionsClient {
    private static final Jsonb JSONB = JsonbBuilder.newBuilder().build();
    private static final HttpClient httpClient = HttpClient.newBuilder().build();
    private final URI matomoBaseUri;

    public MatomoActionsClient(final URI matomoBaseUri) {
        this.matomoBaseUri = matomoBaseUri;
    }

    public List<MatomoPageUrlResponse> getPageUrl(final MatomoPageUrlRequest request) {
        final var httpRequest = getHttpRequest(request);
        final HttpResponse<String> httpResponse;
        try {
            httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        } catch (final InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }
        final String body = httpResponse.body();
        return JSONB.fromJson(body, ListOfMatomoPageUrlResponse.class.getGenericSuperclass());
    }

    private HttpRequest getHttpRequest(final MatomoPageUrlRequest request) {
        final var targetUri = getTargetUri(request);
        return HttpRequest.newBuilder()
                          .GET()
                          .uri(targetUri)
                          .build();
    }

    private URI getTargetUri(final MatomoPageUrlRequest request) {
        try {
            return URI.create(matomoBaseUri.toURL() +
                                      "?token_auth=" + request.getTokenAuth() +
                                      "&format=" + request.getFormat() +
                                      "&module=" + request.getModule() +
                                      "&method=" + request.getMethod() +
                                      "&idSite=" + request.getIdSite() +
                                      "&period=" + request.getPeriod() +
                                      "&date=" + request.getDate() +
                                      "&pageUrl=" + request.getPageUrl());
        } catch (final MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}

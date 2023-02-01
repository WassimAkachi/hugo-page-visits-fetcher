package com.techeule.cms.hugo.pages.control.matono;

import java.net.URI;

import org.eclipse.microprofile.rest.client.RestClientBuilder;

public class MatomoRestClientFactory {
    private final URI matomoBaseUri;

    public MatomoRestClientFactory(final URI matomoBaseUri) {
        this.matomoBaseUri = matomoBaseUri;
    }

    public Actions createActionsClient() {
        return RestClientBuilder.newBuilder()
                                .baseUri(matomoBaseUri)
                                .build(Actions.class);
    }
}

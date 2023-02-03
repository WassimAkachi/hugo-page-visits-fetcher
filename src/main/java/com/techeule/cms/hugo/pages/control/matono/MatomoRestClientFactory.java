package com.techeule.cms.hugo.pages.control.matono;

import java.net.URI;

public class MatomoRestClientFactory {
    private final URI matomoBaseUri;

    public MatomoRestClientFactory(final URI matomoBaseUri) {
        this.matomoBaseUri = matomoBaseUri;
    }

    public PlainJavaActions crearPlainJavaActions() {
        return new PlainJavaActions(matomoBaseUri);
    }
}

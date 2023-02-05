package com.techeule.cms.hugo.pages;

import com.techeule.cms.hugo.pages.boundary.HugoMatomoPageViewAggregator;

public class Application {

    public static void main(final String[] args) {
        new HugoMatomoPageViewAggregator(args).generate();
    }
}

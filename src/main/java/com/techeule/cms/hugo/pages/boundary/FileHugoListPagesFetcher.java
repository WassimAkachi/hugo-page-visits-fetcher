package com.techeule.cms.hugo.pages.boundary;

import java.util.List;

import com.techeule.cms.hugo.pages.control.OpenCsvHugoListParser;
import com.techeule.cms.hugo.pages.entity.LineItem;

public class FileHugoListPagesFetcher {
    private final OpenCsvHugoListParser openCsvHugoListParser;

    public FileHugoListPagesFetcher(final OpenCsvHugoListParser openCsvHugoListParser) {
        this.openCsvHugoListParser = openCsvHugoListParser;
    }

    public List<LineItem> readAll(final String path) {
        return openCsvHugoListParser.parse(path);
    }
}

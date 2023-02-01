package com.techeule.cms.hugo.pages.control;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import com.opencsv.bean.CsvToBeanBuilder;
import com.techeule.cms.hugo.pages.entity.LineItem;

public class OpenCsvHugoListParser {

    public List<LineItem> parse(final String path) {
        try {
            return new CsvToBeanBuilder<LineItem>(new FileReader(path)).withType(LineItem.class).build().parse();
        } catch (final FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}

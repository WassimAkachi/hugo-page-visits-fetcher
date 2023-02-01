package com.techeule.cms.hugo.pages.entity;

import java.time.ZonedDateTime;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;

import lombok.Data;

@Data
public class LineItem {
    @CsvBindByName(column = "path", required = true)
    private String path;
    @CsvBindByName(column = "slug")
    private String slug;
    @CsvBindByName(column = "title")
    private String title;
    @CsvBindByName(column = "date")
    @CsvCustomBindByName(converter = ZoneDateTimeConverter.class)
    private ZonedDateTime date;
    @CsvBindByName(column = "expiryDate")
    @CsvCustomBindByName(converter = ZoneDateTimeConverter.class)
    private ZonedDateTime expiryDate;
    @CsvBindByName(column = "publishDate")
    @CsvCustomBindByName(converter = ZoneDateTimeConverter.class)
    private ZonedDateTime publishDate;
    @CsvBindByName(column = "draft")
    private boolean draft;
    @CsvBindByName(column = "permalink")
    private String permalink;
}

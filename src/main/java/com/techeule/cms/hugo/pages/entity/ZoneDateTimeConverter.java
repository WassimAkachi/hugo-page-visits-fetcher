package com.techeule.cms.hugo.pages.entity;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;

public class ZoneDateTimeConverter extends AbstractBeanField {
    @Override
    protected Object convert(final String value) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
        return ZonedDateTime.parse(value, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }
}

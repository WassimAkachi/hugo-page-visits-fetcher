package com.techeule.cms.hugo.pages.control;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.techeule.cms.hugo.pages.entity.LineItem;

class OpenCsvHugoListParserTest {
    private static final String INPUT_TEST_FILE = "hugo-list-all.csv";
    private final OpenCsvHugoListParser openCsvHugoListParser = new OpenCsvHugoListParser();
    private String path;

    @BeforeEach
    void setUp() {
        path = getClass().getClassLoader().getResource(INPUT_TEST_FILE).getPath();
    }

    @Test
    void parseAll() {
        final var lineItems = openCsvHugoListParser.parse(path);

        Assertions.assertThat(lineItems).hasSize(5);

        assertLineItem(lineItems.get(0),
                       "content/posts/Simple-Java-Avro-Serializer-Deserializer/index.md",
                       "Simple Java Avro Serializer Deserializer",
                       "2023-02-03T22:50:03+01:00",
                       "2023-02-03T22:50:03+01:00",
                       false,
                       "https://techeule.com/posts/Simple-Java-Avro-Serializer-Deserializer/");

        assertLineItem(lineItems.get(1),
                       "content/posts/multiple-git-configurations-conditional_includes/index.md",
                       "Multiple Git Configurations - conditional includes",
                       "2023-01-28T15:00:00+01:00",
                       "2023-01-28T15:00:00+01:00",
                       false,
                       "https://techeule.com/posts/multiple-git-configurations-conditional_includes/");

        assertLineItem(lineItems.get(2),
                       "content/about/index.md",
                       "About",
                       "2023-01-26T22:40:00+01:00",
                       "2023-01-26T22:40:00+01:00",
                       false,
                       "https://techeule.com/about/");

        assertLineItem(lineItems.get(3),
                       "content/posts/hello-world/index.md",
                       "Hello World",
                       "2023-01-26T22:40:00+01:00",
                       "2023-01-26T22:40:00+01:00",
                       false,
                       "https://techeule.com/posts/hello-world/");

        assertLineItem(lineItems.get(4),
                       "content/about-wassim/index.md",
                       "Wassim Akachi",
                       "2023-01-26T22:40:00+01:00",
                       "2023-01-26T22:40:00+01:00",
                       false,
                       "https://techeule.com/about-wassim/");
    }

    private static void assertLineItem(final LineItem lineItem,
                                       final String path,
                                       final String title,
                                       final String date,
                                       final String publishedDate,
                                       final boolean isDraft,
                                       final String permaLink) {
        Assertions.assertThat(lineItem.getPath()).isEqualTo(path);
        Assertions.assertThat(lineItem.getSlug()).isNullOrEmpty();
        Assertions.assertThat(lineItem.getTitle()).isEqualTo(title);
        Assertions.assertThat(lineItem.getDate())
                  .isEqualTo(ZonedDateTime.parse(date, DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        Assertions.assertThat(lineItem.getPublishDate())
                  .isEqualTo(ZonedDateTime.parse(publishedDate, DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        Assertions.assertThat(lineItem.isDraft()).isEqualTo(isDraft);
        Assertions.assertThat(lineItem.getPermalink()).isEqualTo(permaLink);
    }
}
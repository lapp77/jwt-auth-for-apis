package com.manning.simplysend.mappers;

import java.util.Optional;

import org.springframework.web.util.HtmlUtils;

public abstract class BaseMapper {

    protected static String htmlEscape(String value) {
        return Optional.ofNullable(value).map(HtmlUtils::htmlEscape).orElse("");
    }
}

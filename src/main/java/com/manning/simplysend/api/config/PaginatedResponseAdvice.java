package com.manning.simplysend.api.config;

import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;

@RestControllerAdvice
public class PaginatedResponseAdvice implements ResponseBodyAdvice<Page> {
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return PageImpl.class == returnType.getParameterType();
    }

    @Override
    public Page beforeBodyWrite(Page page,
                                MethodParameter returnType,
                                MediaType selectedContentType,
                                Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                ServerHttpRequest request,
                                ServerHttpResponse response) {
        var headers = response.getHeaders();
        headers.set("Access-Control-Expose-Headers", "Link,Page-Number,Page-Size,Total-Elements,Total-Pages");
        var links = links(page, request);
        if (!links.isBlank()) {
            headers.set("Link", links);
        }

        var pageNumber = page.getNumber();
        headers.set("Page-Number", Integer.toString(pageNumber));
        headers.set("Page-Size", Integer.toString(page.getSize()));
        headers.set("Total-Elements", Long.toString(page.getTotalElements()));
        headers.set("Total-Pages", Integer.toString(page.getTotalPages()));

        return page;
    }

    private String links(Page page, ServerHttpRequest request) {
        var links = new ArrayList<String>();
        var builder = UriComponentsBuilder.fromUri(request.getURI());
        if (request.getURI().getHost() == "localhost") {
            builder.port(request.getURI().getPort());
        }

        if (!page.isFirst()) {
            var link = replacePageAndSize(builder, page.getPageable().first());
            links.add("<" + link.toUriString() + ">; rel=\"first\"");
        }

        if (page.hasPrevious()) {
            var link = replacePageAndSize(builder, page.previousPageable());
            links.add("<" + link.toUriString() + ">; rel=\"prev\"");
        }

        if (page.hasNext()) {
            var link = replacePageAndSize(builder, page.nextPageable());
            links.add("<" + link.toUriString() + ">; rel=\"next\"");
        }

        if (!page.isLast()) {
            var last = builder.cloneBuilder();
            last.replaceQueryParam("page", page.getTotalPages());
            last.replaceQueryParam("limit", page.getSize());
            links.add("<$" + last.toUriString() + "}>; rel=\"last\"");
        }

        return String.join(",", links);
    }

    private UriComponentsBuilder replacePageAndSize(UriComponentsBuilder builder, Pageable page) {
        var clone = builder.cloneBuilder();
        clone.replaceQueryParam("page", page.getPageNumber());
        clone.replaceQueryParam("limit", page.getPageSize());
        return clone;
    }
}

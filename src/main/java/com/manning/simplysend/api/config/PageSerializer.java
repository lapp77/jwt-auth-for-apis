package com.manning.simplysend.api.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.data.domain.PageImpl;

import java.io.IOException;

@JsonComponent
public class PageSerializer extends JsonSerializer<PageImpl<?>> {
    @Override
    public void serialize(PageImpl<?> value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeObject(value.getContent());
    }
}

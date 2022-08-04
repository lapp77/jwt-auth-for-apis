package com.manning.simplysend.api.config

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import org.springframework.boot.jackson.JsonComponent
import org.springframework.data.domain.PageImpl
import java.io.IOException

@JsonComponent
class PageSerializer : JsonSerializer<PageImpl<*>>() {

    @Throws(IOException::class)
    override fun serialize(value: PageImpl<*>, gen: JsonGenerator, serializers: SerializerProvider) {
        gen.writeObject(value.content)
    }
}
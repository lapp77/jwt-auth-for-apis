package com.manning.simplysend.mappers

import org.springframework.web.util.HtmlUtils
import java.util.*

abstract class BaseMapper {

    companion object {
        @JvmStatic
        protected fun htmlEscape(value: String?): String {
            return Optional.ofNullable(value).map(HtmlUtils::htmlEscape).orElse("")
        }
    }
}
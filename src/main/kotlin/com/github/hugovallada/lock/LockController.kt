package com.github.hugovallada.lock

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.*
import javax.inject.Inject

@Controller("/api/v1/card/{id}/lock")
class LockController(@Inject private val service: LockService) {

    @Post
    fun lock(
        @PathVariable id: String,
        @Header(value = "user-Agent", defaultValue = "unknow") userAgent: String,
        @Header(value = "remoteAddr", defaultValue = "unknow") host: String
    ): HttpResponse<LockCardResponse> {
        return HttpResponse.created(service.lock(id, userAgent, host))
    }

}
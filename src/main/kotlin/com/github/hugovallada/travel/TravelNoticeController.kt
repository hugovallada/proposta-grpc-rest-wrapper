package com.github.hugovallada.travel

import com.github.hugovallada.TravelNoticeGrpcRequest
import com.github.hugovallada.shared.extension.generate
import io.micronaut.http.HttpResponse
import io.micronaut.http.MutableHttpResponse
import io.micronaut.http.annotation.*
import javax.inject.Inject

@Controller("/api/v1/card/{id}/travels/new")
class TravelNoticeController(@Inject private val service: TravelNoticeService) {

    @Post
    fun notificate(
        @PathVariable id: String,
        @Header(value = "user-agent", defaultValue = "unknown") userAgent: String,
        @Header(value = "remoteAddr", defaultValue = "unknown") host: String,
        @Body request: TravelNoticeRequest
    ): HttpResponse<TravelNoticeResponse> {
        service.notificate(TravelNoticeGrpcRequest.getDefaultInstance().generate(id, userAgent, host, request)).run {
            return HttpResponse.created(this)
        }
    }
}
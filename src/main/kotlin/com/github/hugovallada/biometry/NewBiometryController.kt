package com.github.hugovallada.biometry

import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.MutableHttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import javax.inject.Inject

@Controller("/api/v1/card/{id}/biometry/new")
class NewBiometryController(@Inject private val service: NewBiometryService) {

    @Post
    fun newBiometry(@PathVariable id: String, @Body fingerPrint: String): MutableHttpResponse<Any>? {
        service.new(id, fingerPrint).run {
            return HttpResponse.status(HttpStatus.CREATED)
        }
    }

}
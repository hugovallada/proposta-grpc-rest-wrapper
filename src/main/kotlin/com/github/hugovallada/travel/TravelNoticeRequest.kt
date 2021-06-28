package com.github.hugovallada.travel

import io.micronaut.core.annotation.Introspected
import java.time.LocalDate
import javax.validation.constraints.Future
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Introspected
data class TravelNoticeRequest(
    @field:NotBlank
    val destination: String,
    @field:Future @field:NotNull
    val returnDate: LocalDate
)

data class TravelNoticeResponse(
    val message: String
)

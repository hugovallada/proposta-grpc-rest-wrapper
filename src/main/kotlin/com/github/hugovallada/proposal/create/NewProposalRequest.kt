package com.github.hugovallada.proposal.create

import com.github.hugovallada.shared.validator.Document
import io.micronaut.core.annotation.Introspected
import java.math.BigDecimal
import javax.validation.Valid
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive

@Introspected
data class NewProposalRequest(
    @field:NotBlank(message = "The document must not be blank")
    @field:Document(message = "Document inválid")
    val document: String,
    @field:NotBlank(message = "The email must not be blank")
    @field:Email(message = "Invalid email")
    val email: String,
    @field:NotNull(message = "The address can't be null")
    @Valid
    val address: AddressRequest,
    @field:NotBlank(message = "The name must not be blank")
    val name: String,
    @field:NotNull(message = "The salary must not be null")
    @field:Positive(message = "The salary should be a positive value")
    val salary: BigDecimal
)

@Introspected
data class AddressRequest(
    @field:NotBlank(message = "City must not be blank")
    val city: String,
    @field:NotBlank(message = "State must not be blank")
    val state: String,
    @field:NotBlank(message = "Number must not be blank")
    val number: String,
    @field:NotBlank(message = "Cep must not be blank")
    val cep: String,
    val extension: String?
)

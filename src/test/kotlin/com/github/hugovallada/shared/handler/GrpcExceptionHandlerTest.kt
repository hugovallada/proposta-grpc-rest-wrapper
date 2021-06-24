package com.github.hugovallada.shared.handler

import io.grpc.Status
import io.grpc.StatusRuntimeException
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.hateoas.JsonError
import org.junit.jupiter.api.Test

internal class GrpcExceptionHandlerTest{

    val genericRequest = HttpRequest.GET<Any>("/")


    @Test
    internal fun `should return status 404 when grpc status is not found`() {
        val notFoundException = StatusRuntimeException(Status.NOT_FOUND.withDescription("Not found"))

        GrpcExceptionHandler().handle(genericRequest, notFoundException).run {
            status.shouldBe(HttpStatus.NOT_FOUND)
            body.shouldNotBeNull()
            (body() as JsonError).message.shouldBe("Not found")
        }
    }

    @Test
    internal fun `should return status 400 when invalid arguments are send`(){
        val invalidArgumentException = StatusRuntimeException(Status.INVALID_ARGUMENT.withDescription("Invalid Arguments"))
        GrpcExceptionHandler().handle(genericRequest, invalidArgumentException).run {
            status.shouldBe(HttpStatus.BAD_REQUEST)
            body.shouldNotBeNull()
            (body() as JsonError).message.shouldBe("Invalid Arguments")
        }
    }


    @Test
    internal fun `should return status 400 when grpc returns a failed precondition`() {
        val failedPreCondition = StatusRuntimeException(Status.FAILED_PRECONDITION.withDescription("Failed Precondition"))

        GrpcExceptionHandler().handle(genericRequest, failedPreCondition).run {
            status.shouldBe(HttpStatus.BAD_REQUEST)
            body.shouldNotBeNull()
            (body() as JsonError).message.shouldBe("Failed Precondition")
        }
    }

    @Test
    internal fun `should return status 422 when grpc returns already exists`() {
        val unprocessableEntity = StatusRuntimeException(Status.ALREADY_EXISTS.withDescription("Already Exists"))

        GrpcExceptionHandler().handle(genericRequest, unprocessableEntity).run {
            status.shouldBe(HttpStatus.UNPROCESSABLE_ENTITY)
            body.shouldNotBeNull()
            (body() as JsonError).message.shouldBe("Already Exists")
        }
    }

    @Test
    internal fun `should return status 500 when grpc returns an unknow error`() {
        val unknownError = StatusRuntimeException(Status.UNKNOWN.withDescription("Unknow"))

        GrpcExceptionHandler().handle(genericRequest, unknownError).run {
            status.shouldBe(HttpStatus.INTERNAL_SERVER_ERROR)
            body.shouldNotBeNull()
            (body() as JsonError).message.shouldBe("Unknow")
        }
    }
}
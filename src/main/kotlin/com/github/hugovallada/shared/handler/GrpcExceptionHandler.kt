package com.github.hugovallada.shared.handler

import io.grpc.Status
import io.grpc.StatusRuntimeException
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.MutableHttpResponse
import io.micronaut.http.hateoas.JsonError
import io.micronaut.http.server.exceptions.ExceptionHandler
import io.reactivex.Single
import javax.inject.Singleton

@Singleton
class GrpcExceptionHandler : ExceptionHandler<StatusRuntimeException, HttpResponse<Any>> {

    override fun handle(request: HttpRequest<*>, exception: StatusRuntimeException): HttpResponse<Any>? {
        val status = exception.status.code
        val description = exception.status.description

        val (httpStatus, message) = when (status) {
            Status.ALREADY_EXISTS.code -> Pair(HttpStatus.UNPROCESSABLE_ENTITY, description ?: "Já existe uma chave com esse valor")
            Status.INVALID_ARGUMENT.code -> Pair(HttpStatus.BAD_REQUEST, description ?:"Dados inválidos")
            Status.FAILED_PRECONDITION.code -> Pair(HttpStatus.BAD_REQUEST, description ?:"Erro de pré condição")
            Status.PERMISSION_DENIED.code -> Pair(HttpStatus.FORBIDDEN, description ?: "Você não tem permissão para acessar")
            Status.NOT_FOUND.code -> Pair(HttpStatus.NOT_FOUND, description ?: "Dado não encontrado")
            else -> Pair(HttpStatus.INTERNAL_SERVER_ERROR, description ?: "Erro desconhecido")
        }

        return HttpResponse.status<JsonError>(httpStatus).body(JsonError(message))
    }

}
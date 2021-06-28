package com.github.hugovallada.lock

import com.github.hugovallada.CardLockGrpc
import com.github.hugovallada.LockGrpcRequest
import com.github.hugovallada.LockGrpcResponse
import com.github.hugovallada.shared.util.GrpcFactory
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Replaces
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import javax.inject.Inject
import javax.inject.Singleton

@MicronautTest
internal class LockControllerTest(private val grpcClient: CardLockGrpc.CardLockBlockingStub){

    @Inject
    @field:Client("/")
    lateinit var client: HttpClient

    @Test
    internal fun `should return a message when the communication with the external system works`() {
        val id = "5231-1960-9688-9694"
        val request = HttpRequest.POST("api/v1/card/$id/lock",Any::class.java)

        Mockito.`when`(grpcClient.lock(LockGrpcRequest.newBuilder().setCardNumber(id).setUserAgent("unknow").setClientIp("unknow").build()))
            .thenReturn(LockGrpcResponse.newBuilder().setMessage("Credit card $id has been locked").build())

        val response = client.toBlocking().exchange(request, LockCardResponse::class.java)

        with(response){
            status.shouldBe(HttpStatus.CREATED)
            body.shouldNotBeNull()
            body()!!.message.shouldBe("Credit card $id has been locked")
        }
    }

    @Factory
    @Replaces(factory = GrpcFactory::class)
    internal class LockGrpcFactory{
        @Singleton
        fun mockStub() = Mockito.mock(CardLockGrpc.CardLockBlockingStub::class.java)
    }
}
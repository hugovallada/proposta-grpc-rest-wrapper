package com.github.hugovallada.travel

import com.github.hugovallada.CardLockGrpc
import com.github.hugovallada.TravelNoticeGrpc
import com.github.hugovallada.TravelNoticeGrpcRequest
import com.github.hugovallada.TravelNoticeGrpcResponse
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
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

@MicronautTest
internal class TravelNoticeControllerTest{

    @Inject
    lateinit var grpcClient: TravelNoticeGrpc.TravelNoticeBlockingStub

    @Inject
    @field:Client("/")
    lateinit var client: HttpClient

    @Test
    internal fun `should return message when communication occurs`() {
        val request = TravelNoticeGrpcRequest.newBuilder().setCardNumber("9999-9999-9999-9999")
            .setClientIp("unknown").setDestination("Sao Paulo").setReturnDate("2021-09-09")
            .setUserAgent("unknown").build()

        every {
            grpcClient.notificate(request)
        } returns TravelNoticeGrpcResponse.newBuilder().setMessage("Success").build()

        val httpRequest = HttpRequest.POST("/api/v1/card/9999-9999-9999-9999/travels/new", TravelNoticeRequest("Sao Paulo", LocalDate.parse("2021-09-09")))
        val response = client.toBlocking().exchange(httpRequest,TravelNoticeResponse::class.java)

        with(response){
            status.shouldBe(HttpStatus.CREATED)
            body.shouldNotBeNull()
            body()!!.message.shouldBe("Success")
        }
    }

    @Factory
    @Replaces(factory = GrpcFactory::class)
    internal class TravelNoticeGrpcFactory{
        @Singleton
        fun mockStub(): TravelNoticeGrpc.TravelNoticeBlockingStub = mockk()
    }

}
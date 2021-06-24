package com.github.hugovallada.proposal.create

import com.github.hugovallada.CreateProposalGrpc
import com.github.hugovallada.NewProposalGrpcResponse
import com.github.hugovallada.shared.extension.toGrpc
import com.github.hugovallada.shared.util.GrpcFactory
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContainIgnoringCase
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Replaces
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.MutableHttpResponse
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito
import java.math.BigDecimal
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@MicronautTest
internal class NewProposalControllerTest(private val grpcClient: CreateProposalGrpc.CreateProposalBlockingStub){


    @Inject
    @field:Client("/api/v1/proposals/new")
    lateinit var client: HttpClient

    @Test
    internal fun `should return status created with an uri with the proposal id`() {
        val addressRequest = AddressRequest(city = "Jacarépaguá", state = "Rio de Janeiro", number = "999", cep = "182882", extension = "")
        val request = NewProposalRequest(document = "88030149000", email = "email@email.com", address = addressRequest, name = "Hugo", salary = BigDecimal(2500))

        val idProposal = UUID.randomUUID().toString()
        Mockito.`when`(grpcClient.create(request.toGrpc())).thenReturn(NewProposalGrpcResponse.newBuilder().setIdProposal(idProposal).build())


        val response = client.toBlocking().exchange(HttpRequest.POST("/",request), HttpResponse::class.java)

        response.status.shouldBe(HttpStatus.CREATED)
        response.headers.get("location").shouldContainIgnoringCase("/api/v1/proposals/$idProposal/details")
    }

    @Test
    internal fun `should return status bad request when the data is invalid`() {
        val addressRequest = AddressRequest(city = "Jacarépaguá", state = "Rio de Janeiro", number = "999", cep = "182882", extension = "")
        val request = NewProposalRequest(document = "88030", email = "email@email.com", address = addressRequest, name = "Hugo", salary = BigDecimal(2500))

        val response = assertThrows<HttpClientResponseException>{
            client.toBlocking().exchange(HttpRequest.POST("/", request), HttpResponse::class.java)
        }

        with(response){
            status.shouldBe(HttpStatus.BAD_REQUEST)
            message.shouldContainIgnoringCase("document inválid")
        }
    }

    @Factory
    @Replaces(factory = GrpcFactory::class)
    internal class MockitoStubFactory{

        @Singleton
        fun stubMock() = Mockito.mock(CreateProposalGrpc.CreateProposalBlockingStub::class.java)
    }
}
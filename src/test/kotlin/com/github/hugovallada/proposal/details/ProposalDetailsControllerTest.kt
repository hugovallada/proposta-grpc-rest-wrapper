package com.github.hugovallada.proposal.details

import com.github.hugovallada.*
import com.github.hugovallada.ProposalStatusGrpc.ProposalStatusBlockingStub
import com.github.hugovallada.shared.util.GrpcFactory
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Replaces
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@MicronautTest
internal class ProposalDetailsControllerTest(@Inject private val grpcClient: ProposalStatusBlockingStub){

    @Inject
    @field:Client("/api/v1/proposals")
    lateinit var client: HttpClient

    @Test
    internal fun `should return the details when a valid id is send`() {
        val id = UUID.randomUUID().toString()
        Mockito.`when`(grpcClient.watch(ProposalStatusGrpcRequest.newBuilder().setId(id).build()))
            .thenReturn(ProposalStatusGrpcResponse.newBuilder().setCreditCard("5231-1960-9688-9694")
                .setStatus(StatusProposal.ELIGIBLE).setDocumet("12320607292").setName("Hugo").setEmail("email@email.com").build())

        val request = HttpRequest.GET<Any>("$id/details")

        val response = client.toBlocking().exchange(request, ProposalDetailsResponse::class.java)

        with(response){
            status.shouldBe(HttpStatus.OK)
            body.shouldNotBeNull()
            body().run {
                document.shouldBe("12320607292")
                name.shouldBe("Hugo")
                email.shouldBe("email@email.com")
                status.shouldBe(StatusProposalResponse.ELIGIBLE)
                creditCard.shouldBe("5231-1960-9688-9694")
            }
        }
    }

    @Factory
    @Replaces(factory = GrpcFactory::class)
    internal class MockitoStubFactory{

        @Singleton
        fun stubMock() = Mockito.mock(ProposalStatusBlockingStub::class.java)
    }
}
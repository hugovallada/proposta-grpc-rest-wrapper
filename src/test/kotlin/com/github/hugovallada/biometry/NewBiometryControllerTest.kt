package com.github.hugovallada.biometry

import com.github.hugovallada.BiometryAssignGrpc
import com.github.hugovallada.BiometryGrpcRequest
import com.github.hugovallada.BiometryGrpcResponse
import com.github.hugovallada.shared.util.GrpcFactory
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
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@MicronautTest
internal class NewBiometryControllerTest(@Inject private val grpcClient: BiometryAssignGrpc.BiometryAssignBlockingStub){

    @Inject
    @field:Client("/")
    lateinit var client: HttpClient

    @Test
    internal fun `should return status created when new biometry is assigned`() {
        val fingerPrint = UUID.randomUUID().toString()
        Mockito.`when`(grpcClient.assign(BiometryGrpcRequest.newBuilder().setCardNumber("5231-1960-9688-9694").setFingerPrint(
            fingerPrint).build())).thenReturn(BiometryGrpcResponse.newBuilder().setId(UUID.randomUUID().toString()).build())

        val request = HttpRequest.POST("api/v1/card/5231-1960-9688-9694/biometry/new",fingerPrint)
        val response = client.toBlocking().exchange(request,Any::class.java)

        with(response){
            status.shouldBe(HttpStatus.CREATED)
        }
    }


    @Factory
    @Replaces(factory = GrpcFactory::class)
    internal class BiometryGrpcFactory{

        @Singleton
        fun mockStub() = Mockito.mock(BiometryAssignGrpc.BiometryAssignBlockingStub::class.java)
    }
}
package com.github.hugovallada.shared.util

import com.github.hugovallada.CreateProposalGrpc
import com.github.hugovallada.CreateProposalGrpc.CreateProposalBlockingStub
import com.github.hugovallada.CreateProposalGrpc.newBlockingStub
import io.grpc.ManagedChannel
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcChannel
import javax.inject.Singleton

@Factory
class GrpcFactory(@GrpcChannel("proposal") private val channel: ManagedChannel) {

    @Singleton
    fun generateNewProposalClient(): CreateProposalBlockingStub? = newBlockingStub(channel)

}
package com.github.hugovallada.shared.util

import com.github.hugovallada.BiometryAssignGrpc
import com.github.hugovallada.CardLockGrpc
import com.github.hugovallada.CreateProposalGrpc
import com.github.hugovallada.CreateProposalGrpc.CreateProposalBlockingStub
import com.github.hugovallada.CreateProposalGrpc.newBlockingStub
import com.github.hugovallada.ProposalStatusGrpc
import com.github.hugovallada.ProposalStatusGrpc.ProposalStatusBlockingStub
import io.grpc.ManagedChannel
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcChannel
import javax.inject.Singleton

@Factory
class GrpcFactory(@GrpcChannel("proposal") private val channel: ManagedChannel) {

    @Singleton
    fun generateNewProposalClient(): CreateProposalBlockingStub? = newBlockingStub(channel)

    @Singleton
    fun generateNewProposalDetail(): ProposalStatusBlockingStub? = ProposalStatusGrpc.newBlockingStub(channel)

    @Singleton
    fun generateNewBiometry() : BiometryAssignGrpc.BiometryAssignBlockingStub = BiometryAssignGrpc.newBlockingStub(channel)

    @Singleton
    fun generateLockCard() : CardLockGrpc.CardLockBlockingStub = CardLockGrpc.newBlockingStub(channel)
}
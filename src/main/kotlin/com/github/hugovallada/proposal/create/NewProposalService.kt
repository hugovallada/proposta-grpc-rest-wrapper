package com.github.hugovallada.proposal.create

import com.github.hugovallada.CreateProposalGrpc.CreateProposalBlockingStub
import com.github.hugovallada.shared.extension.toGrpc
import io.micronaut.validation.Validated
import javax.inject.Inject
import javax.inject.Singleton
import javax.validation.Valid

@Singleton
@Validated
class NewProposalService(@Inject private val grpcClient: CreateProposalBlockingStub) {

    fun createProposal(@Valid proposalRequest: NewProposalRequest): NewProposalResponse {
        grpcClient.create(proposalRequest.toGrpc()).run {
            return NewProposalResponse(this)
        }
    }

}
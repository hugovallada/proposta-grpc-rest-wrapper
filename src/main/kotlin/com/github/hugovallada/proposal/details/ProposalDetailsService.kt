package com.github.hugovallada.proposal.details

import com.github.hugovallada.ProposalStatusGrpc
import com.github.hugovallada.ProposalStatusGrpc.ProposalStatusBlockingStub
import com.github.hugovallada.ProposalStatusGrpcRequest
import com.github.hugovallada.shared.extension.toRestResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProposalDetailsService(@Inject private val grpcClient: ProposalStatusBlockingStub) {

    fun details(id: String): ProposalDetailsResponse {
        grpcClient.watch(ProposalStatusGrpcRequest.newBuilder().setId(id).build()).run {
            return toRestResponse()
        }
    }

}
package com.github.hugovallada.proposal.create

import com.github.hugovallada.NewProposalGrpcResponse

data class NewProposalResponse(val id: String){
    constructor(grpcResponse: NewProposalGrpcResponse) : this(id = grpcResponse.idProposal)
}

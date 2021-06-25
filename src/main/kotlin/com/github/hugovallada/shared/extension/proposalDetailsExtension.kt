package com.github.hugovallada.shared.extension

import com.github.hugovallada.ProposalStatusGrpcRequest
import com.github.hugovallada.ProposalStatusGrpcResponse
import com.github.hugovallada.proposal.details.ProposalDetailsResponse
import com.github.hugovallada.proposal.details.StatusProposalResponse


fun ProposalStatusGrpcResponse.toRestResponse() = ProposalDetailsResponse(
    document = this.documet,
    creditCard = this.creditCard,
    name = this.name,
    email = this.email,
    status = StatusProposalResponse.of(this.status.name)
)
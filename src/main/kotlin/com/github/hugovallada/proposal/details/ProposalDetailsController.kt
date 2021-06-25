package com.github.hugovallada.proposal.details

import com.github.hugovallada.ProposalStatusGrpc
import com.github.hugovallada.ProposalStatusGrpc.ProposalStatusBlockingStub
import com.github.hugovallada.ProposalStatusGrpcRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import javax.inject.Inject

@Controller("/api/v1/proposals/{id}/details")
class ProposalDetailsController(@Inject private val service: ProposalDetailsService) {

    @Get
    fun getDetails(@PathVariable id: String) = HttpResponse.ok(service.details(id))

}
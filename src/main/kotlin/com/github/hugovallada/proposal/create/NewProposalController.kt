package com.github.hugovallada.proposal.create

import io.micronaut.http.HttpResponse
import io.micronaut.http.MutableHttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.http.uri.UriBuilder
import io.reactivex.Single
import javax.inject.Inject

@Controller("/api/v1/proposals/new")
class NewProposalController(@Inject private val proposalService: NewProposalService) {

    @Post
    fun newProposal(@Body proposalRequest: NewProposalRequest): Single<MutableHttpResponse<Any>> {
        proposalService.createProposal(proposalRequest).run {
            val uri = UriBuilder.of("/api/v1/proposals/{id}/details").expand(mutableMapOf(Pair("id", id)))
            return Single.just(HttpResponse.created<Any>(uri))
        }
    }


}
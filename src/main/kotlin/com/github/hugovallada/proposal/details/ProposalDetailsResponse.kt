package com.github.hugovallada.proposal.details

data class ProposalDetailsResponse(
    val document: String,
    val creditCard: String,
    val name: String,
    val email: String,
    val status: StatusProposalResponse
)

enum class StatusProposalResponse{
    UNKNOW,
    ELIGIBLE,
    NOT_ELIGIBLE;

    companion object{
        fun of(value: String): StatusProposalResponse {
            val values = listOf("ELIGIBLE","NOT_ELIGIBLE")

            values.contains(value).run {
                if(this) return StatusProposalResponse.valueOf(value)
            }
            return UNKNOW
        }
    }

}

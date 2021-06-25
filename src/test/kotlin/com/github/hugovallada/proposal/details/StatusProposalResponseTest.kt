package com.github.hugovallada.proposal.details

import com.github.hugovallada.StatusProposal
import io.kotest.matchers.collections.shouldBeOneOf
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import kotlin.reflect.KClass
import org.junit.jupiter.params.provider.*


internal class StatusProposalResponseTest{

    @Test
    internal fun `should return status unknow when the status in invalid`() {
        val response = StatusProposalResponse.of(StatusProposal.UNKNOW.name)
        response.shouldBe(StatusProposalResponse.UNKNOW)
    }


    @ParameterizedTest
    @ValueSource(strings = arrayOf("NOT_ELIGIBLE","ELIGIBLE"))
    internal fun `should return a valid status when there is a existing status`(string: String) {
        val response = StatusProposalResponse.of(StatusProposal.valueOf(string).name)
        response.shouldBeOneOf(StatusProposalResponse.ELIGIBLE, StatusProposalResponse.NOT_ELIGIBLE)
    }
}
package com.github.hugovallada.shared.extension

import com.github.hugovallada.AddressGrpc
import com.github.hugovallada.NewProposalGrpcRequest
import com.github.hugovallada.proposal.create.AddressRequest
import com.github.hugovallada.proposal.create.NewProposalRequest

fun AddressRequest.toGrpc() = AddressGrpc.newBuilder()
    .setCep(cep).setCity(city).setState(state).setNumber(number)
    .setExtension(extension ?: "").build()


fun NewProposalRequest.toGrpc() = NewProposalGrpcRequest.newBuilder()
    .setAddress(address.toGrpc()).setDocument(document).setEmail(email)
    .setName(name).setSalary(salary.toString()).build()
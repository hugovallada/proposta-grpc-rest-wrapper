package com.github.hugovallada.shared.extension

import com.github.hugovallada.TravelNoticeGrpcRequest
import com.github.hugovallada.travel.TravelNoticeRequest

fun TravelNoticeGrpcRequest.generate(id: String, userAgent: String, host: String, request: TravelNoticeRequest) =
    TravelNoticeGrpcRequest.newBuilder().setUserAgent(userAgent)
        .setReturnDate(request.returnDate.toString())
        .setDestination(request.destination)
        .setClientIp(host)
        .setCardNumber(id)
        .build()

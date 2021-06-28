package com.github.hugovallada.travel

import com.github.hugovallada.TravelNoticeGrpc
import com.github.hugovallada.TravelNoticeGrpcRequest
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TravelNoticeService(@Inject private val grpcClient: TravelNoticeGrpc.TravelNoticeBlockingStub) {

    fun notificate(travelNoticeRequest: TravelNoticeGrpcRequest): TravelNoticeResponse {
        grpcClient.notificate(travelNoticeRequest).run {
            return TravelNoticeResponse(message = this.message)
        }
    }

}
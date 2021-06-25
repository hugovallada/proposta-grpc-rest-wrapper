package com.github.hugovallada.lock

import com.github.hugovallada.CardLockGrpc
import com.github.hugovallada.LockGrpcRequest
import com.github.hugovallada.shared.extension.toRestResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LockService(@Inject private val grpcClient: CardLockGrpc.CardLockBlockingStub) {

    fun lock(id: String, userAgent: String, host: String): LockCardResponse {
        val locker = LockGrpcRequest.newBuilder().setCardNumber(id).setUserAgent(userAgent).setClientIp(host).build()

        grpcClient.lock(locker).run {
            return toRestResponse()
        }


    }
}

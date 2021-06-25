package com.github.hugovallada.biometry

import com.github.hugovallada.BiometryAssignGrpc
import com.github.hugovallada.BiometryGrpcRequest
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewBiometryService(@Inject private val grpcClient: BiometryAssignGrpc.BiometryAssignBlockingStub) {

    fun new(id: String, fingerPrint: String): String? {
        BiometryGrpcRequest.newBuilder().setCardNumber(id).setFingerPrint(fingerPrint).build().run {
            grpcClient.assign(this).let {
                return it.id
            }
        }
    }

}

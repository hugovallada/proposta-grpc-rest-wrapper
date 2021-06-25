package com.github.hugovallada.shared.extension

import com.github.hugovallada.LockGrpcResponse
import com.github.hugovallada.lock.LockCardResponse

fun LockGrpcResponse.toRestResponse() = LockCardResponse(
    message = this.message
)
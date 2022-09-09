package com.brian.server

import com.brian.grpcInterface.v1.user.UserServiceGrpcKt
import org.springframework.stereotype.Service

@Service
class GrpcUserService: UserServiceGrpcKt.UserServiceCoroutineImplBase() {
}
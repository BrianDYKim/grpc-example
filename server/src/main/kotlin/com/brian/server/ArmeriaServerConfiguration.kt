package com.brian.server

import org.springframework.context.annotation.Configuration

@Configuration
class ArmeriaServerConfiguration(
    private val grpcUserService: GrpcUserService
) {


}
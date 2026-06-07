package com.hanitacm.mymoviescmp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
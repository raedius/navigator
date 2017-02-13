package com.navigator.model

trait Node {

    val name: String
    val path: String
    def size: Int
    def list(prefix: String) = println(s"$prefix$name")
}

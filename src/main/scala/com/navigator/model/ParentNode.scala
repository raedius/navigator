package com.navigator.model

import scala.collection.mutable

trait ParentNode extends Node {
    val children = mutable.Map.empty[String, ChildNode]
    override def size = children.map({case (k, v) => v.size}).sum
    override def list(prefix: String) = {
        println(s"$prefix$name")
        children.map({case (k, v) => println(s"\t${v.list(prefix+"*")}")})
    }
}

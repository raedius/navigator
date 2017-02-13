package com.navigator.model

import scala.collection.mutable

case class Zip(name: String, path: String, parent: ParentNode, override val children: mutable.Map[String, ChildNode])
        extends ChildNode with ParentNode {

    override def size = children.map({case (k, v) => v.size/2}).sum
}

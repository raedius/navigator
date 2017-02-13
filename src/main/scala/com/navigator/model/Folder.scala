package com.navigator.model

import scala.collection.mutable

case class Folder(name: String, path: String, parent: ParentNode, override val children: mutable.Map[String, ChildNode])
        extends ChildNode with ParentNode {
}

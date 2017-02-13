package com.navigator.model

case class Text(name: String, path: String, parent: ParentNode, content: String) extends ChildNode {
    override def size: Int = content.length
}

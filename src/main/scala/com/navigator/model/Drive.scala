package com.navigator.model

import scala.collection.mutable

case class Drive(name: String, path: String, override val children: mutable.Map[String, ChildNode]) extends Node with ParentNode {

}

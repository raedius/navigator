package com.navigator

import com.navigator.model.{ChildNode, Folder, Node, ParentNode, Text, Zip}

import scala.collection.mutable

object Creator {

    def create(nodeType: String, name: String, path: String, nodes: mutable.Map[String, Node]) {
        nodeType.toLowerCase() match {
            case "folder" => createFolder(name, path)
            case "text" => createText(name, path, "")
            case "zip" => createZip(name, path)
            case _ => throw new UnsupportedOperationException(s"$nodeType is not a valid entity type")
        }
    }

    private def createFolder(name: String, path: String) {
        Navigator.locateNode(path) match {
            case parent: ParentNode =>
                parent.asInstanceOf[ParentNode].children += (name -> Folder(name, path, parent, mutable.Map.empty[String, ChildNode]))
            case _ => throw new UnsupportedOperationException(s"$path is a file")
        }
    }

    private def createText(name: String, path: String, content: String) {
        Navigator.locateNode(path) match {
            case parent: ParentNode =>
                parent.children += (name -> Text(name, path, parent, content))
            case _ => throw new UnsupportedOperationException("Text files must be created inside another entity")
        }
    }

    private def createZip(name: String, path: String) {
        Navigator.locateNode(path) match {
            case parent: ParentNode =>
                parent.children += (name -> Zip(name, path, parent, mutable.Map.empty[String, ChildNode]))
            case _ => throw new UnsupportedOperationException("Zip files must be created inside another entity")
        }
    }
}

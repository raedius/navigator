package com.navigator

import com.navigator.model.{ChildNode, Folder, ParentNode, Text, Zip}

object Relocator {

    def move(source: String, target: String) {
        val sourceNode = Navigator.locateNode(source)
        val targetNode = Navigator.locateNode(target)

        targetNode match {
            case parent: ParentNode =>
                sourceNode match {
                    case folder: Folder =>
                        folder.parent.children -= folder.name
                        folder.copy(parent = parent)
                    case zip: Zip =>
                        zip.parent.children -= zip.name
                        zip.copy(parent = parent)
                    case text: Text =>
                        text.parent.children -= text.name
                        text.copy(parent = parent)
                }
                parent.children += (sourceNode.name -> sourceNode.asInstanceOf[ChildNode])

            case _ =>
                throw new UnsupportedOperationException("Can only move to folder of zip")
        }
    }
}

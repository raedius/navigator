package com.navigator

import com.navigator.model.{ChildNode, Drive, Node, ParentNode}

import scala.collection.mutable

object Navigator {

    val drives = mutable.Map.empty[String, Node]
    val PathSeparator = "\\"

    def main(args: Array[String]) {

        var input = ""
        while (input != "exit") {
            input = io.StdIn.readLine()
            if (input.trim.toLowerCase == "exit") {
                println("Goodbye")
                System.exit(0)
            }
            if (input.trim.toLowerCase == "list") {
                handleList()
            } else {
                try {
                    val cleanInput = input.trim
                    input.substring(0, cleanInput.indexOf(" ")).toLowerCase match {
                        case "create" => handleCreate(cleanInput.split(" ").tail)
                        case "delete" => handleDelete(cleanInput.split(" ").tail)
                        case "move" => handleMove(cleanInput.split(" ").tail)
                        case "writetofile" => handleCreate(cleanInput.split(" ").tail)
                        case _ => println("I don't recognize that command")
                    }
                } catch {
                    case e: UnsupportedOperationException => println(s"Error: ${e.getMessage}")
                    case e: Exception => println(s"Nasty Error: ${e.getMessage}\n Stacktrace: ${e.getStackTrace}")
                }
            }
        }
    }

    def handleList() {
        drives.foreach({case (k, v) => v.list("*")})
    }

    def handleCreate(args: Seq[String]) {
        val nodeType = if (args.nonEmpty) args.head else throw
                new UnsupportedOperationException("Type is a required parameter for Create")

        val name = if (args.size > 1) args(1) else throw
                new UnsupportedOperationException("Name is a required parameter for Create")

        if (nodeType.toLowerCase() == "drive") {
            if (args.size < 3) {
                drives += (name -> Drive(name, "", mutable.Map.empty[String, ChildNode]))
            } else throw new UnsupportedOperationException("Drives cannot be contained in other entities")
        } else {
            val path = if (args.size > 2) args(2) else throw
                    new UnsupportedOperationException("Path is a required parameter for Create")
            Creator.create(nodeType, name, path, drives)
        }

        println(s"$name created successfully")
    }

    def handleDelete(args: Seq[String]) {
        throw new UnsupportedOperationException("Delete is not implemented")
    }

    def handleMove(args: Seq[String]) {
        val source = if (args.nonEmpty) args.head else throw
                new UnsupportedOperationException("Source path is a required parameter for Move")
        val target = if (args.size > 1) args(1) else throw
                new UnsupportedOperationException("Destination path is a required parameter for Move")

        Relocator.move(source, target)
        println(s"$source moved to $target")
    }

    def handleWriteToFile(args: Seq[String]) {
        val path = if (args.nonEmpty) args.head else throw
                new UnsupportedOperationException("File path is a required parameter for WriteToFile")
        val content = if (args.size > 1) args.head else throw
                new UnsupportedOperationException("Content is a required parameter for WriteToFile")

        FileWriter.write(path, content, drives)
        println(s"Write to $path succeeded")
    }

    def locateNode(path: String): Node = {
        if (!path.contains(PathSeparator))
            return drives.getOrElse(path, throw new UnsupportedOperationException(s"Path $path is not valid"))

        val pathSegments = path.split(PathSeparator+PathSeparator).toSeq
        locateNodeHelper(pathSegments.tail, drives.get(pathSegments.head))
    }

    def locateNodeHelper(pathSegments: Seq[String], current: Option[Node]): Node = {
        if (current.isEmpty) throw new UnsupportedOperationException("Invalid path")
        if (pathSegments.isEmpty) throw new UnsupportedOperationException("Invalid path")

        current.get match {
            case node: ParentNode =>
                val containerNode = node.asInstanceOf[ParentNode]
                if (pathSegments.size == 1) {
                    containerNode.children.getOrElse(pathSegments.head, throw new UnsupportedOperationException("Invalid path"))
                } else {
                    val next = containerNode.children.get(pathSegments.head)
                    locateNodeHelper(pathSegments.tail, next)
                }
            case _ => throw new UnsupportedOperationException("Invalid path")
        }
    }
}

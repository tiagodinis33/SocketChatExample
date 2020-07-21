package org.client

import java.io.OutputStream
import java.io.PrintWriter
import java.net.Socket
import java.util.*
import java.util.regex.Pattern

class Main {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            println("Coloque o ip do servidor para se conectar:")
            var ip = Scanner(System.`in`).nextLine()
            val splitted_ip = ip.split(":")
            val socket = Socket(splitted_ip[0], splitted_ip[1].toInt())
            Thread(
                {
                    while (!Thread.interrupted()) {
                        val scanner = Scanner(System.`in`)
                        val msg = scanner.nextLine()
                        if(socket.isConnected && !socket.isClosed)
                            socket.getOutputStream().printWriter().println(msg)
                    }

                }, "Write-Thread"
            ).start()
            Thread(
                {
                    while (!Thread.interrupted()){
                        val msg: String
                        try {
                            val scanner = Scanner(socket.getInputStream())
                            msg = scanner.nextLine()
                            if(msg != null)
                                println(msg)
                        }catch (e: Exception){
                            return@Thread
                        }

                    }
                }, "Read-Thread"
            ).start()
        }
    }
}

fun OutputStream.printWriter(): PrintWriter {
    return PrintWriter(this, true)
}

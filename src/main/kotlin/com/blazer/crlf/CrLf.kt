package com.blazer.crlf

import java.nio.charset.Charset

object CrLf {
    @JvmStatic
    fun main(args: Array<String>) {
        println("Incorrect:")
        /*
         * Will output:
         * ->0
         * ->1
         * ->2
         * ->3
         * ->4
         * ->5
         */
        CrLf::class.java.classLoader
            .getResourceAsStream("text.crlf")!!.use { ins ->
                ins.reader(Charset.defaultCharset())
                    .readText()
                    .split("\n")
                    .forEachIndexed { i, line ->
                        println("$line->$i")
                    }
            }
        println("Correct:")
        /*
         * Will output:
         * this->0
         * lines->1
         * should->2
         * be->3
         * printed->4
         */
        CrLf::class.java.classLoader
            .getResourceAsStream("text.crlf")!!.use { ins ->
                ins.reader(Charset.defaultCharset())
                    .readLines()
                    .forEachIndexed { i, line ->
                        println("$line->$i")
                    }
            }
    }
}
import javax.script.ScriptEngineManager

class MyClass {
    fun doAfterDelay(delay: Long, callback: Runnable) {
        println("Java: before thread ${Thread.currentThread().id}")
        Thread {
            println("Java: thread begin ${Thread.currentThread().id}")
            Thread.sleep(delay)
            callback.run()
            println("Java: thread end ${Thread.currentThread().id}")
        }.start()
        println("Java: after thread ${Thread.currentThread().id}")
    }
}

class MyAnotherClass {
    fun sum(a: Int, b: Int): Int {
        println("Java: function sum")
        return a + b
    }
}

fun main() {
    val engine = ScriptEngineManager().getEngineByName("nashorn")
    // Eval from string
    val resultString = engine.eval(
            """
        // JavaScript code inside java
        var a = 10;
        var b = 20;
        // Result of the last line will be return value of the script
        print("JS: " + (a + b));
        "JS"
    """.trimIndent()
    )
    println("Java: return from script " + resultString)

    engine.put("myJavaVariable", "Hello From Java")

    engine.put("myAsync", MyClass())
    // Eval from file in fs
    //val resultFile = engine.eval(FileReader("myscript.js"))
    // Eval from file in classpath/jar
    val resultFile = engine.eval(
            (object {})::class.java.getResourceAsStream("/myscript.js").bufferedReader()
    )
    println(resultFile)
}

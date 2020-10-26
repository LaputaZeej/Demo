package com.bugull.android.ext.sam

class KotlinDemoTest(private val item: Item = Item()) {
    fun delegateWork(f: JavaInterface): String {
        return f.doSomething(item)
    }

    fun delegateWork000(f: Runnable) {
    }

    fun delegateOtherWork(f: (Item) -> String): String {
        return f.invoke(item)
    }

    fun delegateAliasWork(f: KotlinFunctionAlias): String {
        return f.invoke(item)
    }

    fun delegateAliasWork2(f: KotlinFunctionAlias2): String {
        return f.invoke(item)
    }

    fun delegateAliasWork3(f: KotlinInterface): String {
        return f.dosomething(item)
    }

    fun doWork() {
        delegateWork(JavaInterface {
            "item = $it"
        })
        delegateWork(object : JavaInterface {
            override fun doSomething(item: Item?): String {
                return item.toString()
            }
        })
        // delegateWork({"item = $it"}) // error: Type mismatch: inferred type is () -> String but JavaInterface was expected

        delegateOtherWork {
            "item = $it"
        }

        delegateAliasWork {
            "item = $it"
        }
        // delegateAliasWork3(KotlinInterface{ }) // error:Interface KotlinInterface does not have constructors
        delegateAliasWork3(object : KotlinInterface {
            override fun dosomething(item: Item): String {
                return item.toString()
            }
        })


    }


    fun test01(f: JavaInterface) {
        f.doSomething(item)
    }

    fun test02(f: Runnable) {
        Thread(f).start()
    }

    fun test03(tag: Any, f: Runnable) {
        Thread(f).start()
    }

    fun test04(tag: Runnable, f: Runnable) {
        Thread(f).start()
    }

}

typealias KotlinFunctionAlias = (Item) -> String
typealias KotlinFunctionAlias1 = (Item) -> String
typealias KotlinFunctionAlias2 = (Item) -> String

fun testFun() {
    var f1: KotlinFunctionAlias1 = { it.toString() }
    var f2: KotlinFunctionAlias2 = { it.toString() }
    var f3: (Item) -> String = { it.toString() }
    f1 = f2
    f2 = f3
    f1 = f3
}

fun main1() {
    val kotlinDemoTest = KotlinDemoTest()
    kotlinDemoTest.test01(JavaInterface { "item = $it" })
    kotlinDemoTest.test02(Runnable { })
    kotlinDemoTest.test03("03", Runnable { })


    val runnable = Runnable { }
    val demoTest = JavaDemoTest()
    demoTest.test01 {}
    demoTest.test02("") {}
    demoTest.test03("") {}

    demoTest.test04({}) {}
    demoTest.test04(runnable, Runnable {})
    demoTest.test04(Runnable {}, Runnable {})
    demoTest.test04({}, {})
}


fun func1(tag: String, block: (Int) -> String) {
    println("tag$block()")
}

fun main() {
    func1("func1", {
        "result"
    })

    func1("func1"){
         "result"
    }
}
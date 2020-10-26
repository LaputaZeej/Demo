package com.bugull.android.ext.pattern_matching

// 模式匹配
//
// 常见模式：1.常量模式 2.类型模式 3.逻辑表达式模式 4.嵌套表达式模式
//
// 嵌套表达式模式

// 模式匹配的实现

// 类型测试/类型转换 TypeTest/TypeCast
// 面向对象的分解 Object-Oriented Decomposition
// 访问者设计模式 Visitor
// Typecase
// 样本类 Case Classes
// 抽取器 Extractor

sealed class Expr {
    abstract fun isZero(): Boolean
    abstract fun isAddZero(): Boolean
    abstract fun left(): Expr
    abstract fun right(): Expr

    abstract fun accept(v: Visitor)
}

class Visitor {
    fun visit(expr: Num): Boolean = false
    fun visit(expr: Operate): Boolean = when (expr) {
        Operate("+", Num(0), expr.right) -> true
        Operate("+", expr.left, Num(0)) -> true
        else -> false
    }
}

data class Num(val value: Int) : Expr() {
    override fun isZero(): Boolean = this.value == 0

    override fun isAddZero(): Boolean = false
    override fun left(): Expr {
        throw Throwable("no element")
    }

    override fun right(): Expr {
        throw Throwable("no element")
    }

    override fun accept(v: Visitor) {
        v.visit(this)
    }
}

data class Operate(val opName: String, val left: Expr, val right: Expr) : Expr() {
    override fun isZero(): Boolean = false

    override fun isAddZero(): Boolean =
        this.opName == "+" && (this.left.isZero() || this.right.isZero())

    override fun left(): Expr = this.left

    override fun right(): Expr = this.right
    override fun accept(v: Visitor) {
        v.visit(this)
    }
}

// 0+5 = 5
// 5+0 = 5
//kotlin
fun simplifyExpr(expr: Expr): Expr = when {
    (expr is Operate) && (expr.opName == "+") && (expr.left is Num) && (expr.left.value == 0) -> expr.right
    (expr is Operate) && (expr.opName == "+") && (expr.right is Num) && (expr.right.value == 0) -> expr.left
    else -> expr
}

// Scala 伪代码 模式匹配
//fun simplifyExprScala(expr: Expr): Expr = when (expr) {
//    Operate("+", Num(0), expr.right) -> expr.right
//    Operate("+", expr.left, Num(0)) -> expr.left
//    else -> expr
//}

// 需要一次类型判断
fun simplifyExprKotlin(expr: Expr): Expr = when (expr) {
    is Num -> expr
    is Operate -> {
        when (expr) {
            Operate("+", Num(0), expr.right) -> expr.right
            Operate("+", expr.left, Num(0)) -> expr.left
            else -> expr
        }
    }
}

// 0+(5+0)=5
// (0+5)+0=5
fun simplifyExprKotlin01(expr: Expr): Expr = when (expr) {
    is Num -> expr
    is Operate -> {
        when (expr) {
            // Operate("+", Num(0), Operate("+",Num(0),expr.right)) -> expr.right.right
            Operate("+", Num(0), expr.right) -> simplifyExprKotlin01(expr.right)
            Operate("+", expr.left, Num(0)) -> simplifyExprKotlin01(expr.left)
            else -> expr
        }
    }
}

// 添加 isZero isAddZero
//fun simplifyExprKotlin02(expr: Expr): Expr = when  {
//    expr.isAddZero() && expr.right.isAddZero() && expr.right.right.isZero()->expr.right.left
//    else->expr
//}

// 添加 left() right()
fun simplifyExprKotlin02(expr: Expr): Expr = when {
    expr.isAddZero() && expr.right().isAddZero() && expr.right().left().isZero() -> expr.right()
        .right()
    else -> expr
}

// visitor 模式


fun main() {
    val a1 = Operate("+", Num(0), Num(9))
    println("a1 = ${simplifyExpr(a1)}")

    val a2 = Operate("+", Num(0), Operate("+", Num(0), Num(11)))
    println("a2 = ${simplifyExprKotlin02(a2)}")
}
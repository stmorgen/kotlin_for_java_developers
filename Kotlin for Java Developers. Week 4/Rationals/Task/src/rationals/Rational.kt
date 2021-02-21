package rationals

import java.math.BigInteger

data class Rational(var num: BigInteger, var dom: BigInteger) : Comparable<Rational> {

    init{
        val gcd = num.gcd(dom)
        num = num / gcd
        dom = dom / gcd

        if(dom < BigInteger.ZERO ) {
            num = num.negate()
            dom = dom.negate()
        }
    }

    constructor(n: Long, d: Long) : this(BigInteger.valueOf(n), BigInteger.valueOf(d))
    constructor(n: Int, d: Int): this(BigInteger.valueOf(n.toLong()), BigInteger.valueOf(d.toLong()))

    override fun compareTo(other: Rational) : Int {
        return (this.num * other.dom).compareTo(other.num * this.dom)
    }

    override fun toString(): String {
        if(dom == BigInteger.ONE) {
            return num.toString()
        }
        return num.toString() + "/" + dom.toString()
    }
}

fun String.toRational() : Rational {
    val parts = this.split("/")
    if(parts.size == 1) {
        return Rational(parts.get(0).toBigInteger(), BigInteger.ONE)
    }
    if (parts.get(1).toBigInteger() == BigInteger.ZERO) {
        throw IllegalArgumentException("dominator was zero");
    }
    return Rational(parts.get(0).toBigInteger(),parts.get(1).toBigInteger())
}



infix fun Int.divBy(second: Int) : Rational {
    return Rational(this, second)
}

infix fun Long.divBy(second: Long) : Rational {
    return Rational(this, second)
}

infix fun BigInteger.divBy(second: BigInteger) : Rational {
    return Rational(this, second)
}

operator fun Rational.plus(other: Rational) : Rational {

    return Rational( (this.num * other.dom + other.num * this.dom), this.dom * other.dom)
}

operator fun Rational.minus(other: Rational) : Rational {
    return this + (-other)
}

operator fun Rational.times(other: Rational) : Rational {
    return Rational(this.num * other.num, this.dom * other.dom)
}

operator fun Rational.div(other: Rational) : Rational {
    return this * Rational(other.dom, other.num)
}

operator fun Rational.unaryMinus() : Rational {
    return Rational(this.num.negate(), this.dom)
}

fun main() {
    val half = 1 divBy 2
    val third = 1 divBy 3

    val sum: Rational = half + third
    println(5 divBy 6 == sum)

    val difference: Rational = half - third
    println(1 divBy 6 == difference)

    val product: Rational = half * third
    println(1 divBy 6 == product)

    val quotient: Rational = half / third
    println(3 divBy 2 == quotient)

    val negation: Rational = -half
    println(-1 divBy 2 == negation)

    println((2 divBy 1).toString() == "2")
    println((-2 divBy 4).toString() == "-1/2")
    println("117/1098".toRational().toString() == "13/122")

    val twoThirds = 2 divBy 3
    println(half < twoThirds)

    println(half in third..twoThirds)

    val x = 2000000000L divBy 4000000000L
    println(2000000000L divBy 4000000000L == 1 divBy 2)

    val y = "912016490186296920119201192141970416029".toBigInteger() divBy
            "1824032980372593840238402384283940832058".toBigInteger()
    println("912016490186296920119201192141970416029".toBigInteger() divBy
            "1824032980372593840238402384283940832058".toBigInteger() == 1 divBy 2)
}
package nicestring

fun String.isNice(): Boolean {

    var x = setOf("be", "ba", "bu").none {this.contains(it) };
    var y = this.toList().count { it in "aeuio"} >= 3;
    var z = this.toList().zipWithNext().any{ (f,s) -> f == s}
    return listOf(x ,y, z).count { it } >= 2;
}
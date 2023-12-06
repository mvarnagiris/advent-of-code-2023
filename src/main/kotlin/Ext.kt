
const val NewLine = "\r\n"

fun getResourceAsText(path: String) = object {}.javaClass.getResource(path)?.readText()!!
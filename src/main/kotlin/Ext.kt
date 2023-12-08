
const val NewLine = "\r\n"
val NumberRegex = "\\d+".toRegex()

fun getResourceAsText(path: String) = object {}.javaClass.getResource(path)?.readText()!!
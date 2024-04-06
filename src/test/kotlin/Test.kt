import org.junit.jupiter.api.Test


class Test {

    val plano = DirectoryElement("plano")
    val curso = DirectoryElement("curso", plano)
    val mei = LeafElement("MEI", curso)
    @Test
    fun main() {
        println(mei.parent)
        println(plano.children)
    }
}
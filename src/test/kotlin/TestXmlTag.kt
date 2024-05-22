import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class TestXmlTag {
    val plano = XmlTag("plano")
    val curso = XmlLeaf("curso", plano, leafText = "MEI")
    val fuc1 = XmlTag("fuc", plano)
    val avaliacaofuc1 = XmlTag("avaliacao", fuc1)
    val componente1fuc1 = XmlLeaf("componente", avaliacaofuc1)

    @Test
    fun testParentAndChildren() {
        Assertions.assertNull(plano.parent)
        assertEquals(plano, curso.parent)
        assertEquals(fuc1, avaliacaofuc1.parent)
        assertEquals(listOf(curso, fuc1), plano.children)
        assertEquals(listOf(avaliacaofuc1), fuc1.children)
    }

    @Test
    fun testRemoveChildElement() {
        fuc1.removeChildElement(avaliacaofuc1)
        assertEquals(listOf<XmlElement>(), fuc1.children)
        Assertions.assertNull(avaliacaofuc1.parent)
    }

    @Test
    fun testAddChildElement() {
        val fuc2 = XmlTag("fuc2")
        Assertions.assertNull(fuc2.parent)

        plano.addChildElement(fuc2)
        assertEquals(listOf(curso, fuc1, fuc2), plano.children)

        fuc2.addChildElement(avaliacaofuc1)
        assertEquals(listOf(avaliacaofuc1), fuc2.children)
        assertEquals(avaliacaofuc1.parent, fuc2)
        assertEquals(listOf<XmlElement>(), fuc1.children)
    }

}
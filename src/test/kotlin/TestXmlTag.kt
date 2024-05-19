import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class TestXmlTag {
    val plano = XmlTag("plano")
    val curso = XmlLeaf("curso", plano, leafText = "MEI")
    val fuc1 = XmlTag("fuc", plano)
    val avaliacaofuc1 = XmlTag("avaliacao", fuc1)
    val componente1fuc1 = XmlLeaf("componente", avaliacaofuc1)

    /**
     * Test adding attribute to element
     */
    @Test()
    fun testAddAttribute() {
        val planoTest = XmlTag("plano")

        // adding attribute to a xml tag
        var attribute1 = Attribute("codigo", "M4310")
        planoTest.addAttribute(attribute1)
        assert(planoTest.attributes.contains(attribute1))

        // adding two attributes to a xml tag
        val attribute2 = Attribute("peso", "20%")
        planoTest.addAttribute(attribute2)
        assert(planoTest.attributes.containsAll(listOf(attribute1, attribute2)))

        // adding an attribute to a xml tag that already exist and doesn't update the value
        val attribute3 = Attribute("peso", "40%")
        planoTest.addAttribute(attribute3)
        assert(planoTest.attributes.containsAll(listOf(attribute1, attribute2)))
    }

    /**
     * Test removing attribute from element
     */
    @Test
    fun testRemoveAttribute() {
        val componente1fuc1Test = XmlLeaf("componente", avaliacaofuc1)

        val planoTest = XmlTag("plano")

        // remove an attribute from a xml tag that doesn't exist and the object doesn't have any attributes
        var attribute1 = Attribute("nome", "")
        planoTest.removeAttribute(attribute1.getName())
        assert(planoTest.attributes.isEmpty())

        // removing an attribute from a xml tag that doesn't exist and the object has more attributes
        attribute1 = Attribute("nome", "Quizzes")
        val attribute2 = Attribute("peso", "")
        planoTest.addAttribute(attribute1)
        val planoTest2 = planoTest.copy()
        planoTest.removeAttribute(attribute2.getName())
        assertEquals(planoTest2.attributes, planoTest.attributes)

        // removing the only attribute that exists from a xml tag
        planoTest.removeAttribute(attribute1.getName())
        assert(componente1fuc1Test.attributes.isEmpty())
    }

    /**
     * Test update attribute from entity
     */
    @Test
    fun testUpdateAttribute() {
        val planoTest = XmlTag("plano")

        val attribute1 = Attribute("nome", "Quizzes")
        val attribute2 = Attribute("nome", "Quizz1")
        planoTest.updateAttribute(attribute1)
        //update attribute that doesn't exist in a xml tag
        assertEquals(plano.attributes, planoTest.attributes)

        //update attribute that exists in a xml tag
        planoTest.addAttribute(attribute1)
        planoTest.updateAttribute(attribute2)
        assert(planoTest.attributes.none { it.getName() == "nome" && it.getValue() == "Quizzes" } ?: true &&
                planoTest.attributes.any { it.getName() == "nome" && it.getValue() == "Quizz1" } ?: false)
    }

    /**
     * Test update attribute from entity with prints
     */
    @Test
    fun testUpdateAttributeWithPrint() {
        val fuc1Test = XmlTag("fuc", plano)

        val expected = "<fuc codigo=\"M1111\">"
        fuc1Test.addAttribute(Attribute("codigo", "M4310"))
        fuc1Test.updateAttribute(Attribute("codigo", "M1111"))
        assertEquals(expected, fuc1Test.print())
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

    @Test
    fun testListDistinctElementNames() {
        assertEquals(setOf("plano", "curso", "fuc", "avaliacao", "componente"), plano.listDistinctElementNames())
        assertEquals(setOf("curso"), curso.listDistinctElementNames())
    }

    @Test
    fun testParentAndChildren() {
        Assertions.assertNull(plano.parent)
        assertEquals(plano, curso.parent)
        assertEquals(fuc1, avaliacaofuc1.parent)
        assertEquals(listOf(curso, fuc1), plano.children)
        assertEquals(listOf(avaliacaofuc1), fuc1.children)
    }


    /**
     * Test prettyprint
     */
    @Test
    fun testPrettyPrint() {
        var expected = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
        expected += "<plano>"
        expected += "<curso>Mestrado em Engenharia Informática</curso>"
        expected += "<fuc codigo=\"M4310\">"
        expected += "<nome>Programação Avançada</nome>"
        expected += "<ects>6.0</ects>"
        expected += "<avaliacao>"
        expected += "<componente nome=\"Quizzes\" peso=\"20%\"/>"
        expected += "<componente nome=\"Projeto\" peso=\"80%\"/>"
        expected += "</avaliacao>"
        expected += "</fuc>"
        expected += "<fuc codigo=\"03782\">"
        expected += "<nome>Dissertação</nome>"
        expected += "<ects>42.0</ects>"
        expected += "<avaliacao>"
        expected += "<componente nome=\"Dissertação\" peso=\"60%\"/>"
        expected += "<componente nome=\"Apresentação\" peso=\"20%\"/>"
        expected += "<componente nome=\"Discussão\" peso=\"20%\"/>"
        expected += "</avaliacao>"
        expected += "</fuc>"
        expected += "</plano>"

        assertEquals(expected, createXML1("plano", 1).prettyPrint())

        expected = "<fuc codigo=\"M4310\">"
        expected += "<nome>Programação Avançada</nome>"
        expected += "<ects>6.0</ects>"
        expected += "<avaliacao>"
        expected += "<componente nome=\"Quizzes\" peso=\"20%\"/>"
        expected += "<componente nome=\"Projeto\" peso=\"80%\"/>"
        expected += "</avaliacao>"
        expected += "</fuc>"

        assertEquals(expected, createXML1("fuc", 1).prettyPrint())

    }
}

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class TestXmlElement {

    val plano = XmlTag("plano")
    val curso = XmlLeaf("curso", plano, leafText = "MEI")
    val fuc1 = XmlTag("fuc", plano)
    val avaliacaofuc1 = XmlTag("avaliacao", fuc1)
    val componente1fuc1 = XmlLeaf("componente", avaliacaofuc1)

    /**
     * Test adding attribute to element
    **/
    @Test
    fun testAddAttribute() {

        // adding attribute to a xml leaf
        var attribute1 = XmlAttribute("codigo", "M4310")
        curso.addAttribute(attribute1)
        assert(curso.attributes.contains(attribute1))

        // adding two attributes to a xml leaf
        attribute1 = XmlAttribute("nome", "Quizzes")
        componente1fuc1.addAttribute(attribute1)
        var attribute2 = XmlAttribute("peso", "20%")
        componente1fuc1.addAttribute(attribute2)
        assert(componente1fuc1.attributes.containsAll(listOf(attribute1, attribute2)))

        // adding an attribute to a xml leaf that already exist and doesn't update the value
        var attribute3 = XmlAttribute("peso", "40%")
        componente1fuc1.addAttribute(attribute3)
        assert(componente1fuc1.attributes.containsAll(listOf(attribute1, attribute2)))

        // adding attribute to a xml tag
        attribute1 = XmlAttribute("codigo", "M4310")
        plano.addAttribute(attribute1)
        assert(plano.attributes.contains(attribute1))

        // adding two attributes to a xml tag
        attribute2 = XmlAttribute("peso", "20%")
        plano.addAttribute(attribute2)
        assert(plano.attributes.containsAll(listOf(attribute1, attribute2)))

        // adding an attribute to a xml tag that already exist and doesn't update the value
        attribute3 = XmlAttribute("peso", "40%")
        plano.addAttribute(attribute3)
        assert(plano.attributes.containsAll(listOf(attribute1, attribute2)))
    }

    /**
     * Test removing attribute from element
     */
    @Test
    fun testRemoveAttribute() {

        // remove an attribute from a xml leaf that doesn't exist and the object doesn't have any attributes
        var attribute1 = XmlAttribute("nome", "")
        componente1fuc1.removeAttribute(attribute1.getName())
        assert(componente1fuc1.attributes.isEmpty())

        // removing an attribute from a xml leaf that doesn't exist and the object has more attributes
        attribute1 = XmlAttribute("nome", "Quizzes")
        componente1fuc1.addAttribute(attribute1)
        val componente1fuc1Test = componente1fuc1.copy()
        var attribute2 = XmlAttribute("peso", "")
        componente1fuc1.removeAttribute(attribute2.getName())
        assertEquals(componente1fuc1Test.attributes, componente1fuc1.attributes)

        // removing the only attribute that exists from a xml leaf
        componente1fuc1.removeAttribute(attribute1.getName())
        assert(componente1fuc1.attributes.isEmpty())

        // remove an attribute from a xml tag that doesn't exist and the object doesn't have any attributes
        attribute1 = XmlAttribute("nome", "")
        plano.removeAttribute(attribute1.getName())
        assert(plano.attributes.isEmpty())

        // removing an attribute from a xml tag that doesn't exist and the object has more attributes
        attribute1 = XmlAttribute("nome", "Quizzes")
        attribute2 = XmlAttribute("peso", "")
        plano.addAttribute(attribute1)
        val planoTest = plano.copy()
        plano.removeAttribute(attribute2.getName())
        assertEquals(planoTest.attributes, plano.attributes)

        // removing the only attribute that exists from a xml tag
        planoTest.removeAttribute(attribute1.getName())
        assert(componente1fuc1Test.attributes.isEmpty())
    }

    /**
     * Test removing attribute from element with strings
     */
    @Test
    fun testRemoveAttributeWithElementToString() {
        var expected = "<componente/>"
        componente1fuc1.removeAttribute("nome")
        assertEquals(expected, componente1fuc1.elementToString())

        componente1fuc1.addAttribute(XmlAttribute("nome", "Quizzes"))
        expected = "<componente nome=\"Quizzes\"/>"
        componente1fuc1.removeAttribute("peso")
        assertEquals(expected, componente1fuc1.elementToString())

        componente1fuc1.addAttribute(XmlAttribute("nome", "Quizzes"))
        expected = "<componente/>"
        componente1fuc1.removeAttribute("nome")
        assertEquals(expected, componente1fuc1.elementToString())

        componente1fuc1.addAttribute(XmlAttribute("nome", "Quizzes"))
        componente1fuc1.addAttribute(XmlAttribute("peso", "20%"))
        expected = "<componente peso=\"20%\"/>"
        componente1fuc1.removeAttribute("nome")
        assertEquals(expected, componente1fuc1.elementToString())

        expected = "<curso>MEI</curso>"
        curso.addAttribute(XmlAttribute("codigo", "M4310"))
        curso.removeAttribute("codigo")
        assertEquals(expected, curso.elementToString())
    }

    /**
     * Test update attribute from entity
     */
    @Test
    fun testUpdateAttribute() {
        val componente1fuc1Test = XmlLeaf("componente", avaliacaofuc1)

        var attribute1 = XmlAttribute("nome", "Quizzes")
        var attribute2 = XmlAttribute("nome", "Quizz1")
        componente1fuc1Test.updateAttribute(attribute1)

        //update attribute that doesn't exist in a xml leaf
        assertEquals(componente1fuc1.attributes, componente1fuc1Test.attributes)

        //update attribute that exists in a xml leaf
        componente1fuc1Test.addAttribute(attribute1)
        componente1fuc1Test.updateAttribute(attribute2)
        assert(componente1fuc1Test.attributes.none { it.getName() == "nome" && it.getValue() == "Quizzes" } ?: true &&
                componente1fuc1Test.attributes.any { it.getName() == "nome" && it.getValue() == "Quizz1" } ?: false)

        val planoTest = XmlTag("plano")

        attribute1 = XmlAttribute("nome", "Quizzes")
        attribute2 = XmlAttribute("nome", "Quizz1")
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
    fun testUpdateAttributeWithElementToString() {

        var expected = "<componente/>"
        componente1fuc1.updateAttribute(XmlAttribute("nome", "Quizzes"))
        assertEquals(expected, componente1fuc1.elementToString())
        componente1fuc1.addAttribute(XmlAttribute("nome", "Quizzes"))

        expected = "<componente nome=\"Quizz1\"/>"
        componente1fuc1.updateAttribute(XmlAttribute("nome", "Quizz1"))
        assertEquals(expected, componente1fuc1.elementToString())

        expected = "<componente nome=\"Quizz1\"/>"
        componente1fuc1.updateAttribute(XmlAttribute("peso", "20%"))
        assertEquals(expected, componente1fuc1.elementToString())

        componente1fuc1.addAttribute(XmlAttribute("peso", "20%"))
        expected = "<componente nome=\"Projeto\" peso=\"80%\"/>"
        componente1fuc1.updateAttribute(XmlAttribute("nome", "Projeto"))
        componente1fuc1.updateAttribute(XmlAttribute("peso", "80%"))
        assertEquals(expected, componente1fuc1.elementToString())

        expected = "<fuc codigo=\"M1111\">"
        fuc1.addAttribute(XmlAttribute("codigo", "M4310"))
        fuc1.updateAttribute(XmlAttribute("codigo", "M1111"))
        assertEquals(expected, fuc1.elementToString())
    }

    @Test
    fun testListDistinctElementNames() {
        assertEquals(setOf("plano", "curso", "fuc", "avaliacao", "componente"), plano.listDistinctElementNames())
        assertEquals(setOf("curso"), curso.listDistinctElementNames())
    }

    @Test
    fun testElementToString() {
        assertEquals("<plano>", plano.elementToString())
        assertEquals("<curso>MEI</curso>", curso.elementToString())
    }
}
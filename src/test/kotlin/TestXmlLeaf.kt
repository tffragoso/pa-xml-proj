import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class TestXmlLeaf {
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
        //val planoTest = XmlTag("plano")
        val cursoTest = XmlLeaf("curso", plano, leafText = "MEI")
        val componente1fuc1Test = XmlLeaf("componente", avaliacaofuc1)

        // adding attribute to a xml leaf
        var attribute1 = Attribute("codigo", "M4310")
        cursoTest.addAttribute(attribute1)
        assert(cursoTest.attributes.contains(attribute1))

        // adding two attributes to a xml leaf
        attribute1 = Attribute("nome", "Quizzes")
        componente1fuc1Test.addAttribute(attribute1)
        val attribute2 = Attribute("peso", "20%")
        componente1fuc1Test.addAttribute(attribute2)
        assert(componente1fuc1Test.attributes.containsAll(listOf(attribute1, attribute2)))

        // adding an attribute to a xml leaf that already exist and doesn't update the value
        val attribute3 = Attribute("peso", "40%")
        componente1fuc1Test.addAttribute(attribute3)
        assert(componente1fuc1Test.attributes.containsAll(listOf(attribute1, attribute2)))
    }


    /**
     * Test removing attribute from element
     */
    @Test
    fun testRemoveAttribute() {
        val componente1fuc1Test = XmlLeaf("componente", avaliacaofuc1)

        // remove an attribute from a xml leaf that doesn't exist and the object doesn't have any attributes
        var attribute1 = Attribute("nome", "")
        componente1fuc1Test.removeAttribute(attribute1.getName())
        assert(componente1fuc1Test.attributes.isEmpty())

        // removing an attribute from a xml leaf that doesn't exist and the object has more attributes
        attribute1 = Attribute("nome", "Quizzes")
        componente1fuc1Test.addAttribute(attribute1)
        val componente1fuc1Test2 = componente1fuc1Test.copy()
        val attribute2 = Attribute("peso", "")
        componente1fuc1Test.removeAttribute(attribute2.getName())
        assertEquals(componente1fuc1Test2.attributes, componente1fuc1Test.attributes)

        // removing the only attribute that exists from a xml leaf
        componente1fuc1Test.removeAttribute(attribute1.getName())
        assert(componente1fuc1Test.attributes.isEmpty())
    }

    /**
     * Test removing attribute from element with strings
     */
    @Test
    fun testRemoveAttributeWithPrint() {
        var expected = "<componente/>"
        componente1fuc1.removeAttribute("nome")
        assertEquals(expected, componente1fuc1.print())

        componente1fuc1.addAttribute(Attribute("nome", "Quizzes"))
        expected = "<componente nome=\"Quizzes\"/>"
        componente1fuc1.removeAttribute("peso")
        assertEquals(expected, componente1fuc1.print())

        componente1fuc1.addAttribute(Attribute("nome", "Quizzes"))
        expected = "<componente/>"
        componente1fuc1.removeAttribute("nome")
        assertEquals(expected, componente1fuc1.print())

        componente1fuc1.addAttribute(Attribute("nome", "Quizzes"))
        componente1fuc1.addAttribute(Attribute("peso", "20%"))
        expected = "<componente peso=\"20%\"/>"
        componente1fuc1.removeAttribute("nome")
        assertEquals(expected, componente1fuc1.print())

        expected = "<curso>MEI</curso>"
        curso.addAttribute(Attribute("codigo", "M4310"))
        curso.removeAttribute("codigo")
        assertEquals(expected, curso.print())
    }


    /**
     * Test update attribute from entity
     */
    @Test
    fun testUpdateAttribute() {
        val componente1fuc1Test = XmlLeaf("componente", avaliacaofuc1)

        val attribute1 = Attribute("nome", "Quizzes")
        val attribute2 = Attribute("nome", "Quizz1")
        componente1fuc1Test.updateAttribute(attribute1)
        //update attribute that doesn't exist in a xml leaf
        assertEquals(componente1fuc1.attributes, componente1fuc1Test.attributes)

        //update attribute that exists in a xml leaf
        componente1fuc1Test.addAttribute(attribute1)
        componente1fuc1Test.updateAttribute(attribute2)
        assert(componente1fuc1Test.attributes.none { it.getName() == "nome" && it.getValue() == "Quizzes" } ?: true &&
                componente1fuc1Test.attributes.any { it.getName() == "nome" && it.getValue() == "Quizz1" } ?: false)

    }

    /**
     * Test update attribute from entity with prints
     */
    @Test
    fun testUpdateAttributeWithPrint() {
        val componente1fuc1Test = XmlLeaf("componente", avaliacaofuc1)

        var expected = "<componente/>"
        componente1fuc1Test.updateAttribute(Attribute("nome", "Quizzes"))
        assertEquals(expected, componente1fuc1Test.print())
        componente1fuc1Test.addAttribute(Attribute("nome", "Quizzes"))

        expected = "<componente nome=\"Quizz1\"/>"
        componente1fuc1Test.updateAttribute(Attribute("nome", "Quizz1"))
        assertEquals(expected, componente1fuc1Test.print())

        expected = "<componente nome=\"Quizz1\"/>"
        componente1fuc1Test.updateAttribute(Attribute("peso", "20%"))
        assertEquals(expected, componente1fuc1Test.print())

        componente1fuc1Test.addAttribute(Attribute("peso", "20%"))
        expected = "<componente nome=\"Projeto\" peso=\"80%\"/>"
        componente1fuc1Test.updateAttribute(Attribute("nome", "Projeto"))
        componente1fuc1Test.updateAttribute(Attribute("peso", "80%"))
        assertEquals(expected, componente1fuc1Test.print())
    }
}
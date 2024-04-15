import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

/**
 *  This is the class for unitary testing
 */
class Test {

    val plano = XmlTag("plano")
    val curso = XmlLeaf("curso", plano, leafText = "MEI")
    val fuc1 = XmlTag("fuc", plano)
    val avaliacaofuc1 = XmlTag("avaliacao", fuc1)
    val componente1fuc1 = XmlLeaf("componente", avaliacaofuc1)

    /**
     * Return the [n] occurrence [XmlTag] with the name [elementName]
     */
    private fun createXML1(elementName: String, n: Int): XmlTag {
        val planoTest = XmlTag("plano")
        val cursoTest = XmlLeaf("curso", planoTest, attributes = null, "Mestrado em Engenharia Informática")
        val fuc1Test = XmlTag("fuc", planoTest, mutableListOf(Attribute("codigo", "M4310")))
        val nomefuc1Test = XmlLeaf("nome", fuc1Test, null, "Programação Avançada")
        val ectsfuc1Test = XmlLeaf("ects", fuc1Test, null, "6.0")
        val avaliacaofuc1Test = XmlTag("avaliacao", fuc1Test, null)
        val componente1fuc1Test = XmlLeaf(
            "componente",
            avaliacaofuc1Test,
            mutableListOf(Attribute("nome", "Quizzes"), Attribute("peso", "20%")),
            null
        )
        val componente2fuc1Test = XmlLeaf(
            "componente",
            avaliacaofuc1Test,
            mutableListOf(Attribute("nome", "Projeto"), Attribute("peso", "80%")),
            null
        )
        val fuc2Test = XmlTag("fuc", planoTest, mutableListOf(Attribute("codigo", "03782")))
        val nomefuc2Test = XmlLeaf("nome", fuc2Test, null, "Dissertação")
        val ectsfuc2Test = XmlLeaf("ects", fuc2Test, null, "42.0")
        val avaliacaofuc2Test = XmlTag("avaliacao", fuc2Test, null)
        val componente1fuc2Test = XmlLeaf(
            "componente",
            avaliacaofuc2Test,
            mutableListOf(Attribute("nome", "Dissertação"), Attribute("peso", "60%")),
            null
        )
        val componente2fuc2Test = XmlLeaf(
            "componente",
            avaliacaofuc2Test,
            mutableListOf(Attribute("nome", "Apresentação"), Attribute("peso", "20%")),
            null
        )
        val componente3fuc2Test = XmlLeaf(
            "componente",
            avaliacaofuc2Test,
            mutableListOf(Attribute("nome", "Discussão"), Attribute("peso", "20%")),
            null
        )
        var elementToReturn: XmlTag = XmlTag("none")
        var i: Int = 0
        planoTest.accept {
            if (it.name == elementName) {
                i++
                if (i == n)
                    elementToReturn = it as XmlTag
                return@accept false
            }
            true
        }
        return elementToReturn
    }

    @Test
    fun testParentAndChildren() {
        assertNull(plano.parent)
        assertEquals(plano, curso.parent)
        assertEquals(fuc1, avaliacaofuc1.parent)
        assertEquals(listOf(curso, fuc1), plano.children)
        assertEquals(listOf(avaliacaofuc1), fuc1.children)
    }

    @Test
    fun testListDistinctElementNames() {
        assertEquals(setOf("plano", "curso", "fuc", "avaliacao", "componente"), plano.listDistinctElementNames())
        assertEquals(setOf("curso"), curso.listDistinctElementNames())
    }

    @Test
    fun testAddChildElement() {
        val fuc2 = XmlTag("fuc2")
        assertNull(fuc2.parent)

        plano.addChildElement(fuc2)
        assertEquals(listOf(curso, fuc1, fuc2), plano.children)

        fuc2.addChildElement(avaliacaofuc1)
        assertEquals(listOf(avaliacaofuc1), fuc2.children)
        assertEquals(avaliacaofuc1.parent, fuc2)
        assertEquals(listOf<XmlElement>(), fuc1.children)
    }

    @Test
    fun testRemoveChildElement() {
        fuc1.removeChildElement(avaliacaofuc1)
        assertEquals(listOf<XmlElement>(), fuc1.children)
        assertNull(avaliacaofuc1.parent)
    }

    @Test
    fun testRenameElements() {
        val fuc2 = XmlTag("fuc", plano)
        plano.renameElements("fuc", "fichaUnidadeCurricular")
        assertEquals("fichaUnidadeCurricular", fuc1.name)
        assertEquals("fichaUnidadeCurricular", fuc2.name)
        assertEquals(setOf("fichaUnidadeCurricular", "avaliacao", "componente"), fuc1.listDistinctElementNames())

        plano.renameElements("abc", "def")
        assertEquals(
            setOf("plano", "curso", "fichaUnidadeCurricular", "avaliacao", "componente"),
            plano.listDistinctElementNames()
        )
    }

    /**
     * Test adding attribute to element
     */
    @Test()
    fun testAddAttribute() {
        val planoTest = XmlTag("plano")
        val cursoTest = XmlLeaf("curso", plano, leafText = "MEI")
        val componente1fuc1Test = XmlLeaf("componente", avaliacaofuc1)

        // adding attribute to a xml leaf
        var attribute1 = Attribute("codigo", "M4310")
        cursoTest.addAttribute(attribute1)
        assert(cursoTest.attributes!!.contains(attribute1))

        // adding two attributes to a xml leaf
        attribute1 = Attribute("nome", "Quizzes")
        componente1fuc1Test.addAttribute(attribute1)
        val attribute2 = Attribute("peso", "20%")
        componente1fuc1Test.addAttribute(attribute2)
        assert(componente1fuc1Test.attributes!!.containsAll(listOf(attribute1, attribute2)))

        // adding an attribute to a xml leaf that already exist and doesn't update the value
        val attribute3 = Attribute("peso", "40%")
        componente1fuc1Test.addAttribute(attribute3)
        assert(componente1fuc1Test.attributes!!.containsAll(listOf(attribute1, attribute2)))

        // adding attribute to a xml tag
        attribute1 = Attribute("codigo", "M4310")
        planoTest.addAttribute(attribute1)
        assert(planoTest.attributes!!.contains(attribute1))

        // adding two attributes to a xml tag
        planoTest.addAttribute(attribute2)
        assert(planoTest.attributes!!.containsAll(listOf(attribute1, attribute2)))

        // adding an attribute to a xml tag that already exist and doesn't update the value
        planoTest.addAttribute(attribute3)
        assert(planoTest.attributes!!.containsAll(listOf(attribute1, attribute2)))
    }

    /**
     * Test adding attribute to element with prints
     */
    @Test
    fun testAddAttributeWithPrint() {
        val planoTest = XmlTag("plano")
        val avaliacaofuc1Test = XmlTag("avaliacao", fuc1)
        val cursoTest = XmlLeaf("curso", plano, leafText = "MEI")
        val componente1fuc1Test = XmlLeaf("componente", avaliacaofuc1)

        //add element to a Leaf with no attributes
        var expected = "<curso codigo=\"M4310\">MEI</curso>"
        cursoTest.addAttribute(Attribute("codigo", "M4310"))
        assertEquals(expected, cursoTest.print())

        //add two attributes to a Leaf with no attributes
        expected = "<componente nome=\"Quizzes\" peso=\"20%\"/>"
        componente1fuc1Test.addAttribute(Attribute("nome", "Quizzes"))
        componente1fuc1Test.addAttribute(Attribute("peso", "20%"))
        assertEquals(expected, componente1fuc1Test.print())

        //add two attributes to a Leaf with no attributes and adding a third one with name equal to an existing
        //attribute
        expected = "<componente nome=\"Quizzes\" peso=\"20%\"/>"
        componente1fuc1Test.addAttribute(Attribute("nome", "Quizzes"))
        componente1fuc1Test.addAttribute(Attribute("peso", "20%"))
        componente1fuc1Test.addAttribute(Attribute("peso", "40%"))
        assertEquals(expected, componente1fuc1Test.print())

        //add element to a Tag with no attributes
        expected = "<plano codigo=\"M4310\">"
        planoTest.addAttribute(Attribute("codigo", "M4310"))
        assertEquals(expected, planoTest.print())

        //add two attributes to a Tag with no attributes
        expected = "<avaliacao nome=\"Quizzes\" peso=\"20%\">"
        avaliacaofuc1Test.addAttribute(Attribute("nome", "Quizzes"))
        avaliacaofuc1Test.addAttribute(Attribute("peso", "20%"))
        assertEquals(expected, avaliacaofuc1Test.print())

        //add two attributes to a Tag with no attributes and adding a third one with name equal to an existing
        //attribute
        expected = "<avaliacao nome=\"Quizzes\" peso=\"20%\">"
        avaliacaofuc1Test.addAttribute(Attribute("nome", "Quizzes"))
        avaliacaofuc1Test.addAttribute(Attribute("peso", "20%"))
        avaliacaofuc1Test.addAttribute(Attribute("peso", "40%"))
        assertEquals(expected, avaliacaofuc1Test.print())
    }

    /**
     * Test removing attribute from element
     */
    @Test
    fun testRemoveAttribute() {
        val componente1fuc1Test = XmlLeaf("componente", avaliacaofuc1)

        // remove an attribute from a xml leaf that doesn't exist and the object doesn't have any attributes
        var attribute1 = Attribute("nome", "")
        componente1fuc1Test.removeAttribute(attribute1.name)
        assert(componente1fuc1Test.attributes.isNullOrEmpty())

        // removing an attribute from a xml leaf that doesn't exist and the object has more attributes
        attribute1 = Attribute("nome", "Quizzes")
        componente1fuc1Test.addAttribute(attribute1)
        val componente1fuc1Test2 = componente1fuc1Test.copy()
        val attribute2 = Attribute("peso", "")
        assertEquals(componente1fuc1Test2, componente1fuc1Test.removeAttribute(attribute2.name))

        // removing the only attribute that exists from a xml leaf
        componente1fuc1Test.removeAttribute(attribute1.name)
        assert(componente1fuc1Test.attributes.isNullOrEmpty())

        val planoTest = XmlTag("plano")

        // remove an attribute from a xml tag that doesn't exist and the object doesn't have any attributes
        attribute1 = Attribute("nome", "")
        planoTest.removeAttribute(attribute1.name)
        assert(planoTest.attributes.isNullOrEmpty())

        // removing an attribute from a xml tag that doesn't exist and the object has more attributes
        attribute1 = Attribute("nome", "Quizzes")
        planoTest.addAttribute(attribute1)
        val planoTest2 = planoTest.copy()
        assertEquals(planoTest2, planoTest.removeAttribute(attribute2.name))

        // removing the only attribute that exists from a xml tag
        planoTest.removeAttribute(attribute1.name)
        assert(componente1fuc1Test.attributes.isNullOrEmpty())
    }

    /**
     * Test removing attribute from element with strings
     */
    @Test
    fun testRemoveAttributeWithPrint() {
        var expected: String

        expected = "<componente/>"
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
        val planoTest = XmlTag("plano")

        val attribute1 = Attribute("nome", "Quizzes")
        val attribute2 = Attribute("nome", "Quizz1")

        //update attribute that doesn't exist in a xml leaf
        assertEquals(componente1fuc1Test, componente1fuc1Test.updateAttribute(attribute1))

        //update attribute that exists in a xml leaf
        componente1fuc1Test.addAttribute(attribute1)
        componente1fuc1Test.updateAttribute(attribute2)
        assert(componente1fuc1Test.attributes?.none { it.name == "nome" && it.value == "Quizzes" } ?: true &&
                componente1fuc1Test.attributes?.any { it.name == "nome" && it.value == "Quizz1" } ?: false)

        //update attribute that doesn't exist in a xml tag
        assertEquals(planoTest, planoTest.updateAttribute(attribute1))

        //update attribute that exists in a xml tag
        planoTest.addAttribute(attribute1)
        planoTest.updateAttribute(attribute2)
        assert(planoTest.attributes?.none { it.name == "nome" && it.value == "Quizzes" } ?: true &&
                planoTest.attributes?.any { it.name == "nome" && it.value == "Quizz1" } ?: false)
    }

    /**
     * Test update attribute from entity with prints
     */
    @Test
    fun testUpdateAttributeWithPrint() {
        val componente1fuc1Test = XmlLeaf("componente", avaliacaofuc1)
        val fuc1Test = XmlTag("fuc", plano)

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

        expected = "<fuc codigo=\"M1111\">"
        fuc1Test.addAttribute(Attribute("codigo", "M4310"))
        fuc1Test.updateAttribute(Attribute("codigo", "M1111"))
        assertEquals(expected, fuc1Test.print())
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

    /**
     * Test adding attribute to element and all his children with the given name
     */
    @Test
    fun testAddAttributeGlobally() {
        var expected = "<fuc codigo=\"M4310\">"
        expected += "<nome>Programação Avançada</nome>"
        expected += "<ects>6.0</ects>"
        expected += "<avaliacao>"
        expected += "<componente nome=\"Quizzes\" peso=\"20%\" teste=\"valor\"/>"
        expected += "<componente nome=\"Projeto\" peso=\"80%\" teste=\"valor\"/>"
        expected += "</avaliacao>"
        expected += "</fuc>"

        assertEquals(
            expected, (createXML1("fuc", 1).addAttributeGlobally(
                elementName = "componente",
                attributeName = "teste",
                attributeValue = "valor"
            ) as XmlTag).prettyPrint()
        )

        var expectedObject: XmlTag = createXML1("fuc", 1)
        expectedObject.children.forEach() { e ->
            if (e.name == "componente") {
                e.addAttribute(Attribute("teste", "valor"))
            }
        }
    }
}
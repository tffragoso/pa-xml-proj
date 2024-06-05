import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/**
 * This is a Test class where methods relating to XmlTag objects are tested.
 */
class TestXmlDocument {
    val plano = XmlTag("plano")
    val curso = XmlLeaf("curso", plano, mutableListOf(), "Mestrado em Engenharia Informática")
    val fuc1 = XmlTag("fuc", plano, mutableListOf(XmlAttribute("codigo", "M4310")))
    val nomefuc1 = XmlLeaf("nome", fuc1, mutableListOf(), "Programação Avançada")
    val ectsfuc1 = XmlLeaf("ects", fuc1, mutableListOf(), "6.0")
    val avaliacaofuc1 = XmlTag("avaliacao", fuc1, mutableListOf())
    val componente1fuc1 = XmlLeaf(
        "componente",
        avaliacaofuc1,
        mutableListOf(XmlAttribute("nome", "Quizzes"), XmlAttribute("peso", "20%")),
        null
    )
    val componente2fuc1 = XmlLeaf(
        "componente",
        avaliacaofuc1,
        mutableListOf(XmlAttribute("nome", "Projeto"), XmlAttribute("peso", "80%")),
        null
    )
    val fuc2 = XmlTag("fuc", plano, mutableListOf(XmlAttribute("codigo", "03782")))
    val nomefuc2 = XmlLeaf("nome", fuc2, mutableListOf(), "Dissertação")
    val ectsfuc2 = XmlLeaf("ects", fuc2, mutableListOf(), "42.0")
    val avaliacaofuc2 = XmlTag("avaliacao", fuc2, mutableListOf())
    val componente1fuc2 = XmlLeaf(
        "componente",
        avaliacaofuc2,
        mutableListOf(XmlAttribute("nome", "Dissertação"), XmlAttribute("peso", "60%")),
        null
    )
    val componente2fuc2 = XmlLeaf(
        "componente",
        avaliacaofuc2,
        mutableListOf(XmlAttribute("nome", "Apresentação"), XmlAttribute("peso", "20%")),
        null
    )
    val componente3fuc2 = XmlLeaf(
        "componente",
        avaliacaofuc2,
        mutableListOf(XmlAttribute("nome", "Discussão"), XmlAttribute("peso", "20%")),
        null
    )
    val doc = XmlDocument("document", plano)

    /**
     * Test renaming all elements with a given name.
     */
    @Test
    fun testRenameElements() {
        doc.renameElements("fuc", "fichaUnidadeCurricular")
        assertEquals("fichaUnidadeCurricular", fuc1.name)
        assertEquals("fichaUnidadeCurricular", fuc2.name)
        assertEquals(setOf("fichaUnidadeCurricular", "avaliacao", "componente", "nome", "ects"), fuc1.listDistinctElementNames())

        doc.renameElements("abc", "def")
        assertEquals(
            setOf("plano", "curso", "fichaUnidadeCurricular", "avaliacao", "componente", "nome", "ects"),
            plano.listDistinctElementNames()
        )
    }

    /**
     * Test removing all elements with a given name.
     */
    @Test
    fun testRemoveElements() {
        doc.removeElements("blabla")
        assertEquals(mutableListOf(curso, fuc1, fuc2), doc.body.children)
        doc.removeElements("fuc")
        assertEquals(mutableListOf(curso), doc.body.children)
    }

    /**
     * Test adding an attribute to all elements with the given name.
     */
    @Test
    fun testAddAttributeGlobally() {
        doc.addAttributeGlobally("componente", "testeAtributo", "testeValor")

        assertEquals(listOf("nome", "peso", "testeAtributo"), componente1fuc1.attributes.map { it.getName() })
        assertEquals(listOf("Quizzes", "20%", "testeValor"), componente1fuc1.attributes.map { it.getValue() })
        assertEquals(listOf("nome", "peso", "testeAtributo"), componente2fuc1.attributes.map { it.getName() })
        assertEquals(listOf("Projeto", "80%", "testeValor"), componente2fuc1.attributes.map { it.getValue() })
        assertEquals(listOf("nome", "peso", "testeAtributo"), componente1fuc2.attributes.map { it.getName() })
        assertEquals(listOf("Dissertação", "60%", "testeValor"), componente1fuc2.attributes.map { it.getValue() })
        assertEquals(listOf("nome", "peso", "testeAtributo"), componente2fuc2.attributes.map { it.getName() })
        assertEquals(listOf("Apresentação", "20%", "testeValor"), componente2fuc2.attributes.map { it.getValue() })
        assertEquals(listOf("nome", "peso", "testeAtributo"), componente3fuc2.attributes.map { it.getName() })
        assertEquals(listOf("Discussão", "20%", "testeValor"), componente3fuc2.attributes.map { it.getValue() })
    }

    /**
     * Test renaming an attribute in all elements with the given name.
     */
    @Test
    fun testRenameAttributeGlobally() {
        doc.renameAttributeGlobally("componente", "nome", "tipo")

        assertEquals(listOf("tipo", "peso"), componente1fuc1.attributes.map { it.getName() })
        assertEquals(listOf("tipo", "peso"), componente2fuc1.attributes.map { it.getName() })
        assertEquals(listOf("tipo", "peso"), componente1fuc2.attributes.map { it.getName() })
        assertEquals(listOf("tipo", "peso"), componente2fuc2.attributes.map { it.getName() })
        assertEquals(listOf("tipo", "peso"), componente3fuc2.attributes.map { it.getName() })

    }

    /**
     * Test removing an attribute from all elements with the given name.
     */
    @Test
    fun testRemoveAttributeGlobally() {
        doc.removeAttributeGlobally("componente", "nome")

        assertEquals(listOf("peso"), componente1fuc1.attributes.map { it.getName() })
        assertEquals(listOf("peso"), componente2fuc1.attributes.map { it.getName() })
        assertEquals(listOf("peso"), componente1fuc2.attributes.map { it.getName() })
        assertEquals(listOf("peso"), componente2fuc2.attributes.map { it.getName() })
        assertEquals(listOf("peso"), componente3fuc2.attributes.map { it.getName() })
    }

    /**
     * Test the microXpath method to list all elements within a specified xpath.
     */
    @Test
    fun testMicroXpath() {
        assertEquals(
            listOf(componente1fuc1, componente2fuc1, componente1fuc2, componente2fuc2, componente3fuc2),
            doc.microXpath("fuc/avaliacao/componente", doc.body)
        )
        assertEquals(
            listOf(componente1fuc1, componente2fuc1, componente1fuc2, componente2fuc2, componente3fuc2),
            doc.microXpath("avaliacao/componente", doc.body)
        )
        assertEquals(listOf<XmlElement>(), doc.microXpath("", doc.body))
        assertEquals(
            listOf<XmlElement>(),
            doc.microXpath("avaliacao,componente", doc.body)
        )
    }
}
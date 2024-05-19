/**
 * Return the [n] occurrence [XmlTag] with the name [elementName]
 */
fun createXML1(elementName: String, n: Int): XmlTag {
    val planoTest = XmlTag("plano")
    val cursoTest = XmlLeaf("curso", planoTest, mutableListOf(), "Mestrado em Engenharia Informática")
    val fuc1Test = XmlTag("fuc", planoTest, mutableListOf(Attribute("codigo", "M4310")))
    val nomefuc1Test = XmlLeaf("nome", fuc1Test, mutableListOf(), "Programação Avançada")
    val ectsfuc1Test = XmlLeaf("ects", fuc1Test, mutableListOf(), "6.0")
    val avaliacaofuc1Test = XmlTag("avaliacao", fuc1Test, mutableListOf())
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
    val nomefuc2Test = XmlLeaf("nome", fuc2Test, mutableListOf(), "Dissertação")
    val ectsfuc2Test = XmlLeaf("ects", fuc2Test, mutableListOf(), "42.0")
    val avaliacaofuc2Test = XmlTag("avaliacao", fuc2Test, mutableListOf())
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
    var elementToReturn = XmlTag("none")
    var i = 0
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
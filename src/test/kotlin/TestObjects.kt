import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import java.io.File

/**
 * For testing purposes: class Plano.
 */
class Plano(
    @Leaf
    val curso: String,
    @Inline
    val fuc: List<FUC>
)

/**
 * For testing purposes: class FUC.
 */
@XmlAdapter(FUCAdapter::class)
class FUC(
    @Attribute
    var codigo: String,
    @Leaf
    val nome: String,
    @Leaf
    val ects: Double,
    val observacoes: String,
    @Nested @Leaf
    val avaliacao: List<ComponenteAvaliacao>
)

/**
 * For testing purposes: class ComponenteAvaliacao.
 */
@XmlAdapter(ComponenteAvaliacaoAdapter::class, function = "changeName", newName ="componente")
class ComponenteAvaliacao(
    @Attribute
    val nome: String,
    @Attribute @XmlString(AddPercentage::class,function="addPercentage")
    val peso: Any
)

/**
 * For testing purposes: class to test XmlString annotation.
 */
class AddPercentage {

    /**
     * Method to add percentage "%" character to string.
     */
    fun addPercentage(value: Any): String =
        when(value) {
            is Int, Double, Float -> value.toString() + "%"
            is String -> if(value.endsWith("%"))
                value
            else
                "$value%"
            else -> "$value%"
        }
}

/**
 * For testing purposes: class to test XmlString annotation
 */
class ComponenteAvaliacaoAdapter{

    /**
     * Method to change the name of the mapped xml object.
     */
    fun changeName(newName: String): String {
        return newName
    }
}

/**
 * This is a Test class where the mapping of objects to xml entities, and post-mapping tweaks are tested.
 */
class TestObjects {

    private val f = FUC("M4310", "Programação Avançada", 6.0,
        "la la...",
        listOf(
            ComponenteAvaliacao("Quizzes", "20"),
            ComponenteAvaliacao("Projeto", "80")
        ))
    private val p = Plano("Mestrado em Engenharia Informática",
        listOf(
            FUC("M4310", "Programação Avançada", 6.0,
                "la la...",
                listOf(
                    ComponenteAvaliacao("Quizzes", 20),
                    ComponenteAvaliacao("Projeto", 80.5)
                )),
            FUC("03782", "Dissertação", 42.0,
                "la la...",
                listOf(
                    ComponenteAvaliacao("Dissertação", "60%"),
                    ComponenteAvaliacao("Apresentação", "20%"),
                    ComponenteAvaliacao("Discussão", "20%")
                ))
        )
    )

    /**
     * Test mapping object of class Any to xml element.
     * Also tests prettyPrint method.
     */
    @Test
    fun testMapXML() {
        val expected = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<plano>\n" +
                "\t<curso>Mestrado em Engenharia Informática</curso>\n" +
                "\t<fuc codigo=\"M4310\">\n" +
                "\t\t<avaliacao>\n" +
                "\t\t\t<componente nome=\"Quizzes\" peso=\"20%\"/>\n" +
                "\t\t\t<componente nome=\"Projeto\" peso=\"80.5%\"/>\n" +
                "\t\t</avaliacao>\n" +
                "\t\t<ects>6.0</ects>\n" +
                "\t\t<nome>Programação Avançada</nome>\n" +
                "\t</fuc>\n" +
                "\t<fuc codigo=\"03782\">\n" +
                "\t\t<avaliacao>\n" +
                "\t\t\t<componente nome=\"Dissertação\" peso=\"60%\"/>\n" +
                "\t\t\t<componente nome=\"Apresentação\" peso=\"20%\"/>\n" +
                "\t\t\t<componente nome=\"Discussão\" peso=\"20%\"/>\n" +
                "\t\t</avaliacao>\n" +
                "\t\t<ects>42.0</ects>\n" +
                "\t\t<nome>Dissertação</nome>\n" +
                "\t</fuc>\n" +
                "</plano>"
        val doc = XmlDocument("documento", mapXml(p))
        doc.prettyPrint(".\\XmlOutput.txt")
        val actual = File(".\\XmlOutput.txt").readText()
        assertEquals(expected,actual)
    }

    /**
     * Test creating xml element with a name different from the class name.
     */
    @Test
    fun testRenameLeaf(){
        val expectedLeaf = XmlLeaf("componente",null,mutableListOf())
        val changedLeaf = ComponenteAvaliacao("Quizzes", 20)
        assertEquals(expectedLeaf,renameLeaf(changedLeaf::class))
    }

    /**
     * Test adding percent character to values.
     */
    @Test
    fun testAddPercentage(){
        assertEquals("20%",AddPercentage().addPercentage(20))
        val value : Double = 5.0
        assertEquals("5.0%",AddPercentage().addPercentage(value))
        assertEquals("5.87%",AddPercentage().addPercentage(5.87F))
        assertEquals("456gght%",AddPercentage().addPercentage("456gght%"))
        assertEquals("456gght%",AddPercentage().addPercentage("456gght"))
        assertEquals("456.89%",AddPercentage().addPercentage("456.89"))
    }
}
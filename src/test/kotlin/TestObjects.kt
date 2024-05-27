
import org.junit.jupiter.api.Test

class Plano(
    @Leaf
    val curso: String,
    @Inline
    val fuc: List<FUC>
)
class FUC(
    @Attribute
    val codigo: String,
    @Leaf
    val nome: String,
    @Leaf
    val ects: Double,
    val observacoes: String,
    @Nested @Leaf
    val avaliacao: List<ComponenteAvaliacao>
)

@XmlAdapter(ComponenteAvaliacaoAdapter::class,
    function = "changeName",
    newName ="componente")
class ComponenteAvaliacao(
    @Attribute
    val nome: String,
    @Attribute @XmlString(AddPercentage::class)
    val peso: Any
)

class AddPercentage {
    fun addPercentage(value: Any): String =
        when(value) {
            is Int, Double, Float -> value.toString() + "%"
            is String -> if(value.endsWith("%"))
                value
            else
                "$value%"
            else -> value.toString()
        }
}

class ComponenteAvaliacaoAdapter{
    fun changeName(newName: String): String {
        return newName
    }
}

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

    private val doc = XmlDocument("documento", mapXml(p))
    @Test
    fun testMapXML() {
        //println(mapXml(f).elementToString())
        //println(printTag(mapXml(f)))
        //println(printTag(mapXml(p)))
        doc.prettyPrint(".\\XmlOutput.txt")
    }
}

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
    val avaliacao: List<Componente>
)

class Componente(
    @Attribute
    val nome: String,
    @Attribute
    val peso: String
)

class testObjects {

    val f = FUC("M4310", "Programação Avançada", 6.0,
        "la la...",
        listOf(
            Componente("Quizzes", "20"),
            Componente("Projeto", "80")
        ))

    val p = Plano("Mestrado em Engenharia Informática",
        listOf(
            FUC("M4310", "Programação Avançada", 6.0,
                "la la...",
                listOf(
                    Componente("Quizzes", "20"),
                    Componente("Projeto", "80")
                )),
            FUC("03782", "Dissertação", 42.0,
                "la la...",
                listOf(
                    Componente("Dissertação", "60%"),
                    Componente("Apresentação", "20%"),
                    Componente("Discussão", "20%")
                ))
        )
        )

    val doc = XmlDocument("documento", mapXml(p))
    @Test
    fun testMapXML() {
      //  println(mapXml(f).elementToString())
       // println(auxPrint(mapXml(f)))
        //println(auxPrint(mapXml(p)))
        println(doc.prettyPrint())
    }
}
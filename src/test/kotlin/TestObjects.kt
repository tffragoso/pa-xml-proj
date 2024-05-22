
import org.junit.jupiter.api.Test

class Plano(
    @IsLeaf
    val curso: String,
    @ListObjectsNoName
    val fuc: List<FUC>
)
class FUC(
    @IsAttribute
    val codigo: String,
    @IsLeaf
    val nome: String,
    @IsLeaf
    val ects: Double,
    @Hiden
    val observacoes: String,
    @ListObjects
    val avaliacao: List<Avaliacao>
)

class Avaliacao(
    @IsAttribute
    val nome: String,
    @IsAttribute
    val peso: String
)

class testObjects {

    val f = FUC("M4310", "Programação Avançada", 6.0,
        "la la...",
        listOf(
            Avaliacao("Quizzes", "20"),
            Avaliacao("Projeto", "80")
        ))

    val p = Plano("Mestrado em Engenharia Informática",
        listOf(
            FUC("M4310", "Programação Avançada", 6.0,
                "la la...",
                listOf(
                    Avaliacao("Quizzes", "20"),
                    Avaliacao("Projeto", "80")
                )),
            FUC("03782", "Dissertação", 42.0,
                "la la...",
                listOf(
                    Avaliacao("Dissertação", "60%"),
                    Avaliacao("Apresentação", "20%"),
                    Avaliacao("Discussão", "20%")
                ))
        )
        )
    @Test
    fun testMapXML() {
      //  println(mapXml(f).elementToString())
       // println(auxPrint(mapXml(f)))
        println(auxPrint(mapXml(p)))
    }
}
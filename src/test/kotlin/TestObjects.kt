import kotlin.reflect.full.declaredMemberProperties
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

@Target(AnnotationTarget.CLASS, AnnotationTarget.PROPERTY)
annotation class IsTag

@Target(AnnotationTarget.CLASS, AnnotationTarget.PROPERTY)
annotation class IsLeaf

@Target(AnnotationTarget.PROPERTY)
annotation class IsAttribute

@Target(AnnotationTarget.PROPERTY)
annotation class Hiden

class FUC(
    @IsAttribute
    val codigo: String,
    @IsLeaf
    val nome: String,
    @IsLeaf
    val ects: Double,
    @Hiden
    val observacoes: String
)

class testObjects {

    val f = FUC("M4310", "Programação Avançada", 6.0, "la la...")
    @Test
    fun testMapXML() {

        println(mapXml(f).elementToString())
    }
}
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.reflect.full.declaredMemberFunctions
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.memberProperties

/**
 * For testing purposes: class to test XmlAdapter annotation.
 */
class FUCAdapter {

    /**
     * Method to update attribute "codigo" value.
     */
    fun changeCodigoValue(objFuc: FUC, newValue: String) {
        objFuc.codigo = newValue
    }
}

/**
 * This is a Test class where the XmlAdapter annotation is tested.
 */
class TestFucAdapterClass (){

    private val f = FUC("M4310", "Programação Avançada", 6.0,
        "la la...",
        listOf(
            ComponenteAvaliacao("Quizzes", "20"),
            ComponenteAvaliacao("Projeto", "80")
        ))

    /**
     * Test the example FucAdapterClass.
     */
    @Test
    fun testFucAdapterClass(){
        val g=FUC("M4313", "Programação Avançada", 6.0,
            "la la...",
            listOf(
                ComponenteAvaliacao("Quizzes", "20"),
                ComponenteAvaliacao("Projeto", "80")
            ))

        FUCAdapter().changeCodigoValue(f,"M4313")
        assertEquals(g.codigo,f.codigo)
    }
}
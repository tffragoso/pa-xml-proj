import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.reflect.full.declaredMemberFunctions
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.memberProperties

class FUCAdapter {
    fun ChangeCodigoValue(objFuc:FUC, newValue:String){
        objFuc.codigo= newValue
    }
}

class TestFucAdapterClass (){

    private val f = FUC("M4310", "Programação Avançada", 6.0,
        "la la...",
        listOf(
            ComponenteAvaliacao("Quizzes", "20"),
            ComponenteAvaliacao("Projeto", "80")
        ))

    @Test
    fun testFucAdapterClass(){
        val g=FUC("M4313", "Programação Avançada", 6.0,
            "la la...",
            listOf(
                ComponenteAvaliacao("Quizzes", "20"),
                ComponenteAvaliacao("Projeto", "80")
            ))

        FUCAdapter().ChangeCodigoValue(f,"M4313")
        assertEquals(g.codigo,f.codigo)
    }

    @Test
    fun testFucAdapterClassWithPrint(){
        val expected = "<fuc codigo=\"M4313\">" +
                "<avaliacao>" +
                "<componente nome=\"Quizzes\" peso=\"20%\"/>" +
                "<componente nome=\"Projeto\" peso=\"80%\"/>" +
                "</avaliacao>" +
                "<ects>6.0</ects>" +
                "<nome>Programação Avançada</nome>" +
                "</fuc>"

        FUCAdapter().ChangeCodigoValue(f,"M4313")
        val fucMapped = mapXml(f)
        assertEquals(expected, printTag(fucMapped))
    }
}
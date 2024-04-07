import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/**
 *  This is the class for unitary testing
 */

class Test {

    //TODO
    // utilizar aqui os métodos implementados para adicionar?
    // caso contrário vai ser uma confusão para definir todo o xml
    private val plano = DirectoryElement("plano")
    private var curso = DirectoryElement("curso", plano)
    private val mei = LeafElement("MEI", curso)
    private val fuc1 = DirectoryElement("fuc", plano)
    //private val nomefuc1 = LeafElement("nome", fuc1)
    //private val ectsfuc1 = LeafElement("6.0", fuc1)
    private val avaliacaofuc1 = DirectoryElement("avaliacao", fuc1)
    private var componente1fuc1 = LeafElement("componente", avaliacaofuc1)
    //private val componente2fuc1 = LeafElement("componente", avaliacaofuc1)


    @Test
    fun main() {
        println(mei.parent)
        println(plano.children)
    }

    /**
     * Test adding attribute to element
     */
    @Test
    fun testAddAttribute(){
        var expected : String
        try {
            // TODO Perguntar ao prof como testar os requisitos de inicialização
            /*
            var expected = "testAddAttributeToEntity Error"
            var xml: String = curso.addAttributeToEntity(Attribute("","")).toString()
            assertEquals(expected,xml)
            */
        }catch(e: Throwable){
            println("testAddAttributeToEntity Error Test 1")
            throw e
        }

        try {
            expected = "<curso codigo=\"M4310\">"
            curso.addAttribute(Attribute("codigo","M4310"))
            assertEquals(expected,curso.print())
        }catch(e: Throwable){
            println("testAddAttributeToEntity Error Test 2")
            throw e
        }

        try {
            expected = "<componente nome=\"Quizzes\" peso=\"20%\"/>"
            componente1fuc1.addAttribute(Attribute("nome","Quizzes"))
            componente1fuc1.addAttribute(Attribute("peso","20%"))
            assertEquals(expected,componente1fuc1.print())
        }catch(e: Throwable){
            println("testAddAttributeToEntity Error Test 3")
            throw e
        }

        try {
            expected = "<componente nome=\"Quizzes\" peso=\"20%\"/>"
            componente1fuc1.addAttribute(Attribute("nome","Quizzes"))
            componente1fuc1.addAttribute(Attribute("peso","20%"))
            componente1fuc1.addAttribute(Attribute("peso","40%"))
            //TODO
            // se tentarmos adicionar um atributo com o mesmo nome?
            // atualiza o valor, ignora ou envia uma mensagem de erro?
            assertEquals(expected,componente1fuc1.print())
        }catch(e: Throwable){
            println("testAddAttributeToEntity Error Test 3")
            throw e
        }
    }

    /**
     * Test removing attribute from element
     */
    @Test
    fun testRemoveAttribute(){

        var expected : String

        try {
            expected = "<componente/>"
            componente1fuc1.removeAttribute("nome")
            assertEquals(expected,componente1fuc1.print())
        }catch(e: Throwable){
            println("testRemoveAttribute Error Test 1")
            throw e
        }

        try {
            componente1fuc1.addAttribute(Attribute("nome","Quizzes"))
            expected = "<componente nome=\"Quizzes\"/>"
            componente1fuc1.removeAttribute("peso")
            assertEquals(expected,componente1fuc1.print())
        }catch(e: Throwable){
            println("testRemoveAttribute Error Test 2")
            throw e
        }

        try {
            componente1fuc1.addAttribute(Attribute("nome","Quizzes"))
            expected = "<componente/>"
            componente1fuc1.removeAttribute("nome")
            assertEquals(expected,componente1fuc1.print())
        }catch(e: Throwable){
            println("testRemoveAttribute Error Test 3")
            throw e
        }

        try {
            componente1fuc1.addAttribute(Attribute("nome","Quizzes"))
            componente1fuc1.addAttribute(Attribute("peso","20%"))
            expected = "<componente peso=\"20%\"/>"
            componente1fuc1.removeAttribute("nome")
            assertEquals(expected,componente1fuc1.print())
        }catch(e: Throwable){
            println("testRemoveAttribute Error Test 4")
            throw e
        }

        try {
            expected = "<curso>"
            curso.addAttribute(Attribute("codigo","M4310"))
            curso.removeAttribute("codigo")
            assertEquals(expected,curso.print())
        }catch(e: Throwable){
            println("testRemoveAttribute Error Test 5")
            throw e
        }
    }

    /**
     * Test update attribute from entity
     */
    @Test
    fun testUpdateAttribute(){
        var expected : String

        try {
            expected = "<componente/>"
            componente1fuc1.updateAttribute(Attribute("nome","Quizzes"))
            assertEquals(expected,componente1fuc1.print())
        }catch(e: Throwable){
            println("testUpdateAttribute Error Test 1")
            throw e
        }

        try {
            componente1fuc1.addAttribute(Attribute("nome","Quizzes"))
            expected = "<componente nome=\"Quizz1\"/>"
            componente1fuc1.updateAttribute(Attribute("nome","Quizz1"))
            assertEquals(expected,componente1fuc1.print())
        }catch(e: Throwable){
            println("testUpdateAttribute Error Test 2")
            throw e
        }

        try {
            expected = "<componente nome=\"Quizz1\"/>"
            componente1fuc1.updateAttribute(Attribute("peso","20%"))
            assertEquals(expected,componente1fuc1.print())
        }catch(e: Throwable){
            println("testUpdateAttribute Error Test 3")
            throw e
        }

        try {
            componente1fuc1.addAttribute(Attribute("peso","20%"))
            expected = "<componente nome=\"Projeto\" peso=\"80%\"/>"
            componente1fuc1.updateAttribute(Attribute("nome","Projeto"))
            componente1fuc1.updateAttribute(Attribute("peso","80%"))
            assertEquals(expected,componente1fuc1.print())
        }catch(e: Throwable){
            println("testUpdateAttribute Error Test 4")
            throw e
        }

        try {
            expected = "<fuc codigo=\"M1111\">"
            fuc1.addAttribute(Attribute("codigo","M4310"))
            fuc1.updateAttribute(Attribute("codigo","M1111"))
            assertEquals(expected,fuc1.print())
        }catch(e: Throwable){
            println("testUpdateAttribute Error Test 5")
            throw e
        }
    }

}
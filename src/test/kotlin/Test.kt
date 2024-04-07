import org.junit.jupiter.api.Assertions
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
    val fuc1 = DirectoryElement("fuc", plano)
    private val nomefuc1 = LeafElement("nome", fuc1)
    private val ectsfuc1 = LeafElement("6.0", fuc1)
    val avaliacaofuc1 = DirectoryElement("avaliacao", fuc1)
    private var componente1fuc1 = LeafElement("componente", avaliacaofuc1)
    private val componente2fuc1 = LeafElement("componente", avaliacaofuc1)


    @Test
    fun main() {
        println(mei.parent)
        println(plano.children)
    }

    /**
     * Test adding attribute to entity
     */
    @Test
    fun testAddAttribute(){
        var expected : String
        var xml : String
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
            curso.addAttribute(Attribute("codigo","M4310")).print()
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
    }

    /**
     * Test removing attribute to entity
     */
    @Test
    fun testRemoveAttributeFromEntity(){
        //TODO
        // validar se o método pode ser igual ao das Leafs
        // Se sim, atualizar o nome do método e do teste
    }

    /**
     * Test update attribute from entity
     */
    @Test
    fun testUpdateAttributeFromEntity(){
        //TODO
        // validar se o método pode ser igual ao das Leafs
        // Se sim, atualizar o nome do método e do teste
    }

}
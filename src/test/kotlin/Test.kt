import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/**
 *  This is the class for unitary testing
 */

class Test {

    private val plano = DirectoryElement("plano")
    private val curso = DirectoryElement("curso", plano)
    private val mei = LeafElement("MEI", curso)
    val fuc = DirectoryElement("fuc", plano)

    @Test
    fun main() {
        println(mei.parent)
        println(plano.children)
    }

    /**
     * Test adding attribute to entity
     */
    @Test
    fun testAddAttributeToEntity(){
        //TODO
        // validar se o método pode ser igual ao das Leafs
        // Se sim, atualizar o nome do método e do teste
        try {
            // TODO Perguntar ao prof como testar os requisitos de inicialização
            /*
            var expected = "testAddAttributeToEntity Error"
            var xml: String = curso.addAttributeToEntity(Attribute("","")).toString()
            assertEquals(expected,xml)
            */

            var expected = "<fuc codigo=\"M4310\">"
            // TODO funcao para imprimir a tag completa para testar
            var xml = curso.addAttributeToEntity(Attribute("codigo","")).toString()
            assertEquals(expected,xml)
        }catch(e: Throwable){
            println("testAddAttributeToEntity Error")
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
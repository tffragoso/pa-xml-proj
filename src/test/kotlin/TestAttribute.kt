import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class TestAttribute {

    /**
     * Test valid attribute values
     */
    @Test
    fun testValidAttributeValue() {
        Assertions.assertEquals(false, isValidAttributeValue("\"123kot"))
        Assertions.assertEquals(false, isValidAttributeValue("<123kot"))
        Assertions.assertEquals(false, isValidAttributeValue("X>ML"))
        Assertions.assertEquals(false, isValidAttributeValue("a1&"))
        Assertions.assertEquals(true, isValidAttributeValue(",a123xml"))
        Assertions.assertEquals(true, isValidAttributeValue("a1,23xml"))
        Assertions.assertEquals(true, isValidAttributeValue("_xml"))
        Assertions.assertEquals(true, isValidAttributeValue("nome"))
        Assertions.assertEquals(true, isValidAttributeValue("componente"))
        Assertions.assertEquals(true, isValidAttributeValue(""))
        Assertions.assertEquals(true, isValidAttributeValue("XML"))
    }

    /**
     * Test valid attribute names
     */
    @Test
    fun testValidAttributeName() {
        Assertions.assertEquals(false, isValidAttributeName(""))
        Assertions.assertEquals(false, isValidAttributeName("XML"))
        Assertions.assertEquals(false, isValidAttributeName("\"123kot"))
        Assertions.assertEquals(false, isValidAttributeName("\"123kot"))
        Assertions.assertEquals(false, isValidAttributeName("XML"))
        Assertions.assertEquals(false, isValidAttributeName("a1 2 3 kot\""))
        Assertions.assertEquals(false, isValidAttributeName(",a123xml"))
        Assertions.assertEquals(true, isValidAttributeName("_xml"))
        Assertions.assertEquals(true, isValidAttributeName("nome"))
        Assertions.assertEquals(true, isValidAttributeName("componente"))
    }
}
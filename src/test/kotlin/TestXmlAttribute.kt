import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class TestXmlAttribute {

    /**
     * Test valid attribute names
     */
    @Test
    fun testValidAttributeName() {
        val attributeTest = XmlAttribute("test","")
        Assertions.assertFalse(attributeTest.setName(""))
        Assertions.assertFalse(attributeTest.setName("XML"))
        Assertions.assertFalse(attributeTest.setName("\"123kot"))
        Assertions.assertFalse(attributeTest.setName("XML"))
        Assertions.assertFalse(attributeTest.setName("a1 2 3 kot\""))
        Assertions.assertFalse(attributeTest.setName(",a123xml"))
        Assertions.assertTrue(attributeTest.setName("_xml"))
        Assertions.assertTrue(attributeTest.setName("nome"))
        Assertions.assertTrue(attributeTest.setName("componente"))
    }

    /**
     * Test valid attribute values
     */
    @Test
    fun testValidAttributeValue() {
        val attributeTest = XmlAttribute("test","")
        Assertions.assertFalse(attributeTest.setValue("\"123kot"))
        Assertions.assertFalse(attributeTest.setValue("<123kot"))
        Assertions.assertFalse(attributeTest.setValue("X>ML"))
        Assertions.assertFalse(attributeTest.setValue("a1&"))
        Assertions.assertTrue(attributeTest.setValue(",a123xml"))
        Assertions.assertTrue(attributeTest.setValue("_xml"))
        Assertions.assertTrue(attributeTest.setValue("nome"))
        Assertions.assertTrue(attributeTest.setValue(""))
        Assertions.assertTrue(attributeTest.setValue("componente"))
        Assertions.assertTrue(attributeTest.setValue("XML"))
    }

}
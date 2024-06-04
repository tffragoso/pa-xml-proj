import java.io.File

/**
 * This class implements a XmlDocument.
 * XmlDocument is the class to build xml document type objects.
 * It has a [config] block and [body] block.
 *
 * @property [name] the name of the xml document.
 * @property [body] the body of the xml document; it is a XmlTag.
 * @constructor Creates a XmlDocument with the standard xml header tag as [config].
 */
class XmlDocument(
    val name: String,
    var body : XmlTag
) {

    val config : XmlLeaf
    init {
        config = XmlLeaf("xml")
        config.addAttribute(XmlAttribute("version", "1.0"))
        config.addAttribute(XmlAttribute("encoding", "UTF-8"))
    }

    /**
     * Renames all elements in the XmlDocument [body] to [newName] if the element's name matches the input [elementName].
     * If the input [elementName] does not exist in the [body], this method does not update any elements.
     */
    fun renameElements(elementName: String, newName: String) {
        body.accept {
            if(it.name == elementName )
                it.name = newName
            true
        }
    }

    /**
     * Removes all elements in the XmlDocument [body] whose [name] matches the input [elementName].
     * If the provided [elementName] does not exist in the document, this method does not remove any elements.
     */
    fun removeElements(elementName: String) {
        val elementsToRemove: MutableList<XmlElement> = mutableListOf()
        body.accept {
            if(it.name == elementName )
                elementsToRemove.add(it)
            true
        }
        elementsToRemove.forEach{
            it.parent?.removeChildElement(it)
        }
    }

    /**
     * Adds an attribute with [attributeName] and [attributeValue] to all elements in the XmlDocument
     * whose [name] matches the input [elementName].
     */
    fun addAttributeGlobally(elementName: String, attributeName: String, attributeValue:String) {
        body.accept {
            if (it.name == elementName)
                it.addAttribute(XmlAttribute(attributeName, attributeValue))
            true
        }
    }

    /**
     * Renames the attribute with name [attributeName] to [newAttributeName],
     * in all elements whose [name] matches the input [elementName].
     */
    fun renameAttributeGlobally(elementName: String, attributeName: String, newAttributeName: String) {
        body.accept {
            if(it.name == elementName && it.attributes.isNotEmpty()) {
                it.attributes.forEach() { e ->
                    if (e.getName() == attributeName)
                        e.setName(newAttributeName)
                }
            }
            true
        }
    }

    /**
     * Removes the attribute with name [attributeName] from all
     * the elements whose [name] matches the input [elementName].
     */
    fun removeAttributeGlobally(elementName: String, attributeName: String) {
        body.accept {
            if(it.name==elementName && it.attributes.isNotEmpty()) {
                it.attributes.forEach() { e ->
                    if (e.getName() == attributeName)
                        it.attributes.remove(e)
                }
            }
            true
        }
    }

    /**
     * Lists all elements in the specified element [node] (sub)tree which match the input [xpath].
     * [xpath] is a sequence of element names separated by "/", which form the "path" where the elements live.
     * If the [xpath] is blank, or contains a non-existing element name, the outputed list is empty.
     *
     * @return a list of all elements in the specified [xpath].
     */
    fun microXpath(xpath: String, node: XmlTag): List<XmlElement> {
        val elements: MutableList<XmlElement> = mutableListOf()
        val xpathElements = xpath.split("/")
        node.children.forEach {
            if(it.name == xpathElements[0]) {
                if(xpathElements.size == 1)
                    elements.add(it)
            }
            if(it is XmlTag)
                elements.addAll(microXpath(xpath.substringAfter("/"), it))
        }
        return elements
    }

    /**
     * Writes the XmlDocument contents to a file in a location [outputFilePath] specified by the user.
     * [outputFilePath] should contain the directory and also the file name and extension.
     * The written text is formatted so that it is easy to understand how the entities are nested.
     *
     * @return file with the contents - [config] & [body] - of the XmlDocument.
     */
    fun prettyPrint(outputFilePath: String) {

        val file = File(outputFilePath)

        fun auxPrint(element: XmlElement): String {
            var auxOutput = element.elementToString()
            if(element is XmlTag) {
                element.children.forEach {
                    auxOutput += "\n" + "\t".repeat(it.depth) + auxPrint(it)
                }
                if(element.parent == null)
                    auxOutput += "\n</" + element.name + ">"
                else
                    auxOutput += "\n" + "\t".repeat(element.depth) + "</" + element.name + ">"
            }
            return auxOutput
        }

        var output = config.elementToString()
        output += "\n" + auxPrint(body)
        file.writeText(output)

        println("Text written successfully to file in specified path.")
    }
}


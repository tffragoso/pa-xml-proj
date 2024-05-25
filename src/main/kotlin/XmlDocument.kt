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
     * Renames all XmlElements in the document to newName,
     * if the element's name matches the input elementName.
     * If the provided elementName does not exist in the document, this method does not update any elements.
     */
    fun renameElements(elementName: String, newName: String) {
        body.accept {
            if(it.name == elementName )
                it.name = newName
            true
        }
    }

    /**
     * Removes all XmlElements in the document whose name matches the input elementName.
     * If the provided elementName does not exist in the document, nothing is removed.
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
     * Adds an attribute with [attributeName] and [attributeValue]
     * to all [XmlElement] with name equal to [elementName].
     */
    fun addAttributeGlobally(elementName: String, attributeName: String, attributeValue:String) {
        body.accept {
            if (it.name == elementName)
                it.addAttribute(XmlAttribute(attributeName, attributeValue))
            true
        }
    }

    /**
     * Updates the name of the attributes [attributeName] of the elements [elementName]
     * with the new name [newAttributeName]
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
     * Removes all the attributes with name [attributeName] of the elements [elementName]
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

    // To Do : escrever para ficheiro xml, nao devolver string nem print
    fun prettyPrint(): String {

        fun auxPrint(element: XmlElement): String {
            var auxOutput = element.elementToString()
            if(element is XmlTag) {
                element.children.forEach {
                    auxOutput += "\n" + auxPrint(it)
                }
                auxOutput += "\n</" + element.name + ">"
            }
            return auxOutput
        }

        var output = config.elementToString()
        output += "\n" + auxPrint(body)

        return output
    }

}


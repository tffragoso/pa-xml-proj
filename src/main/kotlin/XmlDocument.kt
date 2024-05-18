class XmlDocument(
    val name: String,
    var body : XmlTag
) {

    val config : XmlLeaf
    init {
        config = XmlLeaf("xml")
        config.addAttribute(Attribute("version", "1.0"))
        config.addAttribute(Attribute("encoding", "UTF-8"))
    }

    /**
     * Given a XmlElement, this function renames all entities in that XmlElement's tree
     * to newName, if their name matches the input elementName
     */
    fun renameElements(elementName: String, newName: String) {
        body.accept {
            if(it.name == elementName )
                it.name = newName
            true
        }
    }

    /**
     *
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

}


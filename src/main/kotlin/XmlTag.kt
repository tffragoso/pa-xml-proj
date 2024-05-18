data class XmlTag(
    override var name: String,
    override var parent: XmlTag? = null,
    //TODO
    // private val attributes
    override var attributes: MutableList<Attribute> = mutableListOf()
) : XmlElement {

    val children: MutableList<XmlElement> = mutableListOf()

    init {
        parent?.children?.add(this) //this chain returns null if any of the properties is null
    }

    /*
    * Adds a XmlElement as child of a XmlElement.
    * If the child already has a parent, the new parent is assigned as parent of the child.
    * */
    fun addChildElement(child: XmlElement) {
        // Add child to the list of children
        this.children.add(child)
        // Remove child from previous parent's list of children
        child.parent?.children?.remove(child)
        // Assign new parent to child
        child.parent = this
    }

    /*
    * Removes a XmlElement as child of a XmlElement.
    * The child is assigned no parent.
    * */
    fun removeChildElement(child: XmlElement) {
        // Remove child from the list of children
        this.children.remove(child)
        // Make child's parent null
        child.parent = null
    }

    override fun accept(visitor: (XmlElement) -> Boolean) {
        if (visitor(this))
            children.forEach {
                it.accept(visitor)
            }
    }

    // TO DO: change method name since nothing is being printed
    /**
     * return a String with XmlElement and attributes
     * @return [String]
     */
    fun print(): String {
        var dirString = "<"
        dirString += this.name
        if (this.attributes.isNotEmpty()) {
            val attributesString = attributes.joinToString(separator = " ") { "${it.getName()}=\"${it.getValue()}\"" }
            dirString += " $attributesString"
        }
        dirString += ">"
        return dirString
    }

    // To Do : escrever para ficheiro xml, nao devolver string nem print
    fun prettyPrint(): String {
        var dirString = ""
        if (this.parent == null) {
            dirString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
        }
        dirString += "<"
        dirString += this.name
        if (this.attributes.isNotEmpty()) {
            val attributesString = attributes.joinToString(separator = " ") { "${it.getName()}=\"${it.getValue()}\"" }
            dirString += " $attributesString"
        }
        dirString += ">"
        this.children.forEach { e ->
            if (e is XmlLeaf)
                dirString += e.print()
            else if (e is XmlTag)
                dirString += e.prettyPrint()
        }
        dirString += "</" + this.name + ">"
        return dirString
    }

    //TO DO : mover para XMLDOcument
    fun microXpath(xpath: String): List<XmlElement> {
        val elements: MutableList<XmlElement> = mutableListOf()
        val xpathElements = xpath.split("/")
        this.children.forEach {
            if(it.name == xpathElements[0]) {
                if(xpathElements.size == 1)
                    elements.add(it)
            }
            if(it is XmlTag)
                elements.addAll(it.microXpath(xpath.substringAfter("/")))
            }
        return elements
    }
}

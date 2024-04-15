data class XmlTag(
    override var name: String,
    override var parent: XmlTag? = null,
    override var attributes : MutableList<Attribute>? = null
) : XmlElement {

    val children: MutableList<XmlElement> = mutableListOf()
    init {
        parent?.children?.add(this) //this chain returns null if any of the properties is null
    }

    /*
    * Adds a XmlElement as child of a XmlElement.
    * If the child already has a parent, this new parent is assigned to the child
    * */
    fun addChildElement(child: XmlElement) {
        // Add child to the list of children
        this.children.add(child)
        // Remove child from previous parent list of children
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
            children.forEach{
                it.accept(visitor)
            }
    }

    /**
     * return a String with XmlElement and attributes
     * @return [String]
     */
    fun print(): String{
        var dirString = "<"
        dirString += this.name
        if(!this.attributes.isNullOrEmpty()){
            val attributesString = attributes!!.joinToString(separator = " ") { "${it.name}=\"${it.value}\"" }
            dirString += " $attributesString"
        }
        dirString += ">"
        return dirString
    }

    fun prettyPrint():String {
        var dirString = ""
        if (this.parent == null) {
            dirString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
        }
        dirString += "<"
        dirString += this.name
        if (!this.attributes.isNullOrEmpty()) {
             val attributesString = attributes!!.joinToString(separator = " ") { "${it.name}=\"${it.value}\"" }
             dirString += " $attributesString"
        }
        dirString += ">"
        this.children.forEach { e ->
           if (e is XmlLeaf)
               dirString += e.print()
           else if (e is XmlTag)
               dirString += e.prettyPrint()
           }
        dirString +="</" + this.name + ">"
        return dirString
    }

}
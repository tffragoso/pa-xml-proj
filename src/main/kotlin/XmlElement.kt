sealed interface XmlElement {
    var name: String
    var parent: XmlTag?
    var attributes: MutableList<Attribute>?

    /**
     * Adds an [attribute] to a [XmlElement].
     * @return [XmlElement]
     */
    fun addAttribute(attribute: Attribute): XmlElement{
        if (this.attributes == null) {
            this.attributes = mutableListOf(attribute)
        } else if (attribute.name !in this.attributes!!.map{it.name}){
            attributes!!.add(attribute)
        }
        return this
    }
    /**
     * Adds an attribute with [attributeName] and [attributeValue] to a [XmlElement].
     * @return [XmlElement]
     */
    fun addAtributeGlobally(elementName: String, attributeName: String,attributeValue:String): XmlElement {
        accept {
            if (it.name == elementName)
                it.addAttribute(Attribute(attributeName, attributeValue))
            true
        }
        return this
    }
    /**
     * Removes the attribute with the [name] from an [XmlElement].
     * @return [XmlElement]
     */
    fun removeAttribute(name: String): XmlElement{
        val i = this.attributes?.iterator()
        while (i?.hasNext() == true) {
            val attribute = i.next()
            if (attribute.name == name) {
                i.remove()
                break
            }
        }
        return this
    }

    /**
     * Updates [attribute] with the [attribute] value.
     * @return [XmlElement]
     */
    fun updateAttribute(attribute: Attribute): XmlElement{
        this.attributes?.forEach { e ->
            if(e.name == attribute.name)
                e.value = attribute.value
        }
        return this
    }

    fun accept(visitor: (XmlElement) -> Boolean) {
        visitor(this)
    }

}

/**
 * Return a list of distinct names of all elements in the XmlElement´s tree.
 * This list includes the XmlElement's name.
 */
fun XmlElement.listDistinctElementNames(): MutableSet<String>  {
    val distinctElementNames: MutableSet<String> = mutableSetOf()
    accept {
        distinctElementNames.add(it.name)
        true
    }
    return distinctElementNames
}

/**
 * Given a XmlElement, this function renames all entities in that XmlElement's tree
 * to newName, if their name matches the input elementName
 */
fun XmlElement.renameElements(elementName: String, newName: String) {
    accept {
        if(it.name == elementName )
            it.name = newName
        true
    }
}
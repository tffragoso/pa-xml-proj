sealed interface XmlElement {
    var name: String
    var parent: XmlTag?
    //TODO
    //private val attributes
    var attributes: MutableList<Attribute>

    /**
     * Adds an [attribute] to a [XmlElement].
     * if the attribute exists the element maintains the same
     * if the attribute doesn't exist then the attribute is added
     */
    fun addAttribute(attribute: Attribute) {
        if (attribute.getName() !in this.attributes.map{it.getName()}) {
            attributes.add(attribute)
        }
    }
    /**
     * Removes the attribute with the [name] from an [XmlElement].
     * if the attribute exists then is deleted
     * if the attribute doesn't exist the object maintains the same
     */
    fun removeAttribute(name: String) {
        val i = this.attributes.iterator()
        while (i.hasNext()) {
            val attribute = i.next()
            if (attribute.getName() == name) {
                i.remove()
            }
        }
    }

    /**
     * Updates [attribute] with the [attribute] value.
     * if the attribute exists then the value is updated
     * if the attribute doesn't exist the object maintains the same
     */
    fun updateAttribute(attribute: Attribute) {
        this.attributes.forEach { e ->
            if(e.getName() == attribute.getName()) {
                e.setValue(attribute.getValue())
            }
        }
    }

    //TODO
    // change return of the next method to boolean and review comment
    // mover para XMLDOcument
    /**
     * Adds an attribute with [attributeName] and [attributeValue] to a [XmlElement].
     * @return [XmlElement]
     */
    fun addAttributeGlobally(elementName: String, attributeName: String,attributeValue:String): XmlElement {
        accept {
            if (it.name == elementName)
                it.addAttribute(Attribute(attributeName, attributeValue))
            true
        }
        return this
    }

    //TODO
    // change return of the next method to boolean and review comment
    // mover para XMLDOcument
    /**
     * Updates the name of the attributes [attributeName] of the element [elementName]
     * with the new name [newAttributeName]
     */
    fun renameAttributeGlobally(elementName: String,attributeName: String,newAttributeName:String): XmlElement {
        accept {
            if(it.name==elementName && it.attributes.isNotEmpty()) {
                it.attributes.forEach() { e ->
                    if (e.getName() == attributeName)
                        e.setName(newAttributeName)
                }
            }
            true
        }
        return this
    }

    //TODO
    // change return of the next method to boolean and review comment
    // mover para XMLDOcument
    /**
     * Removes all the attributes with name [attributeName] of the element [elementName]
     */

    fun removeAttributeGlobally(elementName: String,attributeName: String):XmlElement{
        accept {
            if(it.name==elementName && it.attributes.isNotEmpty()) {
                it.attributes.forEach() { e ->
                    if (e.getName() == attributeName)
                        it.attributes.remove(e)
                }
            }
            true
        }
        return this
    }

    fun accept(visitor: (XmlElement) -> Boolean) {
        visitor(this)
    }

}

// mover para dentro do XMLelement
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
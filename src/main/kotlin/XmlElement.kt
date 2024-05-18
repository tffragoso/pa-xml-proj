sealed interface XmlElement {
    var name: String
    var parent: XmlTag?
    //TODO
    //private val attributes
    var attributes: MutableList<Attribute>

    /**
     * Adds an [attribute] to a [XmlElement].
     * if the attribute exists the element maintains the same and the method returns false
     * if the attribute doesn't exist then the attribute is added and the method return true
     */
    fun addAttribute(attribute: Attribute): Boolean {
        if (attribute.getName() !in this.attributes.map{it.getName()}) {
            attributes.add(attribute)
            return true
        } else
            return false
    }
    /**
     * Removes the attribute with the [name] from an [XmlElement].
     * if the attribute exists then is deleted and the method returns true
     * if the attribute doesn't exist then the method returns false
     */
    fun removeAttribute(name: String): Boolean {
        val i = this.attributes.iterator()
        while (i.hasNext()) {
            val attribute = i.next()
            if (attribute.getName() == name) {
                i.remove()
                return true
            }
        }
        return false
    }

    /**
     * Updates [attribute] with the [attribute] value.
     * if the attribute exists then the value is updated and the method returns true
     * if the attribute doesn't exist then the method returns false
     */
    fun updateAttribute(attribute: Attribute): Boolean {
        var updated = false
        this.attributes.forEach { e ->
            if(e.getName() == attribute.getName()) {
                e.setValue(attribute.getValue())
                updated = true
            }
        }
        return updated
    }

    //TODO
    // change return of the next method to boolean and review comment
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

/**
 *
 *
 */
fun XmlElement.removeElements(elementName: String) {
    val elementsToRemove: MutableList<XmlElement> = mutableListOf()
    accept {
        if(it.name == elementName )
            elementsToRemove.add(it)
        true
    }
    elementsToRemove.forEach{
        it.parent?.removeChildElement(it)
    }
}
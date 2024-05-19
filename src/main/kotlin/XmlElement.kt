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

    /**
     * Return a list of distinct names of all elements in the XmlElement´s tree.
     * This list includes the XmlElement's name.
     */
    fun listDistinctElementNames(): MutableSet<String>  {
        val distinctElementNames: MutableSet<String> = mutableSetOf()
        accept {
            distinctElementNames.add(it.name)
            true
        }
        return distinctElementNames
    }

    fun accept(visitor: (XmlElement) -> Boolean) {
        visitor(this)
    }

}
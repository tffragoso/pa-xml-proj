/**
 * This class implements a XmlElement.
 * XmlElement is an abstract interface to represent the elements of a XML file.
 * XmlElement has the variants XmlTag and XmlLeaf, which are defined in respective classes.
 *
 * @constructor Creates a XmlElement with a valid, non-null, [name].
 */
sealed interface XmlElement {
    var name: String
    var parent: XmlTag?
    var attributes: MutableList<XmlAttribute>

    /*
     * A numeric value representing the depth of the element in the Xml tree.
     * A parent-less element has depth 0.
     */
    val depth: Int
        get() = if(parent == null)
            0
        else
            1 + parent!!.depth

    /*
     * Method to accept visitors.
     */
    fun accept(visitor: (XmlElement) -> Boolean) {
        visitor(this)
    }

    /**
     * Adds an [attribute] to a XmlElement.
     * If the attribute already exists, it is not updated.
     * If the attribute doesn't exist, then it is added to the element's [attributes] list.
     */
    fun addAttribute(attribute: XmlAttribute) {
        if (attribute.getName() !in this.attributes.map{it.getName()}) {
            attributes.add(attribute)
        }
    }

    /**
     * Removes an [attribute] with the [name] from a XmlElement.
     * If the attribute exists, then it is removed from the element's [attributes] list.
     * If the attribute doesn't exist, the element is not updated.
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
     * Updates a XmlElement [attribute] with the new provided value.
     * If the attribute exists, then its value is updated.
     * If the attribute doesn't exist, the element is not updated.
     */
    fun updateAttribute(attribute: XmlAttribute) {
        this.attributes.forEach { e ->
            if(e.getName() == attribute.getName()) {
                e.setValue(attribute.getValue())
            }
        }
    }

    /**
     * Lists all distinct element names in the XmlElement´s tree.
     * This list includes the XmlElement's name.
     *
     * @return a list with distinct names of all elements in the xml (sub)tree.
     */
    fun listDistinctElementNames(): MutableSet<String>  {
        val distinctElementNames: MutableSet<String> = mutableSetOf()
        accept {
            distinctElementNames.add(it.name)
            true
        }
        return distinctElementNames
    }

    /**
     * Outputs the XmlElement in a readable xml entity string.
     *
     * @return string representation of the xml element.
     */
    fun elementToString(): String {
        // Tag opening and name
        var output = "<"

        output += if(this.name == "xml")
            "?" + this.name
        else
            this.name

        // Adding in the attributes, if any exist
        if(this.attributes.isNotEmpty()) {
            val attributesString = attributes.joinToString(separator = " ") { "${it.getName()}=\"${it.getValue()}\"" }
            output += " $attributesString"
        }

        // Tag closing
        if(this is XmlTag)
            output += ">"
        else if(this is XmlLeaf)
            if(this.name == "xml")
                output += "?>"
            else if(this.leafText.isNullOrEmpty())
                output += "/>"
            else
                output += ">" + this.leafText + "</" + this.name + ">"

        return output
    }
}

/**
 * Checks if XmlElement's [name] is valid.
 * Valid [name] can't be null, and can only contain letters, digits, underscores, hyphens and periods.
 *
 * @return True if [name] is valid, False otherwise.
 */
fun isValidElementName(name: String): Boolean {
    return name.matches(Regex("^[A-Za-z_][A-Za-z0-9-_.]*\$"))
}

//REMOVER ANTES DE ENTREGAR
fun printTag(element: XmlElement): String {
    var auxOutput = element.elementToString()
    if(element is XmlTag) {
        element.children.forEach {
            auxOutput += printTag(it)
        }
        auxOutput += "</" + element.name + ">"
    }
    return auxOutput
}
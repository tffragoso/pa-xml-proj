/**
 * This class implements a XmlAttribute.
 * XmlAttribute can be added to all XmlElement's (so both XmlTag and XmlLeaf).
 *
 * @property [name] the name of the attribute.
 * @property [value] the value of the attribute.
 * @constructor Creates an attribute with valid, non-null, [name].
 */
class XmlAttribute(
    private var name: String,
    private var value: String
){
    init {
        require(this.isValidAttributeName(this.name)
                && this.isValidAttributeValue(this.value)) { "Not a valid name" }
    }

    fun getName() = this.name
    fun getValue() = this.value

    /**
     * Sets the name for the attribute.
     * If [newName] is a valid name, the property [name] is updated with [newName].
     * If [newName] is NOT a valid name, the attribute is not updated.
     *
     * @return True if [name] was updated, False otherwise.
     */
    fun setName(newName:String): Boolean {
        if(isValidAttributeName(newName)) {
            this.name = newName
            return true
        } else
            return false
    }

    /**
     * Sets the value for the attribute.
     * If [newValue] is a valid value, the property [value] is updated with [newValue].
     * If [newValue] is NOT a valid value, the attribute is not updated.
     *
     * @return True if [value] was updated, False otherwise.
     */
    fun setValue(newValue:String): Boolean {
        if(isValidAttributeValue(newValue)) {
            this.value = newValue
            return true
        }
        else
            return false
    }

    /**
     * Checks if XmlAttribute's [name] is valid.
     * Valid [name] can't be null, and can only contain letters, digits, underscores, hyphens and periods.
     * Must start with a letter, underscore, or period, and can´t start with sequence of characters 'xml'.
     *
     * @return True if [name] is valid, False otherwise.
     */
    private fun isValidAttributeName(newName:String): Boolean {
        return newName.matches(
            Regex("^(?=.+)(?!xml|Xml|xMl|xmL|XMl|xML|XmL|XML)[A-Za-z_][A-Za-z0-9-_.]*\$"))
    }

    /**
     * Checks if XmlAttribute's [value] is valid.
     * Valid [value] can't be null, and can´t contain the lesser than/greater than characters that open and close XML entities.
     *
     * @return True if [value] is valid, False otherwise.
     */
    private fun isValidAttributeValue(newValue:String): Boolean {
        return newValue.matches(Regex("[^\"<>&]*"))
    }
}


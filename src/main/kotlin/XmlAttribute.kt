/**
 * This class defines an attribute
 *
 * @property [name] the name of the attribute.
 * @property [value] the value of the attribute.
 * @constructor Creates an attribute with name not empty.
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
     * if [newName] is a valid name for an XML entity
     * the property [name] is updated with [newName] and returns true
     * if [newName] is NOT a valid name for an XML entity the method returns false
     */
    fun setName(newName:String): Boolean {
        if(isValidAttributeName(newName)) {
            this.name = newName
            return true
        } else
            return false
    }
    /**
     * if [newValue] is a valid value for an attribute for an XML entity
     * the property [value] is updated with [newValue] and returns true
     * if [newValue] is NOT a valid name for an XML entity the method returns false
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
     * XML attributes can contain letters, digits, underscores, hyphens and periods
     * must start with a letter, underscore, or colon
     * cannot start with sequence of characters 'xml' and cannot contain spaces
     */
    private fun isValidAttributeName(newName:String): Boolean {
        return newName.matches(
            Regex("^(?=.+)(?!xml|Xml|xMl|xmL|XMl|xML|XmL|XML)[A-Za-z_][A-Za-z0-9-_.]*\$"))
    }

    private fun isValidAttributeValue(newValue:String): Boolean {
        return newValue.matches(Regex("[^\"<>&]*"))
    }
}


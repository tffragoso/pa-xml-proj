/**
 * This class defines an attribute
 *
 * @property [name] the name of the attribute.
 * @property [value] the value of the attribute.
 * @constructor Creates an attribute with name not empty.
 */
class Attribute(
    var name:String,
    var value:String
){
    init {
        require(isValidAttributeName(this.name)) { "Not valid name" }
    }
}
/**
 * XML attributes can contain letters, digits, underscores, hyphens and periods
 * must start with a letter, underscore, or colon
 * cannot star with letters xml and cannot contain spaces
 */
fun isValidAttributeName(name:String): Boolean{
    return name.matches(Regex("^(?!xml|Xml|xMl|xmL|XMl|xML|XmL|XML)[A-Za-z_][A-Za-z0-9-_.]*\$"))
}
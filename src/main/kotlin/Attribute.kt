/**
 * This class defines an attribute
 *
 * @property [name] the name of the attribute.
 * @property [value] the value of the attribute.
 * @constructor Creates an attribute with name not empty.
 */
class Attribute(
    val name:String,
    var value:String
){
    init {
        require(name.isNotEmpty()) { "Name required" }
    }
}


sealed interface Element {
    val name: String
    val parent: DirectoryElement?
    val attributes : MutableList<Attribute>?
}

// TODO
// Deveremos chamar esta classe de Entity?
data class DirectoryElement(
    override val name: String,
    override val parent: DirectoryElement? = null,
    override val attributes : MutableList<Attribute>? = null
) : Element {

    val children: MutableList<Element> = mutableListOf()

    init {
        parent?.children?.add(this) //this chain returns null if any of the properties is null
    }

    /**
     * Adds an [attribute] to a [DirectoryElement].
     * @return [DirectoryElement]
     */
    fun addAttributeToEntity(attribute: Attribute): DirectoryElement{
        return this
    }

    // fun changeAttribute(n: String, v: Any) // To Do

    // fun removeAttribute(n: String, v: Any) // To Do
}

data class LeafElement(
    override val name: String,
    // val leafType: String, //leafType pode ser tag (ex <componente>) ou text (ex "Programacao Avanacada")
    override val parent: DirectoryElement?,
    override val attributes : MutableList<Attribute>? = null
) : Element {

    init {
        parent?.children?.add(this) //this chain returns null if any of the properties is null
    }

    //funçoes semelhantes às da DirectoryElement, mas aqui temos de ter em atençao o leafType
    // se leaftType for tag, entao as funcoes sao validas, se for text entao nao podem ser aplicadas as funcoes
    // fun addAttribute(n: String, v: Any) // To Do

    // fun changeAttribute(n: String, v: Any) // To Do

    // fun removeAttribute(n: String, v: Any) // To Do

}

/**
 * This class defines an attribute
 *
 * @property [name] the name of the attribute.
 * @property [value] the value of the attribute.
 * @constructor Creates an attribute with name not empty.
 */
public class Attribute(
    private val name:String,
    private val value:String
){
    init {
        require(name.isNotEmpty()) { "name required" }
    }
}
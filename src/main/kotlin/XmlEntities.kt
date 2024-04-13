

sealed interface XmlElement {
    val name: String
    val parent: XmlTag?
    var attributes : MutableSet<Attribute>? //o prof na aula disse que devia ser MutableList ou percebi mal?

    /**
     * Adds an [attribute] to an [XmlElement].
     * @return [XmlElement]
     */
    fun addAttribute(attribute: Attribute): XmlElement{
        if (this.attributes == null) {
            this.attributes = mutableSetOf(attribute)
        } else if (attribute.name !in this.attributes!!.map{it.name}){
            attributes!!.add(attribute)
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
        this.attributes?.map{
            if(it.name == attribute.name)
                it.value=attribute.value
        }
        return this
    }

    /**
     * return a String with XmlElement and attributes
     * @return [String]
     */
    fun print(): String{
        var dirString = "<"
        dirString += this.name
        if(!this.attributes.isNullOrEmpty()){
            val attributesString = attributes!!.joinToString(separator = " ") { "${it.name}=\"${it.value}\"" }
            dirString += " $attributesString"
        }
        dirString += if(this is XmlLeaf)
            "/>"
        else
            ">"
        return dirString
    }
}

data class XmlTag(
    override val name: String,
    override val parent: XmlTag? = null,
    override var attributes : MutableSet<Attribute>? = null
) : XmlElement {

    val children: MutableList<XmlElement> = mutableListOf()

    init {
        parent?.children?.add(this) //this chain returns null if any of the properties is null
    }

    // fun changeAttribute(n: String, v: Any) // To Do

    // fun removeAttribute(n: String, v: Any) // To Do
}

data class XmlLeaf(
    override val name: String,
    // TODO
    // Acho que se deve utilizar esta propriedade, então atualizar os métodos
    // val leafType: String, //leafType pode ser tag (ex <componente>) ou text (ex "Programacao Avanacada")
    override val parent: XmlTag?,
    override var attributes : MutableSet<Attribute>? = null
) : XmlElement {

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
class Attribute(
    val name:String,
    var value:String
){
    init {
        require(name.isNotEmpty()) { "Name required" }
    }
}
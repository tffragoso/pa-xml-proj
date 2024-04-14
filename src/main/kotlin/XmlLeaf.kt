data class XmlLeaf(
    override var name: String,
    // TODO
    // Acho que se deve utilizar esta propriedade, então atualizar os métodos
    // val leafType: String, //leafType pode ser tag (ex <componente>) ou text (ex "Programacao Avanacada")
    override var parent: XmlTag? = null,
    override var attributes : MutableSet<Attribute>? = null
) : XmlElement {

    init {
        parent?.children?.add(this)
    }

    //funçoes semelhantes às da DirectoryElement, mas aqui temos de ter em atençao o leafType
    // se leaftType for tag, entao as funcoes sao validas, se for text entao nao podem ser aplicadas as funcoes
    // fun addAttribute(n: String, v: Any) // To Do

    // fun changeAttribute(n: String, v: Any) // To Do

    // fun removeAttribute(n: String, v: Any) // To Do

}
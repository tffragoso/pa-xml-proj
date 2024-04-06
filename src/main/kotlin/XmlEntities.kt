

sealed interface Element {
    val name: String
    val parent: DirectoryElement?
}

data class DirectoryElement(
    override val name: String,
    override val parent: DirectoryElement? = null
) : Element {

    val children: MutableList<Element> = mutableListOf()

    init {
        parent?.children?.add(this) //this chain returns null if any of the properties is null
    }

    // fun addAttribute(n: String, v: Any) // To Do

    // fun changeAttribute(n: String, v: Any) // To Do

    // fun removeAttribute(n: String, v: Any) // To Do
}

data class LeafElement(
    override val name: String,
    // val leafType: String, //leafType pode ser tag (ex <componente>) ou text (ex "Programacao Avanacada")
    override val parent: DirectoryElement?
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


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

    //To Do
}

data class LeafElement(
    override val name: String,
    // val leafType: String,
    override val parent: DirectoryElement?
) : Element {

    init {
        parent?.children?.add(this) //this chain returns null if any of the properties is null
    }

    //To Do

}
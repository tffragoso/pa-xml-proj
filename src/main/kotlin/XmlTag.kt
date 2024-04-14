data class XmlTag(
    override var name: String,
    override var parent: XmlTag? = null,
    override var attributes : MutableSet<Attribute>? = null
) : XmlElement {

    val children: MutableList<XmlElement> = mutableListOf()
    init {
        parent?.children?.add(this) //this chain returns null if any of the properties is null
    }

    /*
    * Adds a XmlElement as child of a XmlElement.
    * If the child already has a parent, this new parent is assigned to the child
    * */
    fun addChildElement(child: XmlElement) {
        // Add child to the list of children
        this.children.add(child)
        // Remove child from previous parent list of children
        child.parent?.children?.remove(child)
        // Assign new parent to child
        child.parent = this
    }

    /*
    * Removes a XmlElement as child of a XmlElement.
    * The child is assigned no parent.
    * */
    fun removeChildElement(child: XmlElement) {
        // Remove child from the list of children
        this.children.remove(child)
        // Make child's parent null
        child.parent = null
    }

    override fun accept(visitor: (XmlElement) -> Boolean) {
        if (visitor(this))
            children.forEach{
                it.accept(visitor)
            }
    }

    // fun changeAttribute(n: String, v: Any) // To Do

    // fun removeAttribute(n: String, v: Any) // To Do
}
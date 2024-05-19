data class XmlTag(
    override var name: String,
    override var parent: XmlTag? = null,
    //TODO
    // private val attributes
    override var attributes: MutableList<Attribute> = mutableListOf()
) : XmlElement {

    val children: MutableList<XmlElement> = mutableListOf()

    init {
        parent?.children?.add(this) //this chain returns null if any of the properties is null
    }

    /*
    * Adds a XmlElement as child of a XmlElement.
    * If the child already has a parent, the new parent is assigned as parent of the child.
    * */
    fun addChildElement(child: XmlElement) {
        // Add child to the list of children
        this.children.add(child)
        // Remove child from previous parent's list of children
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
            children.forEach {
                it.accept(visitor)
            }
    }

}

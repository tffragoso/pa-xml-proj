data class XmlLeaf(
    override var name: String,
    override var parent: XmlTag? = null,
    override var attributes : MutableSet<Attribute>? = null,
    val leafText: String? = null,
) : XmlElement {

    init {
        parent?.children?.add(this)
    }

}
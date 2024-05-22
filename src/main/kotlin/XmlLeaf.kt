data class XmlLeaf(
    override var name: String,
    override var parent: XmlTag? = null,
    //TODO
    //private val attributes
    override var attributes: MutableList<XmlAttribute> = mutableListOf(),
    val leafText: String? = null,
) : XmlElement {

    init {
        parent?.children?.add(this)
    }

}
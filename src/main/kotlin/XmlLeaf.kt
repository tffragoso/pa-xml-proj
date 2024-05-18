data class XmlLeaf(
    override var name: String,
    override var parent: XmlTag? = null,
    //TODO
    //private val attributes
    override var attributes: MutableList<Attribute> = mutableListOf(),
    val leafText: String? = null,
) : XmlElement {

    init {
        parent?.children?.add(this)
    }

    // TO DO: change method name since nothing is being printed
    /**
     * return a String with XmlElement and attributes
     * just prints a line
     * @return [String]
     */
    fun print(): String {
        var dirString = "<"
        dirString += this.name
        if(this.attributes.isNotEmpty()){
            val attributesString = attributes.joinToString(separator = " ") { "${it.getName()}=\"${it.getValue()}\"" }
            dirString += " $attributesString"
        }
        dirString += if(this.leafText == null)
            "/>"
            else if(this.leafText.isEmpty())
                "/>"
            else
                ">"+this.leafText+"</"+this.name+">"
        return dirString
    }

}
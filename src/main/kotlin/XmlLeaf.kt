data class XmlLeaf(
    override var name: String,
    override var parent: XmlTag? = null,
    override var attributes : MutableList<Attribute>? = null,
    val leafText: String? = null,
) : XmlElement {

    init {
        parent?.children?.add(this)
    }

    /**
     * return a String with XmlElement and attributes
     * @return [String]
     */
    fun print(): String{
        var dirString = "<"
        dirString += this.name
        if(!this.attributes.isNullOrEmpty()){
            val attributesString = attributes!!.joinToString(separator = " ") { "${it.getName()}=\"${it.getValue()}\"" }
            dirString += " $attributesString"
        }
        dirString += if(this.leafText== null)
            "/>"
        else if(this.leafText.isEmpty())
                "/>"
        else
            ">"+this.leafText+"</"+this.name+">"
        return dirString
    }

}
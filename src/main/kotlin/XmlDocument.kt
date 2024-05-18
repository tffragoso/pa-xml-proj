class XmlDocument(

    val name: String,
    var config : XmlLeaf,
    var body : XmlTag
) {

    init {
        config = XmlLeaf("xml")
        config.addAttribute(Attribute("version", "1.0"))
        config.addAttribute(Attribute("encoding", "UTF-8"))
    }

}
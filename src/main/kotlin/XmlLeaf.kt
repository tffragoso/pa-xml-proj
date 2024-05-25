import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.hasAnnotation

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

fun mapXmlLeaf(obj: Any): XmlLeaf {
    val objClass = obj::class
    var leafObject = XmlLeaf(objClass.simpleName!!.lowercase(),null,mutableListOf())

    objClass.declaredMemberProperties.forEach {
        if (it.hasAnnotation<Attribute>()){
            val xmlStringAnnotation = it.findAnnotation<XmlString>()
            var value = it.call(obj).toString()
            if (xmlStringAnnotation != null) {
                val processorClass = xmlStringAnnotation.value
                val processorInstance = processorClass.java.getDeclaredConstructor().newInstance()
                value =
                    processorClass.java.getMethod("addPercentage", String::class.java).invoke(processorInstance, value)
                        .toString()
            }
            leafObject.attributes.add(XmlAttribute(it.name, value))
        }
    }
    return leafObject
}
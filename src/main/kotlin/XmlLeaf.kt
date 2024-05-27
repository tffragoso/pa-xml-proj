import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.hasAnnotation

data class XmlLeaf(
    override var name: String,
    override var parent: XmlTag? = null,
    override var attributes: MutableList<XmlAttribute> = mutableListOf(),
    val leafText: String? = null,
) : XmlElement {

    init {
        parent?.children?.add(this)
    }

}

fun mapXmlLeaf(obj: Any): XmlLeaf {
    val objClass = obj::class

    //change the tag name as required by the user
    var className = objClass.simpleName!!.lowercase()
    val xmlAdapterAnnotation = objClass.findAnnotation<XmlAdapter>()
    if (xmlAdapterAnnotation != null) {
        val processorClass = xmlAdapterAnnotation.value
        val processorInstance = processorClass.java.getDeclaredConstructor().newInstance()
        val newTagName = xmlAdapterAnnotation.newName
        var newFunction = xmlAdapterAnnotation.function
        className =
            processorClass.java.getMethod(newFunction, String::class.java).invoke(processorInstance,newTagName)
                .toString()
    }

    var leafObject = XmlLeaf(className,null,mutableListOf())

    objClass.declaredMemberProperties.forEach {
        if (it.hasAnnotation<Attribute>()){
            val xmlStringAnnotation = it.findAnnotation<XmlString>()
            var value = it.call(obj).toString()
            if (xmlStringAnnotation != null) {
                val processorClass = xmlStringAnnotation.value
                val processorInstance = processorClass.java.getDeclaredConstructor().newInstance()
                value =
                    processorClass.java.getMethod("addPercentage",
                        String::class.java).invoke(processorInstance, value)
                        .toString()
            }
            leafObject.attributes.add(XmlAttribute(it.name, value))
        }
    }
    return leafObject
}
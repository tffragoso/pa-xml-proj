import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.hasAnnotation
import kotlin.reflect.full.primaryConstructor

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
            var newvalue = ""
            if (xmlStringAnnotation != null) {
                val processorClass = xmlStringAnnotation.value
                val processorInstance = processorClass.primaryConstructor?.call()
                var metodo = processorClass.members.first { a -> a.name == "addPercentage" }
                value = metodo.call(processorInstance, value) as String
            }
            leafObject.attributes.add(XmlAttribute(it.name, newvalue))
        }
    }
    return leafObject
}
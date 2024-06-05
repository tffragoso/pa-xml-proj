import kotlin.reflect.KClass
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.hasAnnotation
import kotlin.reflect.full.primaryConstructor

/**
 * This class implements a XmlLeaf.
 * XmlLeaf is a xml element that does not contain other xml element(s) in it.
 *
 * @property [name] the name of the XmlLeaf.
 * @property [parent] the parent element of the XmlLeaf, if there is one.
 * @property [attributes] the list of attributes of the XmlLeaf.
 * @property [leafText] the nested text of the XmlLead, if there is one.
 * @constructor Creates a XmlLeaf with a valid, non-null, [name].
 */
data class XmlLeaf(
    override var name: String,
    override var parent: XmlTag? = null,
    override var attributes: MutableList<XmlAttribute> = mutableListOf(),
    val leafText: String? = null,
) : XmlElement {

    init {
        parent?.children?.add(this)
        require(this.isValidElementName()) { "Invalid name. Please provide a valid name for the XmlLeaf." }
    }

}

/**
 * Create a [XmlLeaf] element from the provided object [obj] of any class.
 * Object [obj] properties should have the correct annotations so that this function produces the right result.
 *
 * @return Object of class XmlLeaf that represents the input [obj].
 */
fun mapXmlLeaf(obj: Any): XmlLeaf {
    val objClass = obj::class
    var leafObject = renameLeaf(objClass)

    objClass.declaredMemberProperties.forEach {
        if (it.hasAnnotation<Attribute>()) {
            val xmlStringAnnotation = it.findAnnotation<XmlString>()
            var value = it.call(obj).toString()
            if (xmlStringAnnotation != null) {
                val processorClass = xmlStringAnnotation.value
                val processorInstance = processorClass.primaryConstructor?.call()
                val processorFunctionName = xmlStringAnnotation.function
                val processorFunction = processorClass.members.first {
                    a -> a.name == processorFunctionName }
                value = processorFunction.call(processorInstance, value) as String
            }
            leafObject.attributes.add(XmlAttribute(it.name, value))
        }
    }
    return leafObject
}

/**
 * Create a [XmlLeaf] element from the provided object [obj] of any class, but using an input name [newName]
 * for the element's name.
 * Object [obj] properties should have the correct annotations so that this function produces the right result.
 *
 * @return Object of class XmlLeaf that represents the input [obj].
 */
fun renameLeaf(obj: KClass<out Any>): XmlLeaf {
    //change the element name as required by the user
    var className = obj.simpleName!!.lowercase()
    val xmlAdapterAnnotation = obj.findAnnotation<XmlAdapter>()
    if (xmlAdapterAnnotation != null) {
        val processorClass = xmlAdapterAnnotation.value
        val processorInstance = processorClass.primaryConstructor?.call()
        val newTagName = xmlAdapterAnnotation.newName
        val processorFunctionName = xmlAdapterAnnotation.function
        val processorFunction = processorClass.members.first {
                a -> a.name == processorFunctionName }
        className = processorFunction.call(processorInstance, newTagName) as String
    }
    return XmlLeaf(className,null, mutableListOf())
}
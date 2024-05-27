import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.hasAnnotation
import kotlin.reflect.full.findAnnotation

data class XmlTag(
    override var name: String,
    override var parent: XmlTag? = null,
    override var attributes: MutableList<XmlAttribute> = mutableListOf()
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

fun mapXml(obj: Any): XmlTag {
    val objClass = obj::class

    val tagObject = XmlTag(objClass.simpleName!!.lowercase(),null,mutableListOf())

    objClass.declaredMemberProperties.forEach {
        if(it.hasAnnotation<Attribute>())
            tagObject.attributes.add(XmlAttribute(it.name, it.call(obj).toString()))
        else if(it.hasAnnotation<Leaf>() and !it.hasAnnotation<Nested>())
            XmlLeaf(it.name,tagObject,mutableListOf(),it.call(obj).toString())
        else if(it.hasAnnotation<Inline>()){
            val result = it.call(obj)
            if (result is Collection<*>) {
                result.forEach { item ->
                    tagObject.addChildElement(mapXml(item!!))
                }
            }
        }
        else if(it.hasAnnotation<Nested>() and it.hasAnnotation<Leaf>()){
            var tagaux = XmlTag(it.name,tagObject, mutableListOf())
            val result = it.call(obj)
            if (result is Collection<*>) {
                result.forEach { item ->
                    tagaux.addChildElement(mapXmlLeaf(item!!))
                }
            }
        }
        else if(it.hasAnnotation<Nested>()){
            var tagaux = XmlTag(it.name,tagObject, mutableListOf())
            val result = it.call(obj)
            if (result is Collection<*>) {
                result.forEach { item ->
                    tagaux.addChildElement(mapXml(item!!))
                }
            }
        }else if(it.hasAnnotation<Inline>()){
            val result = it.call(obj)
            if (result is Collection<*>) {
                result.forEach { item ->
                    tagObject.addChildElement(mapXml(item!!))
                }
            }
        }
    }

    return tagObject
}

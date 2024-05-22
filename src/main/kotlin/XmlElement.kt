
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.hasAnnotation

@Target(AnnotationTarget.CLASS, AnnotationTarget.PROPERTY)
annotation class IsTag

@Target(AnnotationTarget.CLASS, AnnotationTarget.PROPERTY)
annotation class IsLeaf

@Target(AnnotationTarget.PROPERTY)
annotation class IsAttribute

@Target(AnnotationTarget.PROPERTY)
annotation class Hiden

@Target(AnnotationTarget.PROPERTY)
annotation class ListObjects

@Target(AnnotationTarget.PROPERTY)
annotation class ListObjectsNoName

sealed interface XmlElement {
    var name: String
    var parent: XmlTag?
    //TODO
    //private val attributes
    var attributes: MutableList<Attribute>

    fun accept(visitor: (XmlElement) -> Boolean) {
        visitor(this)
    }

    /**
     * Adds an [attribute] to a [XmlElement].
     * if the attribute exists the element maintains the same
     * if the attribute doesn't exist then the attribute is added
     */
    fun addAttribute(attribute: Attribute) {
        if (attribute.getName() !in this.attributes.map{it.getName()}) {
            attributes.add(attribute)
        }
    }

    /**
     * Removes the attribute with the [name] from an [XmlElement].
     * if the attribute exists then is deleted
     * if the attribute doesn't exist the object maintains the same
     */
    fun removeAttribute(name: String) {
        val i = this.attributes.iterator()
        while (i.hasNext()) {
            val attribute = i.next()
            if (attribute.getName() == name) {
                i.remove()
            }
        }
    }

    /**
     * Updates [attribute] with the [attribute] value.
     * if the attribute exists then the value is updated
     * if the attribute doesn't exist the object maintains the same
     */
    fun updateAttribute(attribute: Attribute) {
        this.attributes.forEach { e ->
            if(e.getName() == attribute.getName()) {
                e.setValue(attribute.getValue())
            }
        }
    }

    /**
     * Return a list of distinct names of all elements in the XmlElement´s tree.
     * This list includes the XmlElement's name.
     */
    fun listDistinctElementNames(): MutableSet<String>  {
        val distinctElementNames: MutableSet<String> = mutableSetOf()
        accept {
            distinctElementNames.add(it.name)
            true
        }
        return distinctElementNames
    }

    /**
     * return a String with XmlElement and attributes
     * @return [String]
     */
    fun elementToString(): String {
        // Tag opening and name
        var output = "<"

        output += if(this.name == "xml")
            "?" + this.name
        else
            this.name

        // Adding in the attributes, if any exist
        if(this.attributes.isNotEmpty()) {
            val attributesString = attributes.joinToString(separator = " ") { "${it.getName()}=\"${it.getValue()}\"" }
            output += " $attributesString"
        }

        // Tag closing
        if(this is XmlTag)
            output += ">"
        else if(this is XmlLeaf)
            if(this.name == "xml")
                output += "?>"
            else if(this.leafText.isNullOrEmpty())
                output += "/>"
            else
                output += ">" + this.leafText + "</" + this.name + ">"

        return output
    }
}

fun mapXml(obj: Any): XmlTag {
    val objClass = obj::class
    var tagObject = XmlTag(objClass.simpleName!!.lowercase(),null,mutableListOf())
    //val elementName = objClass.simpleName
    //val attributes: MutableList<Attribute> = mutableListOf()
    //val children: MutableList<XmlElement> = mutableListOf()

    objClass.declaredMemberProperties.forEach {
        if(it.hasAnnotation<IsAttribute>())
            tagObject.attributes.add(Attribute(it.name, it.call(obj).toString()))
        else if(it.hasAnnotation<IsLeaf>())
            XmlLeaf(it.name,tagObject,mutableListOf(),it.call(obj).toString())
        else if(it.hasAnnotation<ListObjects>()){
            var tagaux = XmlTag(it.name,tagObject, mutableListOf())
            val result = it.call(obj)
            if (result is Collection<*>) {
                result.forEach { item ->
                    tagaux.addChildElement(mapXml(item!!))
                }
            }
        }else if(it.hasAnnotation<ListObjectsNoName>()){
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

fun auxPrint(element: XmlElement): String {
    var auxOutput = element.elementToString()
    if(element is XmlTag) {
        element.children.forEach {
            auxOutput += auxPrint(it)
        }
        auxOutput += "</" + element.name + ">"
    }
    return auxOutput
}
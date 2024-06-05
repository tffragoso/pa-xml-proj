import kotlin.reflect.KClass

/**
 * Annotation that labels a property as a [XmlLeaf] object.
 */
@Target(AnnotationTarget.PROPERTY)
annotation class Leaf

/**
 * Annotation that labels a property as a [XmlAttribute] object.
 */
@Target(AnnotationTarget.PROPERTY)
annotation class Attribute

/**
 * Inline annotation means the property/object name should be featured in the XML output as a tag.
 */
@Target(AnnotationTarget.PROPERTY)
annotation class Inline

/**
 * Nested annotation means the property/object name should not be featured in the XML output
 * as a tag, only its value.
 */
@Target(AnnotationTarget.PROPERTY)
annotation class Nested

/**
 * XmlString is an annotation that allows the user to modify the text values in the xml entities. after they have been mapped.
 *
 * @property [value] the class that contains the methods/logic that the user wants to apply to the text entry.
 * @property [function] the name of the method that the user wants to apply to the text entry.
 */
@Target(AnnotationTarget.PROPERTY)
annotation class XmlString(
    val value: KClass<out Any>,
    val function: String = ""
    )

/**
 * XmlAdapter is an annotation that allows the user to modify the xml entity after it has been mapped.
 *
 * @property [value] the adapter class that contains the methods/logic that the user wants to apply to the xml entity.
 * @property [function] the name of the method that the user wants to apply to the xml entity.
 */
@Target(AnnotationTarget.CLASS)
annotation class XmlAdapter(
    val value: KClass<*>,
    val function: String = "",
    val newName: String = ""
)

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
 *
 */
@Target(AnnotationTarget.PROPERTY)
annotation class XmlString(
    val value: KClass<out Any>,
    val function: String = ""
    )

/**
 *
 */
@Target(AnnotationTarget.CLASS)
annotation class XmlAdapter(
    val value: KClass<*>,
    val function: String = "",
    val newName: String = ""
)

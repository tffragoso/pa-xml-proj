import kotlin.reflect.KClass

@Target(AnnotationTarget.CLASS, AnnotationTarget.PROPERTY)
annotation class Leaf

@Target(AnnotationTarget.PROPERTY)
annotation class Attribute

@Target(AnnotationTarget.PROPERTY)
annotation class Inline

@Target(AnnotationTarget.PROPERTY)
annotation class Nested

@Target(AnnotationTarget.PROPERTY, AnnotationTarget.FUNCTION)
annotation class XmlString(val value: KClass<*>)

@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
annotation class XmlAdapter(
    val value: KClass<*>,
    val function : String ="",
    val newName: String = ""
)

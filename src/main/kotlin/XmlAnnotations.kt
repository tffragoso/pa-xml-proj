import kotlin.reflect.KClass

@Target(AnnotationTarget.CLASS, AnnotationTarget.PROPERTY)
annotation class Tag

@Target(AnnotationTarget.CLASS, AnnotationTarget.PROPERTY)
annotation class Leaf

@Target(AnnotationTarget.PROPERTY)
annotation class Attribute

@Target(AnnotationTarget.PROPERTY)
annotation class Inline

@Target(AnnotationTarget.PROPERTY)
annotation class Nested

@Target(AnnotationTarget.CLASS, AnnotationTarget.PROPERTY, AnnotationTarget.FUNCTION)
annotation class XmlString(val value: KClass<*>)

class AddPercentage {
    fun addPercentage(value: String): String {
        return "$value%"
    }
}

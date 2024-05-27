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

@Target(AnnotationTarget.PROPERTY, AnnotationTarget.FUNCTION)
annotation class XmlString(val value: KClass<*>)

@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
annotation class XmlAdapter(
    val value: KClass<*>,
    val newName: String = ""
)

class AddPercentage {
    fun addPercentage(value: String): String {
        return "$value%"
    }
}

class ComponenteAvaliacaoAdapter{
    fun changeName(newName: String): String {
        return newName
    }
}

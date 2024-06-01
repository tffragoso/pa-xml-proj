import kotlin.reflect.KClass

@Target(AnnotationTarget.PROPERTY)
annotation class Leaf

@Target(AnnotationTarget.PROPERTY)
annotation class Attribute

@Target(AnnotationTarget.PROPERTY)
annotation class Inline

@Target(AnnotationTarget.PROPERTY)
annotation class Nested

@Target(AnnotationTarget.PROPERTY)
annotation class XmlString(
    val value: KClass<out Any>,
    val function : String =""
    )

@Target(AnnotationTarget.CLASS)
annotation class XmlAdapter(
    val value: KClass<*>,
    val function : String ="",
    val newName: String = ""
)

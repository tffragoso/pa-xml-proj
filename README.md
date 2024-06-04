# XML Manipulation Library

Final project by Susana Ferreira & Tomás Fragoso

Advanced Programming class\
Masters in Informatics Engineering\
ISCTE - Instituto Universitário de Lisboa\
2023/2024

## About the Project
With this library the user can build XML entities out of objects they provide.\
This library does not read XML files as input.

The Classes in this project relate to one another as pictured in the following UML diagram:
![Optional Alt Text](images/classes_uml.png)

## How to use
*italic like this*

```
code like this
```

**bold like this**

`highlight like this`

## Anotações

### Leaf

> **Target** Property
>
> **Description** Identify a final element 
>
>| XML Example  | Kotlin Usage|
>|------------|------------|
>| ```<nome>Programação Avançada</nome> ```| <code> @Leaf</code><br> <code> val nome: String,</code>|

### Attribute

>**Target** Property
>
>**Description** Identify an attribute
>| XML Example  | Kotlin Usage|
>|------------|------------|
>| ```<fuc codigo="M4310">```| <code>class FUC<br>&nbsp;&nbsp;&nbsp;&nbsp;@Attribute<br>&nbsp;&nbsp;&nbsp;&nbsp;var codigo: String,</code>|

### Inline

>**Target** Property
>
>**Description** Identify an element without an aggregator
>| XML Example  | Kotlin Usage|
>|------------|------------|
>| ``` <plano> ```<br>&nbsp;&nbsp;&nbsp;&nbsp;```<fuc codigo="M4310">```<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;``` <ects>6.0</ects> ```<br>&nbsp;&nbsp;&nbsp;&nbsp;``` </fuc> ```<br> &nbsp;&nbsp;&nbsp;&nbsp;``` <fuc codigo="03782"> ```<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;``` <ects>42.0</ects> ```<br> &nbsp;&nbsp;&nbsp;&nbsp;``` </fuc> ```<br> ``` </plano> ```| <code> class Plano(<br> &nbsp;&nbsp;&nbsp;&nbsp;@Inline<br>&nbsp;&nbsp;&nbsp;&nbsp;val fuc:List&lt;FUC&gt;<br>)</code>|

### Nested

>**Target** Property
>
>**Description** Identify an element with an aggregator
>| XML Example  | Kotlin Usage|
>|------------|------------|
>| ``` <fuc codigo="M4310"> ``` <br> &nbsp;&nbsp;&nbsp;&nbsp;``` <avaliacao> ```<br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;```<componente nome="Quizzes" peso="20%"/>```<br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;```<componente nome="Projeto" peso="80%"/>```<br> &nbsp;&nbsp;&nbsp;&nbsp;```</avaliacao>```<br> ``` <\fuc> ```| <code> class FUC(<br> &nbsp;&nbsp;&nbsp;&nbsp;@Nested<br>&nbsp;&nbsp;&nbsp;&nbsp;val avaliacao: List&lt;Componente&gt;<br>)</code>|

### XmlString

>**Target** Property
>
>**Description** Apply user-defined function to an element (in this example, adds a percentage to the attribute value)
>| XML Example  | Kotlin Usage|
>|------------|------------|
>| ```<componente nome="Quizzes" peso="20%"/>```| <code>@XmlString(AddPercentage::class,function="addPercentage")<br>val peso: Any</code>|

### XmlAdapter

>**Target** Class
>
>**Description** Apply user-defined function to the element after mapping (in this example, changes an attribute value)
>| XML Example  | Kotlin Usage|
>|------------|------------|
>| Before<br>```<fuc codigo="M4310">```<br><br>After<br>```<fuc codigo="AAAAAA23657">```| <code>@XmlAdapter(FUCAdapter::class)<br>class FUC(<br>&nbsp;&nbsp;&nbsp;&nbsp;@Attribute<br>&nbsp;&nbsp;&nbsp;&nbsp;var codigo: String<br>)<br><br>class FUCAdapter {<br>&nbsp;&nbsp;&nbsp;&nbsp;fun ChangeCodigoValue(objFuc:FUC, newValue:String){<br>&nbsp;&nbsp;&nbsp;&nbsp;objFuc.codigo= newValue<br>&nbsp;&nbsp;&nbsp;&nbsp;}<br>}<br><br>FUCAdapter().ChangeCodigoValue(f,"AAAAAA23657")</code>|

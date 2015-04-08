

# About namespaces #

Xmappr fully supports XML with namespaces. There are two ways to configure Xmappr to use namespaces: configuration-wide or per element.

# Namespaces defined in configuration #

Namespace can be defined configuration-wide via Xmappr.addNamespace("x","namespace"), where ‘x’ is a prefix. Namespaces defined in such way are available on all elements in Xmappr. Here is the code:
```
Xmappr xmappr = new Xmappr(Root.class);
xmappr.addNamespace("y","customnamespace");   // Defining namespace instance-wide.
Root root = (Root) xmappr.fromXML(reader);
```
to map this XML:
```
<x:root a="2.2" xmlns:x="customnamespace">
    some text
    <x:node>123</x:node>
</x:root>
```
to this class:
```
@RootElement("y:root")
public class Root {

    @XMLattribute("y:a")
    public float a;

    @XMLelement("y:node")
    public Integer node;

    @XMLtext
    public String text;
}
```
Note that you don’t have to use the same prefix in your code and in XML – that is the whole point of prefixes! It’s only necessary that prefixes point to the same namespace, in this example it’s “customnamespace”.

Instance-wide settings apply to all mappings in current Xmappr instance, so any namespace defined on Xmappr instance will apply to all elements in all mappings.

# Namespaces defined on elements #

If you need to define a namespace only for a certain mapping or even for an element within a mapping than you can define the namespace on the element. Given the above XML, the mapping would be:

```
Xmappr xmappr = new Xmappr(Root.class);
Root root = (Root) xmappr.fromXML(reader);

@Namespaces("y=customnamespace")   // Namespace defined on root element
@RootElement("y:root")
public class Root {

    @XMLattribute("y:a")
    public float a;

    @XMLelement("y:node")
    public Integer node;

    @XMLtext
    public String text;
}
```

Using `@Namespaces` on a class applies also to all of it’s fields – you don’t need to repeat `@Namespaces` on class fields if it’s already defined on class.
**NOTE:** Unlike XML namespace definitions which are inherited by sub-elements, `@Namespaces` definitions are defined on particular field or class and are not inherited by referenced classes. E.g. you have to repeat `@Namespaces` definition of each class/or field where you need to use it.

# Overriding namespaces #

XML allows namespaces to be overriden within the same document:

```
<x:root xmlns:x="customnamespace">
  <x:sub xmlns:x="anothernamespace">2</x:sub>
</x:root>
```

Here `<root>` element belongs to “customnamespace” and `<sub>` belongs to “anothernamespace”, but both are using same prefix “x”. The prefix was effectively overridden. Xmappr can handle this with such mapping:

```
@Namespaces("x=customnamespace")
@RootElement("x:root")
public class Root {

  @Namespaces("x=anothernamespace")
  @XMLelement("x:sub")
  public String sub;
}
```

Or one could use example from paragraph 1. above, where `Xmappr.addNamespace(”x”, “customnamespace”)` would be used and then overridden with `@Namespaces(”x=anothernamespace”)` on the sub field.
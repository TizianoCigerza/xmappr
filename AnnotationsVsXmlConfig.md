# Annotations versus XML configuration #

## All the same.. ##
As described in [quick start](QuickStart.md), Xmappr mappings can be described via Java annotations or via XML configuration. This two are equivalent:

Mapping via Java annotations:
```
@RootElement
public static class Root {

    @Attribute
    public float a;

    @Element
    public Integer node;

    @Text
    public String text;
}
```

Mapping via XML configuration:

```
<root-element name="root" class="package.name.Root">
  <attribute field="a"/>
  <element field="node"/>
  <text field="text"/>
</root-element>
```

Xmappr internally builds a mapping configuration tree, which is the same no matter from which source it's built.

## ..but still different ##

Being internally the same the two configuration method still have some differences:
  1. **XML configuration can map one class to different XMLs.** With XML configuration you can have different configurations that all map to the same class. This is useful in situations like format conversion. Obviously annotations can be defined only once on a Java class, so it is not possible to have multiple mappings defined for one class via annotations.
  1. **Mapping via annotations can have loops:** a class holding a reference to itself (directly or indirectly via another class) produces a loop in configuration. This is not possible with XML configuration. Loops in configuration are needed when parsing XML where depth of XML tree is arbitrary and where element nests the same element. In this case you must use annotation-based mapping.

## Converting from annotations to XML configuration ##
Occasionally you might want to convert your annotation-based configuration to XML-based configuration. This can easily be done:

```
Xmappr xmappr = new Xmappr(Root.class);
String xmlConfiguration = xmappr.getXmlConfiguration(Root.class);
```

A returned string contains XML based mapping for class `Root`.
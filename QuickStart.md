# Quick Start #

## Basic mappings ##

Xmappr is a small and fast library that maps arbitrary XML to your classes. It was designed to handle complex XML coming from external sources, where you do not have control over how it’s generated nor when it’s structure (schema) changes. Xmappr’s biggest strength is that it let’s you map just a part of XML document that is of interest to you.

Let’s give it a spin with this first example. You want to map this XML:
```
<root a="2.2">
    some text
    <node>123</node>
</root>
```
to this class:
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
All you have to do is add `@RootElement`, `@Element`, `@Attribute` or `@Text` annotations to your classes and configuration is done.

Mapping is than done in just three lines of code:
```
Xmappr xm = new Xmappr(Root.class);
Root root = (Root) xm.fromXML(reader);
```

## Mapping via XML configuration ##
If you don't want to use annotations you can achieve the same effect with an external XML configuration file. For the above example the XML config would be:
```
<root-element name="root" class="package.name.Root">
  <attribute field="a"/>
  <element field="node"/>
  <text field="text"/>
</root-element>
```
In case of XML configuration, invoking Xmappr is just a bit different - pass a `Reader` to `Xmappr` where XML configuration can be read:
```
Reader xmlConfigReader = new FileReader("/path/to/XmlConfigFile");
Xmappr xm = new Xmappr(xmlConfigReader);
Root root = (Root) xm.fromXML(reader);
```

## Mappings with a name ##

If XML element names differ from your field names, then just add element name to the Xmappr annotations:
```
@RootElement("root")
public static class NewRoot {

    @Attribute("a")
    public float attribute;

    @Element("node")
    public Integer someOtherNodeName;

    @Text
    public String theTextOfTheNode;
}
```

or with XML configuration:

```
<root-element name="root" class="package.name.NewRoot">
  <attribute field="attribute" name="a"/>
  <element field="someOtherNodeName" name="node"/>
  <text field="theTextOfTheNode"/>
</root-element>
```

Text elements don’t have names, of course.
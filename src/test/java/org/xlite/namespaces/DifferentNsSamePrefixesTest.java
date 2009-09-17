/*
 * This software is released under the BSD license. Full license available at http://www.xlite.org/license/
 *
 * Copyright (c) 2008, 2009, Peter Knego & Xlite contributors
 * All rights reserved.
 */
package org.xlite.namespaces;

import java.io.StringReader;
import java.io.StringWriter;
import java.io.IOException;

import org.testng.Assert;
import org.custommonkey.xmlunit.XMLUnit;
import org.custommonkey.xmlunit.XMLAssert;
import org.xml.sax.SAXException;
import org.xlite.*;

/**
 * Test where xml elements belong to different namespaces although they have the same prefixes.
 */
public class DifferentNsSamePrefixesTest {

    private static String xml = "" +
            "<aaa >\n" +
            "  <lower:bbbb xmlns:lower = \"lowercase\" >\n" +
            "     <lower:cccc />\n" +
            "  </lower:bbbb>\n" +
            "  <lower:BBB xmlns:lower = \"uppercase\" >\n" +
            "    <lower:CCC />\n" +
            "  </lower:BBB>\n" +
            "  <lower:x111 xmlns:lower = \"xnumber\" >\n" +
            "    <lower:x222 />\n" +
            "  </lower:x111>\n" +
            "</aaa>";

    @org.testng.annotations.Test
    public void test() throws IOException, SAXException {
        StringReader reader = new StringReader(xml);
        Configuration conf = new AnnotationConfiguration(aaa.class, "aaa");

        Xlite xlite = new Xlite(conf);
        aaa a = (aaa) xlite.fromXML(reader);

        Assert.assertTrue(a.node_bbbb.node_cccc != null);
        Assert.assertTrue(a.node_BBB.node_CCC != null);
        Assert.assertTrue(a.node_x111.node_x222 != null);

        // writing back to XML
        StringWriter sw = new StringWriter();
        xlite.toXML(a, sw);
        System.out.println(xml);
        System.out.println(sw);
        XMLUnit.setIgnoreWhitespace(true);
        XMLAssert.assertXMLEqual(xml, sw.toString());
    }

    // node aaa is in default namespace
    public static class aaa {
        @Namespaces("lower=lowercase")
        @Element("lower:bbbb")
        public bbbb node_bbbb;

        @Namespaces("lower=uppercase")
        @Element("lower:BBB")
        public BBB node_BBB;

        @Namespaces("lower=xnumber")
        @Element("lower:x111")
        public x111 node_x111;
    }

    public static class bbbb {
        @Namespaces("lower=lowercase")
        @Element("lower:cccc")
        public cccc node_cccc;
    }

    public static class cccc {
    }

    public static class BBB {
        @Namespaces("lower=uppercase")
        @Element("lower:CCC")
        public CCC node_CCC;
    }

    public static class CCC {
    }

    @Namespaces("lower=xnumber")
    public static class x111 {
        @Element("lower:x222")
        public x222 node_x222;
    }

    public static class x222 {
    }
}

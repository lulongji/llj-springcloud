package com.lulongji.base.wechat.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

//先加入dom4j.jar包 
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

/**
 * @author lu
 * @version V1.0
 * @Title: TestDom4j.java
 * @Package
 * @Description: 解析xml字符串
 */
public class XmlUtil {

    /**
     * @param xml
     * @return Map
     * @description 将xml字符串转换成map
     */
    public static Map<String, String> xml2map(String xml) {

        Map<String, String> map = new HashMap<String, String>();
        Document doc = null;
        // 将字符串转为XML
        try {
            doc = DocumentHelper.parseText(xml);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        // 获取根节点
        Element rootElt = doc.getRootElement();
        String rootName = rootElt.getName();
        // 拿到根节点的名称
        convert(rootElt, map, rootName);
        return map;
    }

    /**
     * 递归函数，找出最下层的节点并加入到map中，由xml2Map方法调用。
     *
     * @param e        xml节点，包括根节点
     * @param map      目标map
     * @param lastname 从根节点到上一级节点名称连接的字串
     */
    public static void convert(Element e, Map<String, String> map, String lastname) {
        if (e.attributes().size() > 0) {
            Iterator<?> it_attr = e.attributes().iterator();
            while (it_attr.hasNext()) {
                Attribute attribute = (Attribute) it_attr.next();
                String attrname = attribute.getName();
                String attrvalue = e.attributeValue(attrname);
                map.put(lastname + "." + attrname, attrvalue);
            }
        }
        List<?> children = e.elements();
        Iterator<?> it = children.iterator();
        while (it.hasNext()) {
            Element child = (Element) it.next();
            String name = child.getName();
            // 如果有子节点，则递归调用
            if (child.elements().size() > 0) {
                convert(child, map, name);
            } else {
                // 如果没有子节点，则把值加入map
                map.put(name, child.getText());
                // 如果该节点有属性，则把所有的属性值也加入map
                if (child.attributes().size() > 0) {
                    Iterator<?> attr = child.attributes().iterator();
                    while (attr.hasNext()) {
                        Attribute attribute = (Attribute) attr.next();
                        String attrname = attribute.getName();
                        String attrvalue = child.attributeValue(attrname);
                        map.put(name + "." + attrname, attrvalue);
                    }
                }
            }
        }
    }

    /**
     * 解析xml,返回第一级元素键值对。如果第一级元素有子节点，则此节点的值是子节点的xml数据。
     *
     * @param strxml
     * @return
     * @throws JDOMException
     * @throws IOException
     */
    public static Map<String, String> doXMLParse(String strxml) throws JDOMException, IOException {
        strxml = strxml.replaceFirst("encoding=\".*\"", "encoding=\"UTF-8\"");

        if (null == strxml || "".equals(strxml)) {
            return null;
        }
        Map<String, String> m = new HashMap<String, String>();
        InputStream in = new ByteArrayInputStream(strxml.getBytes("UTF-8"));
        SAXBuilder builder = new SAXBuilder();
        org.jdom2.Document doc = builder.build(in);
        org.jdom2.Element root = doc.getRootElement();
        List<?> list = root.getChildren();
        Iterator<?> it = list.iterator();
        while (it.hasNext()) {
            org.jdom2.Element e = (org.jdom2.Element) it.next();
            String k = e.getName();
            String v = "";
            List<?> children = e.getChildren();
            if (children.isEmpty()) {
                v = e.getTextNormalize();
            } else {
                v = XmlUtil.getChildrenText(children);
            }

            m.put(k, v);
        }
        in.close();

        return m;
    }

    /**
     * 获取子结点的xml
     *
     * @param children
     * @return String
     */
    public static String getChildrenText(List<?> children) {
        StringBuffer sb = new StringBuffer();
        if (!children.isEmpty()) {
            Iterator<?> it = children.iterator();
            while (it.hasNext()) {
                org.jdom2.Element e = (org.jdom2.Element) it.next();
                String name = e.getName();
                String value = e.getTextNormalize();
                List<?> list = e.getChildren();
                sb.append("<" + name + ">");
                if (!list.isEmpty()) {
                    sb.append(XmlUtil.getChildrenText(list));
                }
                sb.append(value);
                sb.append("</" + name + ">");
            }
        }

        return sb.toString();
    }

}
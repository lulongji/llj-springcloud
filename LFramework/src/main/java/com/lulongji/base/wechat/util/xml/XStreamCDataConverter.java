package com.lulongji.base.wechat.util.xml;

import com.thoughtworks.xstream.converters.basic.StringConverter;

/**
 * @author lu
 */
public class XStreamCDataConverter extends StringConverter {

    @Override
    public String toString(Object obj) {
        return "<![CDATA[" + super.toString(obj) + "]]>";
    }

}

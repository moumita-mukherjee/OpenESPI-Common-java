package org.energyos.espi.common.utils;


import javax.xml.bind.annotation.adapters.XmlAdapter;

public final class StringBinaryAdapter extends XmlAdapter<String,byte[]> {
    public byte[] unmarshal(String s) {
        if(s==null)     return null;
        return s.getBytes();
    }

    public String marshal(byte[] bytes) {
        if(bytes==null)     return null;
        return new String(bytes);
    }
}

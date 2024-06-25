package org.winterframework.extensions.java.lang.String;

import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.This;

/**
 * @author qinglin.liu
 * created at 2024/6/25 13:56
 */
@Extension
public class StringExt {
    public static String sayHello(@This String self) {
        return "Hello, " + self;
    }

    public static void main(String[] args) {
        System.out.println("Sven".sayHello());
    }
}

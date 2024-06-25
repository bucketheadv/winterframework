# 实现扩展方法步骤

1. 安装依赖包
```xml
<dependency>
    <groupId>systems.manifold</groupId>
    <artifactId>manifold-ext</artifactId>
    <version>${manifold.version}</version>
</dependency>
```
2. 安装`idea`插件`Manifold`
3. `pom.xml`配置打包
```xml
<build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.12.1</version>
                <configuration>
                    <source>${java.version}</source>
                    <target>${java.version}</target>
                    <showWarnings>true</showWarnings>
                    <compilerArgs>
                        <arg>-Xplugin:Manifold no-bootstrap</arg>
                    </compilerArgs>
                    <annotationProcessorPaths>
                        <path>
                            <groupId>org.projectlombok</groupId>
                            <artifactId>lombok</artifactId>
                            <version>${lombok.version}</version>
                        </path>
                        <path>
                            <groupId>systems.manifold</groupId>
                            <artifactId>manifold-ext</artifactId>
                            <version>${manifold.version}</version>
                        </path>
                    </annotationProcessorPaths>
                </configuration>
            </plugin>
        </plugins>
    </build>
```
4. 使用包名需要以特定`extensions.xxxx.yyyy.zzzz`结尾，如扩展 `java.lang.String`类，包名为 `org.winterframework.extensions.java.lang.String`;
5. 示例扩展方法如下
```java
package org.winterframework.extensions.java.lang.String;

import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.This;

@Extension
public class StringExt {
    public static String sayHello(@This String self) {
        return "Hello, " + self;
    }

    public static void main(String[] args) {
        System.out.println("Sven".sayHello());
    }
}
```
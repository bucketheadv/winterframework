## Install

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>com.github.bucketheadv</groupId>
        <artifactId>winter-starter-parent</artifactId>
        <version>0.6.0</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    <artifactId>yourApp</artifactId>

    <dependencies>
        <!-- if you need all components -->
        <dependency>
            <groupId>com.github.bucketheadv</groupId>
            <artifactId>winterframework</artifactId>
        </dependency>

        <!-- if you need core only -->
        <dependency>
            <groupId>com.github.bucketheadv.winterframework</groupId>
            <artifactId>winter-core</artifactId>
        </dependency>

        <!-- if you need redis only -->
        <dependency>
            <groupId>com.github.bucketheadv.winterframework</groupId>
            <artifactId>winter-data-redis</artifactId>
        </dependency>

        <!-- if you need elasticsearch only -->
        <dependency>
            <groupId>com.github.bucketheadv.winterframework</groupId>
            <artifactId>winter-elasticsearch</artifactId>
        </dependency>

        <!-- if you need kafka only -->
        <dependency>
            <groupId>com.github.bucketheadv.winterframework</groupId>
            <artifactId>winter-kafka</artifactId>
        </dependency>

        <!-- if you need mongodb only -->
        <dependency>
            <groupId>com.github.bucketheadv.winterframework</groupId>
            <artifactId>winter-mongodb</artifactId>
        </dependency>

        <!-- if you need rocketmq only -->
        <dependency>
            <groupId>com.github.bucketheadv.winterframework</groupId>
            <artifactId>winter-rocketmq</artifactId>
        </dependency>
    </dependencies>
</project>
```

## Usage

See `readme.md` in every module.
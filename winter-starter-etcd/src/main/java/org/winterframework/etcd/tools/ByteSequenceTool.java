package org.winterframework.etcd.tools;

import io.etcd.jetcd.ByteSequence;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author qinglin.liu
 * created at 2024/2/5 20:09
 */
public class ByteSequenceTool {
    public static ByteSequence from(String s) {
        return from(s, StandardCharsets.UTF_8);
    }

    public static ByteSequence from(String s, Charset charset) {
        return ByteSequence.from(s, charset);
    }
}

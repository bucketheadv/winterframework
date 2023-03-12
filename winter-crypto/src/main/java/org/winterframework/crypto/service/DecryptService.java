package org.winterframework.crypto.service;

/**
 * @author sven
 * Created on 2023/3/11 1:53 PM
 */
public interface DecryptService {
    <T> T decrypt(T result) throws Exception;
}

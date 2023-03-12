package org.winterframework.crypto.service;

/**
 * @author sven
 * Created on 2023/3/11 1:52 PM
 */
public interface EncryptService {
    <T> T encrypt(T paramsObject) throws Exception;
}

package org.winterframework.rbac.exception;

import java.io.Serial;

/**
 * @author qinglinl
 * Created on 2022/11/4 6:15 PM
 */
public class RbacPermDeniedException extends RuntimeException {
	@Serial
	private static final long serialVersionUID = -5348316313047448666L;

	public RbacPermDeniedException(String msg) {
		super(msg);
	}
}

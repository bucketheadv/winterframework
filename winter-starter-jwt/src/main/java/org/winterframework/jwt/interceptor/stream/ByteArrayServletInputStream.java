package org.winterframework.jwt.interceptor.stream;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import lombok.Getter;
import lombok.Setter;

import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * @author qinglinl
 * Created on 2022/9/27 3:06 PM
 */
@Getter
@Setter
public class ByteArrayServletInputStream extends ServletInputStream {
	private final ByteArrayInputStream stream;

	private ReadListener readListener;

	public ByteArrayServletInputStream(ByteArrayInputStream sourceStream) {
		this.stream = sourceStream;
	}

	public ByteArrayServletInputStream(byte[] bytes) {
		this.stream = new ByteArrayInputStream(bytes);
	}

	@Override
	public boolean isFinished() {
		return stream.available() <= 0;
	}

	@Override
	public boolean isReady() {
		return stream.available() > 0;
	}

	@Override
	public int read() throws IOException {
		return stream.read();
	}

	@Override
	public void close() throws IOException {
		super.close();
		stream.close();
	}

}
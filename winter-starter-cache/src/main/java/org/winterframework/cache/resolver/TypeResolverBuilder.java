package org.winterframework.cache.resolver;

import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;
import org.springframework.util.ClassUtils;

import java.io.Serial;

/**
 * @author qinglinl
 * Created on 2022/11/4 1:00 PM
 */
public class TypeResolverBuilder extends ObjectMapper.DefaultTypeResolverBuilder {
	@Serial
	private static final long serialVersionUID = -150874719703412692L;

	public TypeResolverBuilder(ObjectMapper.DefaultTyping t, PolymorphicTypeValidator ptv) {
		super(t, ptv);
	}

	@Override
	public ObjectMapper.DefaultTypeResolverBuilder withDefaultImpl(Class<?> defaultImpl) {
		return this;
	}

	@Override
	public boolean useForType(JavaType t) {
		if (t.isJavaLangObject()) {
			return true;
		} else {
			t = this.resolveArrayOrWrapper(t);
			if (ClassUtils.isPrimitiveOrWrapper(t.getRawClass())) {
				return false;
			} else {
				return !TreeNode.class.isAssignableFrom(t.getRawClass());
			}
		}
	}

	private JavaType resolveArrayOrWrapper(JavaType type) {
		while(type.isArrayType()) {
			type = type.getContentType();
			if (type.isReferenceType()) {
				type = this.resolveArrayOrWrapper(type);
			}
		}

		while(type.isReferenceType()) {
			type = type.getReferencedType();
			if (type.isArrayType()) {
				type = this.resolveArrayOrWrapper(type);
			}
		}

		return type;
	}
}
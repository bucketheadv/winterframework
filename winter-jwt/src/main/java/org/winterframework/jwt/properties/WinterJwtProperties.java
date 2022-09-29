package org.winterframework.jwt.properties;

import com.google.common.collect.Lists;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author qinglinl
 * Created on 2022/9/27 5:54 PM
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "winter.jwt")
public class WinterJwtProperties {
	/**
	 * 是否验证Token
	 */
	private boolean checkToken = true;

	/**
	 * 是否验证签名
	 */
	private boolean checkSignature = false;

	/**
	 * 是否允许空Token
	 */
	private boolean allowTokenEmpty = false;

	/**
	 * Token校验白名单
	 */
	private final List<String> tokenWhiteUriArray = new ArrayList<>();

	/**
	 * 签名校验白名单
	 */
	private final List<String> signatureWhiteUriArray = new ArrayList<>();

	/**
	 * 支持的语言
	 */
	private final List<Locale> supportLocales = Lists.newArrayList(Locale.ENGLISH, Locale.CHINESE);

	/**
	 * 默认语言
	 */
	private Locale defaultLocale = Locale.ENGLISH;

	/**
	 * 需要执行BasedInterceptor的路径正则表达式，如 /**
	 */
	private List<String> pathPatterns = Lists.newArrayList();

	/**
	 * 不需要执行BasedInterceptor的路径正则表达式，如("/static", "/webjars", "/swagger", "/v2", "/doc.html")
	 */
	private List<String> excludePathPatterns = Lists.newArrayList();
}

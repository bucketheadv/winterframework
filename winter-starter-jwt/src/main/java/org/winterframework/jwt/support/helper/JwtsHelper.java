package org.winterframework.jwt.support.helper;

import com.google.common.collect.Maps;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.DefaultClaims;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.winterframework.core.i18n.api.I18nErrorCode;
import org.winterframework.core.i18n.exception.ServiceException;
import org.winterframework.core.tool.JsonTool;
import org.winterframework.core.tool.StringTool;
import org.winterframework.jwt.env.Environment;
import org.winterframework.jwt.support.enums.WebErrorCode;
import org.winterframework.jwt.support.enums.EnvironmentType;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Map;

/**
 * @author qinglinl
 * Created on 2022/9/27 1:57 PM
 */
@Slf4j
public class JwtsHelper {
	private static final String[] JWT_KEYS = new String[]{"J0jKTG3hI4chg9i0l90q3An6oaGHXKZI", "R5xKe8PKoZ0LEgtUvv9Jytz9rPhKe4hj", "cHFHQHXi7oVVLJgos1bySY40CrA2CsXa", "gsY2Ksrn7Sjif6FNPXIbptbokZb3l4Xk", "Ebs2Su1YdWVMrxXUp1RvIvrui8ZnNKhm", "Q1MFI8bV1OEMPPszKI3zJFX4wceeAJbD", "GToOrdEineUutlz19MuPeycgMdzBVBYC", "fs5Ht6eVfo8ARcQPDDE5Vd9tA7gypCUW"};

	private static final String[] JWT_TOURIST_KEYS = new String[]{"6TT72jqqHQeSN4qDjFhgHW7LfGmFUXfU", "XDmba5YhycmYlqXIy2xjJQq07gPe4SGq", "bwufTbnXOANFfFAkGa3VDlwLGKcv0cBq", "DTnV6UtYio3nTn3ct6MafWYdWrfMr06N", "Pfc7z5ajhcCI2wKYSfoJdiZoZ6nnrAVG"};

	private JwtsHelper() {}

	private static <T> T throwsServiceException(I18nErrorCode i18nEnum) {
		throw new ServiceException(i18nEnum);
	}

	public static void check(Environment environment) throws ServiceException {
		if (StringUtils.isBlank(environment.getToken())) {
			log.error("check token failed, token is empty, env: {}", environment);
			throwsServiceException(WebErrorCode.TOKEN_INVALID);
		} else {
			Map<String, Object> map = verify(environment);
			if (!map.containsKey("uid")) {
				log.error("check token failed, uid is empty, data map: {}, env: {}", map, environment);
				throwsServiceException(WebErrorCode.TOKEN_INVALID);
			}
		}
	}

	public static String encrypt(Long uid) {
		return encrypt(uid, 86400L);
	}

	public static String encrypt(Long uid, Long expirationSeconds) {
		long expiration = System.currentTimeMillis() / 1000L + expirationSeconds;
		int kid = RandomUtils.nextInt(0, JWT_KEYS.length);
		Map<String, Object> header = Maps.newHashMap();
		header.put("exp", expiration);
		header.put("kid", kid);
		header.put("env", EnvironmentHelper.findEnvironmentType());
		Map<String, Object> claims = Maps.newHashMap();
		claims.put("uid", uid);
		claims.put("exp", expiration);
		SecretKey secretKey = Keys.hmacShaKeyFor(JWT_KEYS[kid].getBytes(StandardCharsets.UTF_8));
		JwtBuilder builder = Jwts.builder();
		builder.signWith(secretKey)
				.setExpiration(new Date(expiration * 1000L))
				.setHeaderParams(header)
				.addClaims(claims);
		return builder.compact();
	}

	public static String findUid(Environment env) {
		Map<String, Object> map = verify(env.getToken(), true, env);
		if (!map.containsKey("uid")) {
			log.error("check token failed, uid not exists, environment: {}", env);
			return throwsServiceException(WebErrorCode.TOKEN_INVALID);
		}
		return map.get("uid").toString();
	}

	public static Map<String, Object> verify(Environment environment) throws ServiceException {
		try {
			return verify(environment.getToken(), new TokenSigningKeyResolver(), false, environment);
		} catch (SignatureException e) {
			return verifyTourist(environment.getToken(), false, environment);
		}
	}

	public static Map<String, Object> verify(String token, boolean allowExpired, Environment env) {
		try {
			return verify(token, new TokenSigningKeyResolver(), allowExpired, env);
		} catch (SignatureException e) {
			return verifyTourist(token, allowExpired, env);
		}
	}

	public static Map<String, Object> verify(String token, SigningKeyResolverAdapter adapter, boolean allowExpired, Environment env) {
		try {
			Jws<Claims> claimsJws = Jwts.parserBuilder()
					.setSigningKeyResolver(adapter)
					.build().parseClaimsJws(token);
			Object headerEnv = claimsJws.getHeader().get("env");
			EnvironmentType environmentType = EnvironmentHelper.findEnvironmentType();
			if (headerEnv != null && !StringUtils.equalsIgnoreCase(headerEnv.toString(), environmentType.getValue())) {
				log.error("verify token failed, current token env not equals current env, token: {}, token env: {}, env: {}", token, headerEnv, env);
				return throwsServiceException(WebErrorCode.TOKEN_ENV_INCONSISTENT);
			} else {
				Map<String, Object> resultMap = Maps.newHashMap();
				for (String key : claimsJws.getBody().keySet()) {
					resultMap.put(key, claimsJws.getBody().get(key));
				}
				return resultMap;
			}
		} catch (Exception e) {
			if (e instanceof ExpiredJwtException) {
				return handleExpiredJwtException(token, allowExpired, env, e);
			} else if (e instanceof SignatureException || e instanceof ServiceException) {
				throw e;
			} else {
				if (e instanceof MalformedJwtException && StringUtils.containsIgnoreCase(e.getMessage(), "but the header does not reference a valid signature algorithm")) {
					verifyOldToken(token);
				}
				log.error("verify token failed, token: {}, error message: {}", token, e.getMessage(), e);
				return throwsServiceException(WebErrorCode.TOKEN_ENV_INCONSISTENT);
			}
		}
	}

	private static void verifyOldToken(String token) {
		String[] ts = token.split("[.]");

		for (String s : ts) {
			try {
				String decodedString = new String(Decoders.BASE64.decode(s), StandardCharsets.UTF_8);
				Map<String, Object> jsonObject = JsonTool.parseMap(decodedString, String.class, Object.class);
				assert jsonObject != null;
				long expTime = StringTool.convert2Long(jsonObject.get("exp"));
				long current = System.currentTimeMillis() / 1000L;
				if (current > expTime) {
					log.warn("verify token failed, the token has expired, exp time: {}, current time: {}", expTime, current);
					throwsServiceException(WebErrorCode.TOKEN_INVALID);
				}
			} catch (ServiceException e) {
				throw e;
			} catch (Exception ignored) {
			}
		}
	}

	private static Map<String, Object> handleExpiredJwtException(String token, boolean allowExpired, Environment env, Exception e) {
		if (!allowExpired) {
			log.warn("verify token failed, the token has expired, error message: {}, env: {}", e.getMessage(), env);
			return throwsServiceException(WebErrorCode.TOKEN_EXPIRED);
		} else {
			ExpiredJwtException expiredJwtException = (ExpiredJwtException) e;
			if (!(expiredJwtException.getClaims() instanceof DefaultClaims)) {
				log.warn("verify token failed, the token has expired, error message: {}, env: {}", e.getMessage(), env);
				return throwsServiceException(WebErrorCode.TOKEN_EXPIRED);
			} else {
				Object headerEnv = expiredJwtException.getHeader().get("env");
				EnvironmentType environmentType = EnvironmentHelper.findEnvironmentType();
				if (headerEnv != null && !StringUtils.equalsIgnoreCase(headerEnv.toString(), environmentType.getValue())) {
					log.error("verify token failed, current token env not equals current env, token: {}, token env: {}, env: {}", token, headerEnv, env);
					return throwsServiceException(WebErrorCode.TOKEN_ENV_INCONSISTENT);
				} else {
					DefaultClaims defaultClaims = (DefaultClaims) expiredJwtException.getClaims();
					Map<String, Object> resultMap = Maps.newHashMap();
					for (String key : defaultClaims.keySet()) {
						resultMap.put(key, defaultClaims.get(key));
					}
					return resultMap;
				}
			}
		}
	}

	private static Map<String, Object> verifyTourist(String token, boolean allowExpired, Environment environment) {
		return verify(token, new TouristTokenSigningKeyResolver(), allowExpired, environment);
	}

	private static class TouristTokenSigningKeyResolver extends SigningKeyResolverAdapter {
		private TouristTokenSigningKeyResolver() {}

		public Key resolveSigningKey(JwsHeader header, Claims claims) {
			String keyId = header.getKeyId();
			String keyStr = JWT_TOURIST_KEYS[Integer.parseInt(keyId)];
			return Keys.hmacShaKeyFor(keyStr.getBytes(StandardCharsets.UTF_8));
		}
	}

	private static class TokenSigningKeyResolver extends SigningKeyResolverAdapter {
		private TokenSigningKeyResolver() {}

		public Key resolveSigningKey(JwsHeader header, Claims claims) {
			String keyId = header.getKeyId();
			String keyStr = JWT_KEYS[Integer.parseInt(keyId)];
			return Keys.hmacShaKeyFor(keyStr.getBytes(StandardCharsets.UTF_8));
		}
	}
}

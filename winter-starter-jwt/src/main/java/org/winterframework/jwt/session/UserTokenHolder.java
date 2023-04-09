package org.winterframework.jwt.session;

/**
 * @author qinglinl
 * Created on 2022/9/27 3:03 PM
 */
public class UserTokenHolder {
	private static final ThreadLocal<UserToken> userTokenThreadLocal = new ThreadLocal<>();

	public static void setCurrentUser(UserToken userToken) {
		if (userToken != null) {
			userTokenThreadLocal.set(userToken);
		}
	}

	public static UserToken getCurrentUser() {
		return userTokenThreadLocal.get();
	}

	public static Long getUserId() {
		UserToken userToken = getCurrentUser();
		if (userToken != null) {
			return userToken.getUid();
		}
		return null;
	}

	public static void clear() {
		userTokenThreadLocal.remove();
	}
}

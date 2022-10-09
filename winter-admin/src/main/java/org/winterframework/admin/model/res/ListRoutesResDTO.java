package org.winterframework.admin.model.res;

import lombok.Data;

import java.util.List;

/**
 * @author qinglinl
 * Created on 2022/10/8 12:04 PM
 */
@Data
public class ListRoutesResDTO {
	private String router;

	private List<RouterChildren> children;

	@Data
	public static class RouterChildren {
		private String router;

		private List<String> children;

		private String name;

		private String icon;

		private String path;

		private String link;
	}
}

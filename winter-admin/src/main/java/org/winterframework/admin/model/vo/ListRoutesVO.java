package org.winterframework.admin.model.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author qinglinl
 * Created on 2022/10/8 12:04 PM
 */
@Getter
@Setter
public class ListRoutesVO {
	private String router;

	private List<RouterChildren> children;

	@Getter
	@Setter
	public static class RouterChildren {
		private String router;

		private List<String> children;

		private String name;

		private String icon;

		private String path;

		private String link;
	}
}

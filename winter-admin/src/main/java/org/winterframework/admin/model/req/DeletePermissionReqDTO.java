package org.winterframework.admin.model.req;

import lombok.Data;

import java.util.List;

/**
 * @author qinglinl
 * Created on 2022/10/8 5:56 PM
 */
@Data
public class DeletePermissionReqDTO {
	private List<Long> ids;
}

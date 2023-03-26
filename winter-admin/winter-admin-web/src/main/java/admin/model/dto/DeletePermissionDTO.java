package admin.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author qinglinl
 * Created on 2022/10/8 5:56 PM
 */
@Getter
@Setter
public class DeletePermissionDTO {
	private List<Long> ids;
}

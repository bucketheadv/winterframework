package admin.model.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author qinglinl
 * Created on 2022/10/11 9:12 AM
 */
@Getter
@Setter
public class ListUserDTO {
	private Integer pageNum = 1;

	private Integer pageSize = 10;
}

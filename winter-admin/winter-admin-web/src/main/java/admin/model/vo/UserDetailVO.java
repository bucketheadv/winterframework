package admin.model.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author qinglinl
 * Created on 2022/9/30 2:57 PM
 */
@Getter
@Setter
public class UserDetailVO {
	private Long id;

	private String email;

	private Date createTime;

	private Date updateTime;
}

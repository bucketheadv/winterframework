package org.winterframework.web.dao.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Table(name = "t_user")
public class UserEntity {
    @Id
    private Long id;

    private String mobile;
}

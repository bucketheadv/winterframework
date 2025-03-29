package org.winterframework.web.dao.entity;

import lombok.Getter;
import lombok.Setter;
import org.winterframework.crypto.annotation.EncryptField;

import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Table(name = "t_user")
public class UserEntity {
    @Id
    private Long id;

    @EncryptField
    private String username;

    private Integer age;

    private Integer gender;

    @EncryptField
    private String address;
}

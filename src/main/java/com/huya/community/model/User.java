package com.huya.community.model;

import lombok.Data;

/**
 * @author niuting
 */
@Data
public class User {
    private Integer id;
    private String name;
    private String accountId;
    private String token;
    private Long gmtCreate;
    private Long gmtModified;
}

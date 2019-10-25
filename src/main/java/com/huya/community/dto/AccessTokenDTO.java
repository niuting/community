package com.huya.community.dto;

import lombok.Data;

/**
 * @author niuting
 */
@Data
public class AccessTokenDTO {
    private String client_id;
    private String client_secret;
    private String  code;
    private String redirect_uri;
    private String state;

}

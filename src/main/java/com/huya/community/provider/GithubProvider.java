package com.huya.community.provider;

import com.alibaba.fastjson.JSON;
import com.huya.community.dto.AccessTokenDTO;
import com.huya.community.dto.GithubUser;
import okhttp3.*;
import okio.BufferedSink;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;

/**
 * @author niuting
 */
@Component
public class GithubProvider {
    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new RequestBody() {
            @Nullable
            @Override
            public MediaType contentType() {
                return mediaType ;
            }

            @Override
            public void writeTo(@NotNull BufferedSink bufferedSink) throws IOException {
                bufferedSink.write(JSON.toJSONString(accessTokenDTO).getBytes());
            }
        };

        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(requestBody)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String body = response.body().string();
            String token = StringUtils.split(StringUtils.split(body, "&")[0], "=")[1];
            return token;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null ;
    }

    public GithubUser getUser(String accessToken)
    {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token=" + accessToken)
                .build();

        try (Response response = client.newCall(request).execute()) {
            String body = response.body().string() ;
            GithubUser user = JSON.parseObject(body, GithubUser.class);
            return user;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}

package com.vapers.communityservice.client;

import com.vapers.communityservice.vo.ResponseUser;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("user-service")
public interface UserServiceClient {

    ResponseUser getUser(String userToken);
}

package com.dynamodb.service.services;

import com.dynamodb.service.models.IdMUser;
import com.dynamodb.service.models.IdMUserWithRole;

import java.util.List;


public interface IdMUserService {
    public IdMUserWithRole readIdMUser(String accountId);
    public List<IdMUserWithRole> readAllIdMUsers(List<String> accountIds);
    public IdMUser createIdMUser(IdMUser idMUser);
    public IdMUser updateIdMUser(IdMUser idMUser);
    public void deleteIdMUser(String accountId);

    public IdMUserWithRole addIdMUserRoleToIdmUser(String accountId, String role);
}

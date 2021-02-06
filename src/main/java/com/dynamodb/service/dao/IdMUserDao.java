package com.dynamodb.service.dao;

import com.dynamodb.service.models.IdMUser;
import com.dynamodb.service.models.IdMUserWithRole;

import java.util.List;

public interface IdMUserDao {
    public IdMUserWithRole readIdMUser(String accountId);
    public List<IdMUserWithRole> readAllIdMUsers(List<String> accountIds);
    public IdMUser createIdMUser(IdMUser idMUser);
    public IdMUser updateIdMUser(IdMUser idMUser);
    public IdMUserWithRole updateIdMUserWithRole(IdMUserWithRole idMUserWithRole);
    public void deleteIdMUser(String accountId);
}

package com.dynamodb.service.services;

import com.dynamodb.service.dao.IdMUserDao;
import com.dynamodb.service.models.IdMUser;
import com.dynamodb.service.models.IdMUserWithRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class IdMUserServiceImpl implements IdMUserService {

    @Autowired
    private IdMUserDao idMUserDao;

    @Override
    public IdMUserWithRole readIdMUser(String accountId) {
        return idMUserDao.readIdMUser(accountId);
    }

    @Override
    public List<IdMUserWithRole> readAllIdMUsers(List<String> accountIds) {
        return idMUserDao.readAllIdMUsers(accountIds);
    }

    @Override
    public IdMUser createIdMUser(IdMUser idMUser) {
        return idMUserDao.createIdMUser(idMUser);
    }

    @Override
    public IdMUser updateIdMUser(IdMUser idMUser) {
        return idMUserDao.updateIdMUser(idMUser);
    }

    @Override
    public void deleteIdMUser(String accountId) {
        idMUserDao.deleteIdMUser(accountId);
    }

    @Override
    public IdMUserWithRole addIdMUserRoleToIdmUser(String accountId, String role) {
        IdMUserWithRole idMUserWithRole = readIdMUser(accountId);
        List<String> rolesToUpdate = idMUserWithRole.getRoles();
        if(rolesToUpdate == null) {
            rolesToUpdate = new ArrayList<>();
            rolesToUpdate.add(role);
        } else {
            rolesToUpdate.add(role);
        }
        idMUserWithRole.setRoles(rolesToUpdate);
        return idMUserDao.updateIdMUserWithRole(idMUserWithRole);
    }
}

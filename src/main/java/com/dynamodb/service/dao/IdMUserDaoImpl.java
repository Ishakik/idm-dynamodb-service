package com.dynamodb.service.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDeleteExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.dynamodb.service.models.IdMUser;
import com.dynamodb.service.models.IdMUserWithRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class IdMUserDaoImpl implements IdMUserDao {
    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    @Override
    public IdMUserWithRole readIdMUser(String accountId) {
        log.info("started reading user from dynamodb");
        return dynamoDBMapper.load(IdMUserWithRole.class, accountId);
    }

    @Override
    public List<IdMUserWithRole> readAllIdMUsers(List<String> accountIds) {
        Map<String, AttributeValue> eav = new HashMap();
        List<String> placeHolders = new ArrayList<>();
        int count = 1;
        for(String accountId: accountIds) {
            String placeHolder = ":accountId" + count++;
            eav.put(placeHolder, new AttributeValue().withS(accountId));
            placeHolders.add("accountId="+ placeHolder);
        }
        String filterExpression = String.join(" or ", placeHolders);
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression().withFilterExpression(filterExpression).withExpressionAttributeValues(eav);
        return dynamoDBMapper.scan(IdMUserWithRole.class, scanExpression);
    }


    @Override
    public IdMUser createIdMUser(IdMUser idMUser) {
        dynamoDBMapper.save(idMUser);
        return idMUser;
    }

    @Override
    public IdMUser updateIdMUser(IdMUser idMUser) {
        Map<String, ExpectedAttributeValue> expectedAttributeValueMap = new HashMap<>();
        expectedAttributeValueMap.put("accountId", new ExpectedAttributeValue(new AttributeValue().withS(idMUser.getAccountId())));
        DynamoDBSaveExpression saveExpression = new DynamoDBSaveExpression().withExpected(expectedAttributeValueMap);
        dynamoDBMapper.save(idMUser, saveExpression);
        return idMUser;
    }

    @Override
    public IdMUserWithRole updateIdMUserWithRole(IdMUserWithRole idMUserWithRole) {
        Map<String, ExpectedAttributeValue> expectedAttributeValueMap = new HashMap<>();
        expectedAttributeValueMap.put("accountId", new ExpectedAttributeValue(new AttributeValue().withS(idMUserWithRole.getAccountId())));
        DynamoDBSaveExpression saveExpression = new DynamoDBSaveExpression().withExpected(expectedAttributeValueMap);
        dynamoDBMapper.save(idMUserWithRole, saveExpression);
        return idMUserWithRole;
    }

    @Override
    public void deleteIdMUser(String accountId) {
        Map<String, ExpectedAttributeValue> expectedAttributeValueMap = new HashMap<>();
        expectedAttributeValueMap.put("accountId", new ExpectedAttributeValue(new AttributeValue().withS(accountId)));
        DynamoDBDeleteExpression deleteExpression = new DynamoDBDeleteExpression().withExpected(expectedAttributeValueMap);
        IdMUserWithRole idMUserWithRole = IdMUserWithRole.builder()
                .accountId(accountId)
                .build();
        dynamoDBMapper.delete(idMUserWithRole, deleteExpression);
    }
}

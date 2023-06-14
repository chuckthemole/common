package com.rumpus.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;


// TODO think about adding protected methods, addKey setKey etc. would this be benificial?
/**
 * Wrapper around KeyHolder
 */
public class CommonKeyHolder extends RumpusObject implements KeyHolder {

    public static final String NAME = "CommonKeyHolder";
    private GeneratedKeyHolder key;
    // private List<Map<String, Object>> keyList;

    public CommonKeyHolder() {
        super(NAME);
        this.key = new GeneratedKeyHolder();
    }
    public CommonKeyHolder(List<Map<String, Object>> keyList) {
        super(NAME);
        this.key = new GeneratedKeyHolder(keyList);
    }

    @Override
    public Number getKey() throws InvalidDataAccessApiUsageException {
        return this.key.getKey();
    }

    @Override
    public <T> T getKeyAs(Class<T> keyType) throws InvalidDataAccessApiUsageException {
        return this.key.getKeyAs(keyType);
    }

    @Override
    public Map<String, Object> getKeys() throws InvalidDataAccessApiUsageException {
        return this.key.getKeys();
    }

    @Override
    public List<Map<String, Object>> getKeyList() {
        return this.key.getKeyList();
    }
    
}

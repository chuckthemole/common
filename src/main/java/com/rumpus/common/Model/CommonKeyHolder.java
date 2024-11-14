package com.rumpus.common.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.rumpus.common.AbstractCommonObject;


// TODO think about adding protected methods, addKey setKey etc. would this be benificial?
/**
 * Wrapper around KeyHolder
 */
public class CommonKeyHolder extends AbstractCommonObject implements KeyHolder {

    private GeneratedKeyHolder key;
    // private List<Map<String, Object>> keyList;

    public CommonKeyHolder() {
        this.key = new GeneratedKeyHolder();
    }
    public CommonKeyHolder(List<Map<String, Object>> keyList) {
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
    @Override
    public String toString() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toString'");
    }
    
}

package com.rumpus.common.User;

import java.io.IOException;
import java.io.OutputStream;

import com.google.gson.TypeAdapter;
import com.rumpus.common.Model.AbstractModel;

public class EmptyUser<USER extends AbstractModel<USER>, META extends AbstractCommonUserMetaData<META>> extends AbstractCommonUser<USER, META> {

    private final static String NAME = "EmptyUser";

    private EmptyUser() {
        super(NAME);
        this.setEmail("EMPTY_EMAIL");
        this.setUsername("EMPTY_USERNAME");
        this.setUserDetails(CommonUserDetails.createEmptyUserDetails());
        this.setMetaData(EmptyUserMetaData.createEmptyUserMetaData());
        this.setPassword("EMPTY_PASSWORD");
        this.setTypeAdapter(null);
    }

    public static <USER extends AbstractModel<USER>, META extends AbstractCommonUserMetaData<META>> EmptyUser<USER, META> createEmptyUser() {
        return new EmptyUser<USER, META>();
    }

    @Override
    public void serialize(USER object, OutputStream outputStream) throws IOException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'serialize'");
    }

    @Override
    public TypeAdapter<USER> createTypeAdapter() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createTypeAdapter'");
    }

    
}

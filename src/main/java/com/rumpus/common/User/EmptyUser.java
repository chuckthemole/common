package com.rumpus.common.User;

import java.io.IOException;
import java.io.OutputStream;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.rumpus.common.Model.IModelIdManager;
import com.rumpus.common.Model.SqlIdManager;

/**
 * Empty user object
 * 
 * TODO: document use case or maybe try and remove this class
 */
public class EmptyUser<
        USER extends AbstractCommonUser<USER, META>,
        META extends AbstractCommonUserMetaData<META>
    > extends AbstractCommonUser<USER, META> {

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

        public static <USER extends AbstractCommonUser<USER, META>, META extends AbstractCommonUserMetaData<META>> EmptyUser<USER, META> createEmptyUser() {
            return new EmptyUser<USER, META>();
        }

        @Override
        public void serialize(USER object, OutputStream outputStream) throws IOException {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'serialize'");
        }

        @Override
        public TypeAdapter<USER> createTypeAdapter() {
            return new TypeAdapter<USER>() {

                @Override
                public void write(JsonWriter out, USER value) throws IOException {
                    // TODO Auto-generated method stub
                    throw new UnsupportedOperationException("Unimplemented method 'write' - EmptyUser");
                }

                @Override
                public USER read(JsonReader in) throws IOException {
                    // TODO Auto-generated method stub
                    throw new UnsupportedOperationException("Unimplemented method 'read' - EmptyUser");
                }
                
            };
        }

        @Override
        public IModelIdManager getIdManager() {
            return new SqlIdManager();
        }

    
}

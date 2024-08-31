package com.rumpus.common.Dao;

public interface IUserDao
<
    USER extends com.rumpus.common.User.AbstractCommonUser<USER, META>,
    META extends com.rumpus.common.User.AbstractCommonUserMetaData<META>
> extends IDao<USER> {
    // public boolean remove(int id); in IDao
    public boolean remove(String name);
    // public USER get(int id); in IDao
    // public USER get(String name); in IDao
    // public java.util.List<USER> get(java.util.Map<String, String> constraints); in IDao
    // public java.util.List<USER> getAll(); in IDao
    // public USER add(USER newUser); in IDao
	// public USER update(String id, USER newUser); in IDao
    // public boolean removeAll();
    public void setQueriesFromMap(java.util.Map<String, String> queries);

    // Keeping these here for now. 
    // private USER addUser(USER newUser);
    // private USER simpleAddUser(USER newUser);
    // private java.sql.Blob serializeUserMetaWithCommonBlob(META meta);
    // private java.sql.Blob serializeUserMetaWithClassSerializer(META meta);
    // private void setQueries(Map<String, String> queries);
    // private void setDefaultQueries();
}


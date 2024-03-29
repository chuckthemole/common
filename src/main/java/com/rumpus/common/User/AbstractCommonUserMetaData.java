package com.rumpus.common.User;

import java.io.IOException;

import com.rumpus.common.Builder.LogBuilder;
import com.rumpus.common.Builder.StringBuilderHelper;
import com.rumpus.common.Model.AbstractMetaData;

/**
 * TODO: let's make an interface, ICommonUserMetaData, and have this class implement it. 2024/1/15 - chuck
 */
public abstract class AbstractCommonUserMetaData<USER_META extends AbstractCommonUserMetaData<USER_META>> extends AbstractMetaData<USER_META> {

    private static final long serialVersionUID = USER_META_DATA_UID;

    public static final String NAME = "CommonUserMetaData";
    public static final String USER_PHOTO_LINK = "user_photo_link";
    public static final String USER_ABOUT_ME = "user_about_me";

    protected String photoLink;
    protected String aboutMe;
    
    public AbstractCommonUserMetaData() {
        super(NAME);
        this.init();
    }
    public AbstractCommonUserMetaData(String name) {
        super(name);
        this.init();
    }
    public AbstractCommonUserMetaData(String name, String creationTime) {
        super(name, creationTime);
        this.init();
    }

    private void init() {
        this.photoLink = EMPTY_FIELD;
        this.aboutMe = EMPTY_FIELD;
    }

    public void setPhotoLink(String photoLink) {
        this.photoLink = photoLink;
    }

    public String getPhotoLink() {
        return this.photoLink;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public String getAboutMe() {
        return this.aboutMe;
    }

    // overriding these serializer methods here. right now just using defaults but can customize as commented out below. 2023/6/28
    private void writeObject(java.io.ObjectOutputStream out) throws IOException {
        LOG("AbstractCommonUserMetaData::writeObject()");
        out.defaultWriteObject();
    }
    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        LOG("AbstractCommonUserMetaData::readObject()");
        in.defaultReadObject();
    }

    @Override 
    public String toString() {
        return StringBuilderHelper.buildString(
            "{\n",
            "  ", USER_CREATION_DATE_TIME, ": ", this.getStandardFormattedCreationTime(), ",\n",
            "  ", USER_PHOTO_LINK, ": ", this.getPhotoLink(), ",\n",
            "  ", USER_ABOUT_ME, ": ", this.getAboutMe(), "\n",
            "}"
        );
    }
}

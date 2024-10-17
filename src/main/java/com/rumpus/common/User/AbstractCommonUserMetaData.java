package com.rumpus.common.User;

import java.io.IOException;

import com.rumpus.common.Builder.StringBuilderHelper;
import com.rumpus.common.Model.AbstractMetaData;

/**
 * TODO: let's make an interface, ICommonUserMetaData, and have this class implement it. 2024/1/15 - chuck
 */
public abstract class AbstractCommonUserMetaData<USER_META extends AbstractCommonUserMetaData<USER_META>> extends AbstractMetaData<USER_META> {

    private static final long serialVersionUID = USER_META_DATA_UID;

    transient public static final String USER_PHOTO_LINK = "user_photo_link";
    transient public static final String USER_ABOUT_ME = "user_about_me";

    protected String photoLink;
    protected String aboutMe;
    
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
    protected void writeObject(java.io.ObjectOutputStream out) throws IOException {
        LOG("AbstractCommonUserMetaData::writeObject()");
        out.defaultWriteObject();
    }
    protected void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        LOG("AbstractCommonUserMetaData::readObject()");
        try {
            in.defaultReadObject();
        } catch (IOException | ClassNotFoundException e) {
            LOG("TODO: catch this error");
            LOG(e.getMessage());
        }
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

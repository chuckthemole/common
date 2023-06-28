package com.rumpus.common.User;

import com.rumpus.common.MetaData;
import com.rumpus.common.Builder.StringBuilderHelper;

/**
 * 
 */
public abstract class CommonUserMetaData<USER_META extends CommonUserMetaData<USER_META>> extends MetaData<USER_META> {

    private static final long serialVersionUID = USER_META_DATA_UID;

    public static final String NAME = "CommonUserMetaData";
    public static final String USER_PHOTO_LINK = "user_photo_link";
    public static final String USER_ABOUT_ME = "user_about_me";

    protected String photoLink;
    protected String aboutMe;
    
    public CommonUserMetaData() {
        super(NAME);
    }
    public CommonUserMetaData(String name) {
        super(name);
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

package com.rumpus.common.Model.User;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.rumpus.common.Builder.LogBuilder;
import com.rumpus.common.User.AbstractCommonUserMetaData;

public class TestUserModelMetaData extends AbstractCommonUserMetaData<TestUserModelMetaData> {

    private static final long serialVersionUID = TEST_USER_META_DATA_UID;

    // TODO: add more member variables for specific meta data here

    // ctors
    private TestUserModelMetaData(List<Map<String, String>> metaList) {
        super(null, null, null);
        this.init(metaList);
    }
    private TestUserModelMetaData(Map<String, String> metaMap) {
        super(null, null, null);
        this.init(List.of(metaMap));
    }

    // factory static ctors
    public static TestUserModelMetaData createEmpty() {
        return new TestUserModelMetaData(List.of());
    }
    public static TestUserModelMetaData createFromListOfMaps(List<Map<String, String>> metaList) {
        return new TestUserModelMetaData(metaList);
    }
    public static TestUserModelMetaData createFromMap(Map<String, String> metaMap) {
        return new TestUserModelMetaData(metaMap);
    }

    // TODO: this isn't really doing anything right now. 2024/1/22 - chuck
    // it's just printing out the metaList that is passed in
    private void init(List<Map<String, String>> metaList) {
        String log = LogBuilder.logBuilderFromStringArgs(
            TestUserModelMetaData.class,
            "TestUserModelMetaData::init()").toString();
        LOG(log);
        // TestUserModelMetaDataSerializer serializer = new TestUserModelMetaDataSerializer();
        if(metaList == null) {
            log = LogBuilder.logBuilderFromStringArgsNoSpaces(
                TestUserModelMetaData.class,
                "Provided metaList is null").toString();
            LOG(log);
        } else if(metaList.isEmpty()) {
            log = LogBuilder.logBuilderFromStringArgsNoSpaces(
                TestUserModelMetaData.class,
                "Provided metaList is empty").toString();
        } else {
            for(Map<String, String> map : metaList) {
                log = LogBuilder.logBuilderFromStringArgs(
                    TestUserModelMetaData.class,
                    "New Map:").toString();
                LOG(log);
                map.forEach((key, value) -> {
                    final String mapLog = LogBuilder.logBuilderFromStringArgs(
                        TestUserModelMetaData.class,
                        "  ",
                        key,
                        value,
                        "\n").toString();
                    LOG(mapLog);
                });
            }
        }
    }

    // overriding these serializer methods here. right now just using defaults but can customize as commented out below. 2023/6/28
    protected void writeObject(java.io.ObjectOutputStream out) throws IOException {
        LOG("TestUserModelMetaData::writeObject()");
        out.defaultWriteObject();
        // out.writeObject(this.getCreationTime());
        // out.writeChars(this.photoLink);
        // out.writeChars(this.aboutMe);
    }
    protected void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        LOG("TestUserModelMetaData::readObject()");
        in.defaultReadObject();
        // try {
        //     this.creationTime = (Instant) stream.readObject();
        //     this.photoLink = (String) stream.readObject();
        //     this.aboutMe = (String) stream.readObject();
        // } catch (ClassNotFoundException e) {
        //     LogBuilder.logBuilderFromStringArgs(e.getMessage()).error();
        //     LogBuilder.logBuilderFromStackTraceElementArray(e.getStackTrace()).error();
        // } catch (IOException e) {
        //     LogBuilder.logBuilderFromStringArgs(e.getMessage()).error();
        //     LogBuilder.logBuilderFromStackTraceElementArray(e.getStackTrace()).error();
        // }
    }

    @Override
    public Map<String, Object> getMetaAttributesMap() {
        LOG("TestUserModelMetaData::getModelAttributesMap()");
        Map<String, Object> modelAttributesMap = Map.of(
            USER_CREATION_DATE_TIME, (String) this.getStandardFormattedCreationTime(),
            USER_PHOTO_LINK, (String) this.getPhotoLink(),
            USER_ABOUT_ME, (String) this.getAboutMe());
        return modelAttributesMap;
    }
}


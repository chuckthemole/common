package com.rumpus.common.Manager;

import com.rumpus.common.Builder.LogBuilder;

public abstract class AbstractSetItem extends com.rumpus.common.AbstractCommonObject implements ISetItem {

    private String uniqueId = null;

    public AbstractSetItem() {}

    @Override
    public void setUniqueId(String id) {
        if (id == null) {
            LOG_THIS("Failed to set unique id: ", id);
            return;
        }
        this.uniqueId = id;
    }

    @Override
    public String getUniqueId() {
        return this.uniqueId;
    }

    @Override
    public boolean hasUniqueId() {
        return this.uniqueId != null;
    }

    private void LOG_THIS(String... args) {
        final String log = LogBuilder.logBuilderFromStringArgsNoSpaces(AbstractSetItem.class, args).toString();
        LOG(log);
    }
}

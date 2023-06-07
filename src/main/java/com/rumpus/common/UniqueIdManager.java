package com.rumpus.common;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;

import com.rumpus.common.util.Random;

// @Data
// @Entity
// @NoArgsConstructor
// @AllArgsConstructor
public class UniqueIdManager extends RumpusObject implements Serializable { // TODO should this be Serializable?

    private static final String NAME = "UniqueIdManager";
    private Set<String> uniqueIds;
    private final int idLength;
    private static final int DEFAULT_ID_LENGTH = 10;

    public UniqueIdManager() {
        super(NAME);
        this.uniqueIds = new HashSet<>();
        this.idLength = DEFAULT_ID_LENGTH;
    }

    public UniqueIdManager(final int idLength) {
        super(NAME);
        this.uniqueIds = new HashSet<>();
        this.idLength = idLength;
    }

    public String add() {
        String id;
        do {
            id = Random.alphaNumericUpper(this.idLength);
        } while(this.uniqueIds.contains(id));
        return id;
    }

    public void remove(final String id) {
        this.uniqueIds.remove(id);
    }
}

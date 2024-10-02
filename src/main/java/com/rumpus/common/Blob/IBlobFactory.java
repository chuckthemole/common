package com.rumpus.common.Blob;

import java.sql.Blob;

// TODO:
// Consider using a factory or builder pattern for Blob creation, especially if Blob is complex to construct.
//This will centralize the construction logic, making the class cleaner and decoupling Blob creation from the class itself.
public interface IBlobFactory {
    public Blob createBlob(String data);
}

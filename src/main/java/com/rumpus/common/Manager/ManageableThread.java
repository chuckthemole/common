package com.rumpus.common.Manager;

import com.rumpus.common.AbstractCommonObject;

abstract public class ManageableThread<MANAGEE extends AbstractCommonObject> extends Thread implements IManageable {

    private MANAGEE managee;

    public ManageableThread(Runnable runnable, MANAGEE managee) {
        super(runnable);
        this.managee = managee;
    }

    public MANAGEE getManagee() {
        return this.managee;
    }

    public void setManagee(MANAGEE managee) {
        this.managee = managee;
    }

    /**
     * Stop the thread.
     * 
     * @return true if the thread was stopped, false otherwise.
     */
    abstract public boolean stopThread();
}

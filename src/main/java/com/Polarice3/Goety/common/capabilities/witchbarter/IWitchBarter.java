package com.Polarice3.Goety.common.capabilities.witchbarter;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;

public interface IWitchBarter extends AutoSyncedComponent {

    int getTimer();

    void setTimer(int timer);

    int getTraderID();

    void setTraderID(int id);
}

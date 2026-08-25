package com.Polarice3.Goety.common.capabilities.lichdom;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;

public interface ILichdom extends AutoSyncedComponent {

    boolean getLichdom();

    void setLichdom(boolean lichdom);

    boolean isLichMode();

    void setLichMode(boolean lichMode);

    boolean nightVision();

    void setNightVision(boolean nightVision);

    int smited();

    void setSmited(int smited);

    int lichModeColor();

    void setLichModeColor(int colorCode);
}

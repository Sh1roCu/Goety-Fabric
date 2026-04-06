package cn.sh1rocu.goety.api.extension;

public interface IEntityListener {

    boolean isAddedToWorld();

    void onAddedToWorld();

    void onRemovedFromWorld();
}

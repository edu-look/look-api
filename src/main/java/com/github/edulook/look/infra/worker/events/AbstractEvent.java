package com.github.edulook.look.infra.worker.events;

public abstract class AbstractEvent {

    public abstract boolean isValid();
    public abstract boolean isNotValid();
}

package net.mommymarlow.marlowclient.event.impl;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.mommymarlow.marlowclient.event.Event;


public class AttackEntityEvent extends Event {
    private Entity target;
    private PlayerEntity player;

    public AttackEntityEvent(PlayerEntity player, Entity target) {
        this.target = target;
        this.player = player;
    }

    public Entity getTarget() {
        return target;
    }

    public PlayerEntity getPlayer() {
        return player;
    }

    public void setPlayer(PlayerEntity player) {
        this.player = player;
    }

    public void setTarget(Entity target) {
        this.target = target;
    }
}
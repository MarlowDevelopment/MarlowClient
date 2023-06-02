package net.mommymarlow.marlowclient.event.impl;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.HitResult;
import net.mommymarlow.marlowclient.event.Event;

public class PlayerAttackEntityEvent extends Event {
    private final PlayerEntity player;
    private final Entity entity;
    private final HitResult hitResult;

    public PlayerAttackEntityEvent(PlayerEntity player, Entity entity, HitResult hitResult) {
        this.player = player;
        this.entity = entity;
        this.hitResult = hitResult;
    }

    public PlayerEntity getPlayer() {
        return player;
    }

    public Entity getEntity() {
        return entity;
    }

    public HitResult getHitResult() {
        return hitResult;
    }


}

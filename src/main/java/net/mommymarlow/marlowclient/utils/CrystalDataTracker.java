package net.mommymarlow.marlowclient.utils;

import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.mommymarlow.marlowclient.event.EventTarget;
import net.mommymarlow.marlowclient.event.impl.EventEntity;

import java.util.ArrayList;
import java.util.HashMap;

public class CrystalDataTracker {
    public static final CrystalDataTracker INSTANCE = new CrystalDataTracker();


    private final ArrayList<Entity> crystalsInWorld = new ArrayList<>();
    private final HashMap<Entity, Integer> crystalAges = new HashMap<>();
    private final HashMap<Entity, Boolean> crystalAttacked = new HashMap<>();
    private final HashMap<Entity, Integer> crystalSinceLastAttack = new HashMap<>();

    public void recordAttack(Entity entity) {
        if (!(entity instanceof EndCrystalEntity))
            return;

        crystalAttacked.remove(entity);
        crystalAttacked.put(entity, true);
    }

    public int getCrystalAge(Entity crystal) {
        return crystalAges.get(crystal);
    }

    public boolean isCrystalAttacked(Entity crystal) {
        return crystalAttacked.get(crystal);
    }

    public int sinceLastAttack(Entity crystal) {
        return crystalSinceLastAttack.get(crystal);
    }

    public CrystalDataTracker() {
    }

    @EventTarget
    public void onEntityDespawn(EventEntity.Remove event, Entity entity) {
        if (!(entity instanceof EndCrystalEntity))
            return;

        crystalsInWorld.removeIf(e -> e == entity);
        crystalAges.remove(entity);
        crystalAttacked.remove(entity);
        crystalSinceLastAttack.remove(entity);
    }

    @EventTarget
    public void onEntitySpawn(EventEntity.Spawn event, Entity entity) {
        if (!(entity instanceof EndCrystalEntity))
            return;

        crystalsInWorld.add(entity);
        crystalAges.put(entity, 0);
        crystalAttacked.put(entity, false);
        crystalSinceLastAttack.put(entity, 0);
    }

    public void onTick() {
        crystalsInWorld.forEach(entity ->
        {
            crystalAges.merge(entity, 1, Integer::sum);
            crystalSinceLastAttack.merge(entity, 1, (v1, v2) ->
            {
                if (crystalAttacked.get(entity))
                    return v1 + v2;
                return 0;
            });
//			if (unmarkBrokenCrystalDelay.getValue() != 0)
//				crystalAttacked.merge(entity, false, (v1, v2) ->
//				{
//					if (crystalSinceLastAttack.get(entity) > unmarkBrokenCrystalDelay.getValue())
//						return false;
//					return v1;
//				});
        });
    }
}
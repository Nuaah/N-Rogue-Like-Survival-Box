package com.Nuaah.NRogueLikeSurvivalBox.regi;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class StatusPuzzleManager {

    private static final Map<UUID, Map<String, Float>> SPE_SPECIAL = new HashMap<>();

    // 保存するメソッド
    public static void saveEffect(UUID uuid, String effectName, float value) {
        Map<String, Float> effects = SPE_SPECIAL.computeIfAbsent(uuid, k -> new HashMap<>());

        effects.merge(effectName, value, (oldValue, newValue) -> oldValue + newValue);
    }

    // 呼び出すメソッド
    public static float getEffectValue(UUID uuid, String effectName) {
        if (SPE_SPECIAL.containsKey(uuid)) {
            return SPE_SPECIAL.get(uuid).getOrDefault(effectName, 0F);
        }
        return 0F;
    }

    //特定のプレイヤーの全データを削除
    public static void removePlayer(UUID uuid) {
        SPE_SPECIAL.remove(uuid);
    }
}

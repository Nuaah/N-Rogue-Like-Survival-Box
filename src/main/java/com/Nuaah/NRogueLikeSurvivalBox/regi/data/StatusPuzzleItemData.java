package com.Nuaah.NRogueLikeSurvivalBox.regi.data;

import java.util.List;
import java.util.Map;

public class StatusPuzzleItemData {

    public String item;
    public List<effects> effects;
    public List<special> specials;
    public List<slotBonus> slotBonus;

    public static class effects {
        public String name;
        public float value;
    }

    public static class special {
        public String name;
        public float value;
    }

    public static class slotBonus {
        public Map<String, keyEntry> key;
        public String[] pattern;
    }

    public static class keyEntry {
        public String name;
    }


}

package com.Nuaah.NRogueLikeSurvivalBox.regi.data;

import java.util.List;

public class BagLootTableData {

    public String entity;
    public List<pools> pools;

    public static class pools {
        public String condition;
        public int level;
        public List<entries> entries;
    }

    public static class entries {
        public String id;
        public int min;
        public int max;
        public int weight;
    }
}

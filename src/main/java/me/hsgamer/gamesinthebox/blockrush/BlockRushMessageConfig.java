package me.hsgamer.gamesinthebox.blockrush;

import me.hsgamer.hscore.config.annotation.ConfigPath;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public interface BlockRushMessageConfig {
    @ConfigPath("display-name")
    default String getDisplayName() {
        return "Block Rush";
    }

    @ConfigPath("default-hologram-lines")
    default Map<String, Object> getDefaultHologramLines() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("description", Arrays.asList(
                "&c&lBLOCK RUSH",
                "&fYou will mine blocks to get points"
        ));
        map.put("points", Collections.singletonList(
                "&fPoints when you mine the block: &a{game_point_block}"
        ));
        map.put("top", Arrays.asList(
                "&a#1 &f{game_top_name_1} &7- &f{game_top_value_1}",
                "&a#2 &f{game_top_name_2} &7- &f{game_top_value_2}",
                "&a#3 &f{game_top_name_3} &7- &f{game_top_value_3}",
                "&a#4 &f{game_top_name_4} &7- &f{game_top_value_4}",
                "&a#5 &f{game_top_name_5} &7- &f{game_top_value_5}"
        ));
        map.put("status", Arrays.asList(
                "&fStatus: &a{planner_game_state}",
                "&fTime left: &a{game_time_left}"
        ));
        return map;
    }

    void reloadConfig();
}

package se_ii.gruppe2.moving_maze.item;

import java.util.ArrayList;
import java.util.List;

public class ItemTheme {

    private ItemTheme(){
    }

    public static String getItemThemePath(String theme){
        switch(theme){
            case "Original": return "items/original";
            case "Emoji": return "items/emoji";
            default: return "items/original";
        }
    }

    public static List<String> getAvailableThemes() {
        var availableThemes = new ArrayList<String>();
        availableThemes.add("Original");
        availableThemes.add("Emoji");
        return availableThemes;
    }
}

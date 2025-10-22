package MapEditor;

import Level.Map;
import Maps.NewRoom_1;
import Maps.NewRoom_2;
import Maps.TestMap;
import Maps.TitleScreenMap;

import java.util.ArrayList;

public class EditorMaps {
    public static ArrayList<String> getMapNames() {
        return new ArrayList<String>() {{
            add("TestMap");
            add("TitleScreen");
            add("NewRoom_1");
            add("NewRoom_2");
        }};
    }

    public static Map getMapByName(String mapName) {
        switch(mapName) {
            case "TestMap":
                return new TestMap();
            case "TitleScreen":
                return new TitleScreenMap();
            case "NewRoom_1":
                return new NewRoom_1();
            case "NewRoom_2":
                return new NewRoom_2();
            default:
                throw new RuntimeException("Unrecognized map name");
        }
    }
}

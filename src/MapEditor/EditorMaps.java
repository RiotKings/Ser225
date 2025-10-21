package MapEditor;

import Level.Map;
import Maps.TestMap;
import Maps.TitleScreenMap;
import Maps.FirstRoom;
import Maps.Floor1Room0;
import Maps.Floor1Room1;
import Maps.Floor1Room2;

import java.util.ArrayList;

public class EditorMaps {
    public static ArrayList<String> getMapNames() {
        return new ArrayList<String>() {{
            add("TestMap");
            add("TitleScreen");
            add("FirstRoom");
            add("Floor1Room0");
            add("Floor1Room1");
            add("Floor1Room2");
        }};
    }

    public static Map getMapByName(String mapName) {
        switch(mapName) {
            case "TestMap":
                return new TestMap();
            case "TitleScreen":
                return new TitleScreenMap();
            case "FirstRoom":
                return new FirstRoom();
            case "Floor1Room0":
                return new Floor1Room0();
            case "Floor1Room1":
                return new Floor1Room1();
            case "Floor1Room2":
                return new Floor1Room2();
            default:
                throw new RuntimeException("Unrecognized map name");
        }
    }
}

package MapEditor;

import Level.Map;
import Maps.NewRoom_1;
import Maps.NewRoom_2;
import Maps.TestMap;
import Maps.TitleScreenMap;
import Maps.FirstRoom;
import Maps.Floor1Room0;
import Maps.Floor1Room1;
import Maps.Floor1Room2;
import Maps.Floor1Room3;
import Maps.Floor1Room4;

import java.util.ArrayList;

public class EditorMaps {
    public static ArrayList<String> getMapNames() {
        return new ArrayList<String>() {{
            add("TestMap");
            add("TitleScreen");
            add("NewRoom_1");
            add("NewRoom_2");
            add("FirstRoom");
            add("Floor1Room0");
            add("Floor1Room1");
            add("Floor1Room2");
            add("Floor1Room3");
            add("Floor1Room4");
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
            case "FirstRoom":
                return new FirstRoom();
            case "Floor1Room0":
                return new Floor1Room0();
            case "Floor1Room1":
                return new Floor1Room1();
            case "Floor1Room2":
                return new Floor1Room2();
            case "Floor1Room3":
                return new Floor1Room3();
            case "Floor1Room4":
                return new Floor1Room4();

            default:
                throw new RuntimeException("Unrecognized map name");
        }
    }
}

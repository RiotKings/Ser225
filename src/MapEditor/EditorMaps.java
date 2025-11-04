package MapEditor;

import Level.Map;
import Maps.Floor1Room6;
import Maps.Floor1Room7;
import Maps.TestMap;
import Maps.TitleScreenMap;
import Maps.Floor1Room5;
import Maps.Floor1BossRoomMap;
import Maps.FirstRoom;
import Maps.Floor1Room0;
import Maps.Floor1Room1;
import Maps.Floor1Room2;
import Maps.Floor1Room3;
import Maps.Floor1Room4;

import Maps.TreasureRoom;
import Maps.Floor1Room8;
import Maps.Floor1Room9;
import Maps.Floor1Room10;
import Maps.Floor1Room11;
import Maps.Floor1Room12;
import Maps.Floor1Room13;
import Maps.Floor1Room14;
import Maps.Floor1Room15;
import Maps.Floor1Room16;
import Maps.Floor1Room17;
import Maps.Floor1Room18;


import java.util.ArrayList;

public class EditorMaps {
    public static ArrayList<String> getMapNames() {
        return new ArrayList<String>() {{
            add("TestMap");
            add("TitleScreen");
            // initial rooms
            add("RegularRoom");
            add("BossRoom");
            add("FirstRoom");
            add("Floor1Room0");
            add("Floor1Room1");
            add("Floor1Room2");
            add("Floor1Room3");
            add("Floor1Room4");
            add("Floor1Room6");
            add("Floor1Room7");
            //new rooms 
            add("TreasureRoom");
            add("Floor1Room8");
            add("Floor1Room9");
            add("Floor1Room10");
            add("Floor1Room12");
            add("Floor1Room13");
            add("Floor1Room14");
            add("Floor1Room15");
            add("Floor1Room16");
            add("Floor1Room17");
            add("Floor1Room18");

        }};
    }

    public static Map getMapByName(String mapName) {
        switch(mapName) {
            case "TestMap":
                return new TestMap();
            case "TreasureRoom":
                return new TreasureRoom();
            case "TitleScreen":
                return new TitleScreenMap();
            case "RegularRoom":
                return new Floor1Room5();
            case "BossRoom":
                return new Floor1BossRoomMap();
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
            case "Floor1Room6":
                return new Floor1Room6();
            case "Floor1Room7":
                return new Floor1Room7();
            case "Floor1Room8":
                return new Floor1Room8();
            case "Floor1Room9":
                return new Floor1Room9();
            case "Floor1Room10":
                return new Floor1Room10();
            case "Floor1Room11":
                return new Floor1Room11();
            case "Floor1Room12":
                return new Floor1Room12();
            case "Floor1Room13":
                return new Floor1Room13();
            case "Floor1Room14":
                return new Floor1Room14();
            case "Floor1Room15":
                return new Floor1Room15();
            case "Floor1Room16":
                return new Floor1Room16();
            case "Floor1Room17":
                return new Floor1Room17();
            case "Floor1Room18":
                return new Floor1Room18();    
            default:
                throw new RuntimeException("Unrecognized map name");
        }
    }
}

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
import Maps.Floor2Room0;
import Maps.Floor1Room10;
import Maps.Floor1Room11;
import Maps.Floor1Room12;
import Maps.Floor1Room13;
import Maps.Floor1Room14;
import Maps.Floor1Room15;
import Maps.Floor1Room16;
import Maps.Floor1Room17;
import Maps.Floor1Room18;

import Maps.Floor2Room0;
import Maps.Floor2Room1;
import Maps.Floor2Room2;
import Maps.Floor2Room3;
import Maps.Floor2Room4;
import Maps.Floor2Room5;
import Maps.Floor2Room6;
import Maps.Floor2Room7;
import Maps.Floor2Room8;
import Maps.Floor2Room9;
import Maps.Floor2Room10;
import Maps.Floor2Room11;
import Maps.Floor2Room12;
import Maps.Floor2Room13;
import Maps.Floor2Room14;
import Maps.Floor2Room15;
import Maps.Floor2Room16;
import Maps.Floor2Room17;
import Maps.Floor2Room18;
import Maps.Floor2Room19;
import Maps.Floor2Room20;
import Maps.Floor2Room21;
import Maps.Floor2Room22;
import Maps.Floor2Room23;
import Maps.Floor2Room24;



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
            add("Floor1Room11");
            add("Floor1Room12");
            add("Floor1Room13");
            add("Floor1Room14");
            add("Floor1Room15");
            add("Floor1Room16");
            add("Floor1Room17");
            add("Floor1Room18");


            //Floor 2 maps

            add("Floor2Room0");
            add("Floor2Room1");
            add("Floor2Room2");
            add("Floor2Room3");
            add("Floor2Room4");
            add("Floor2Room5");
            add("Floor2Room6");
            add("Floor2Room7");
            add("Floor2Room8");
            add("Floor2Room9");
            add("Floor2Room10");
            add("Floor2Room11");
            add("Floor2Room12");
            add("Floor2Room13");
            add("Floor2Room14");
            add("Floor2Room15");
            add("Floor2Room16");
            add("Floor2Room17");
            add("Floor2Room18");
            add("Floor2Room19");
            add("Floor2Room20");
            add("Floor2Room21");
            add("Floor2Room22");
            add("Floor2Room23");
            add("Floor2Room24");













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
            case "Floor2Room0":
                return new Floor2Room0();
            case "Floor2Room1":
                return new Floor2Room1();

            case "Floor2Room2":
                return new Floor2Room2();

            case "Floor2Room3":
                return new Floor2Room3();

            case "Floor2Room4":
                return new Floor2Room4();

            case "Floor2Room5":
                return new Floor2Room5();

            case "Floor2Room6":
                return new Floor2Room6();

            case "Floor2Room7":
                return new Floor2Room7();

            case "Floor2Room8":
                return new Floor2Room8();

            case "Floor2Room9":
                return new Floor2Room9();

            case "Floor2Room10":
                return new Floor2Room10();

            case "Floor2Room11":
                return new Floor2Room11();

            case "Floor2Room12":
                return new Floor2Room12();

            case "Floor2Room13":
                return new Floor2Room13();

            case "Floor2Room14":
                return new Floor2Room14();

            case "Floor2Room15":
                return new Floor2Room15();

            case "Floor2Room16":
                return new Floor2Room16();

            case "Floor2Room17":
                return new Floor2Room17();

            case "Floor2Room18":
                return new Floor2Room18();

            case "Floor2Room19":
                return new Floor2Room19();

            case "Floor2Room20":
                return new Floor2Room20();

            case "Floor2Room21":
                return new Floor2Room21();

            case "Floor2Room22":
                return new Floor2Room22();

            case "Floor2Room23":
                return new Floor2Room23();

            case "Floor2Room24":
                return new Floor2Room24();     
            default:
                throw new RuntimeException("Unrecognized map name");
        }
    }
}

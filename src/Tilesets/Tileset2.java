package Tilesets;

import Builders.FrameBuilder;
import Builders.MapTileBuilder;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.ImageEffect;
import Level.TileType;
import Level.Tileset;

import java.util.ArrayList;

// This class represents a "common" tileset of standard tiles defined in the CommonTileset.png file
public class Tileset2 extends Tileset {

    public Tileset2() {
        super(ImageLoader.load("Tileset2.png"), 16, 16, 3);
    }

     @Override
    public ArrayList<MapTileBuilder> defineTiles() {
        ArrayList<MapTileBuilder> mapTiles = new ArrayList<>();
        
        //inpassable 

        // Sanctum1
        Frame Sanctum1 = new FrameBuilder(getSubImage(0, 0))
                .withScale(tileScale)
                .build();
        MapTileBuilder SanctumTile1 = new MapTileBuilder(Sanctum1)
                .withTileType(TileType.NOT_PASSABLE);
        mapTiles.add(SanctumTile1);

        // Sanctum2
        Frame Sanctum2 = new FrameBuilder(getSubImage(0, 1))
                .withScale(tileScale)
                .build();
        MapTileBuilder SanctumTile2 = new MapTileBuilder(Sanctum2)
                .withTileType(TileType.NOT_PASSABLE);
        mapTiles.add(SanctumTile2);

        // Sanctum3
        Frame Sanctum3 = new FrameBuilder(getSubImage(0, 2))
                .withScale(tileScale)
                .build();
        MapTileBuilder SanctumTile3 = new MapTileBuilder(Sanctum3)
                .withTileType(TileType.NOT_PASSABLE);
        mapTiles.add(SanctumTile3);

        // Sanctum4
        Frame Sanctum4 = new FrameBuilder(getSubImage(0, 3))
                .withScale(tileScale)
                .build();
        MapTileBuilder SanctumTile4 = new MapTileBuilder(Sanctum4)
                .withTileType(TileType.NOT_PASSABLE);
        mapTiles.add(SanctumTile4);

        // Sanctum5
        Frame Sanctum5 = new FrameBuilder(getSubImage(0, 4))
                .withScale(tileScale)
                .build();
        MapTileBuilder SanctumTile5 = new MapTileBuilder(Sanctum5)
                .withTileType(TileType.NOT_PASSABLE);
        mapTiles.add(SanctumTile5);

        // Sanctum6
        Frame Sanctum6 = new FrameBuilder(getSubImage(1, 0))
                .withScale(tileScale)
                .build();
        MapTileBuilder SanctumTile6 = new MapTileBuilder(Sanctum6)
                .withTileType(TileType.NOT_PASSABLE);
        mapTiles.add(SanctumTile6);

        // Sanctum7
        Frame Sanctum7 = new FrameBuilder(getSubImage(1, 1))
                .withScale(tileScale)
                .build();
        MapTileBuilder SanctumTile7 = new MapTileBuilder(Sanctum7)
                .withTileType(TileType.NOT_PASSABLE);
        mapTiles.add(SanctumTile7);

        // Sanctum8
        Frame Sanctum8 = new FrameBuilder(getSubImage(1, 2))
                .withScale(tileScale)
                .build();
        MapTileBuilder SanctumTile8 = new MapTileBuilder(Sanctum8)
                .withTileType(TileType.NOT_PASSABLE);
        mapTiles.add(SanctumTile8);

        // Sanctum9
        Frame Sanctum9 = new FrameBuilder(getSubImage(1, 3))
                .withScale(tileScale)
                .build();
        MapTileBuilder SanctumTile9 = new MapTileBuilder(Sanctum9)
                .withTileType(TileType.NOT_PASSABLE);
        mapTiles.add(SanctumTile9);

        // Sanctum10
        Frame Sanctum10 = new FrameBuilder(getSubImage(1, 4))
                .withScale(tileScale)
                .build();
        MapTileBuilder SanctumTile10 = new MapTileBuilder(Sanctum10)
                .withTileType(TileType.NOT_PASSABLE);
        mapTiles.add(SanctumTile10);

        // Sanctum11
        Frame Sanctum11 = new FrameBuilder(getSubImage(2, 0))
                .withScale(tileScale)
                .build();
        MapTileBuilder SanctumTile11 = new MapTileBuilder(Sanctum11)
                .withTileType(TileType.NOT_PASSABLE);
        mapTiles.add(SanctumTile11);

        // Sanctum12
        Frame Sanctum12 = new FrameBuilder(getSubImage(2, 1))
                .withScale(tileScale)
                .build();
        MapTileBuilder SanctumTile12 = new MapTileBuilder(Sanctum12)
                .withTileType(TileType.NOT_PASSABLE);
        mapTiles.add(SanctumTile12);

        // Sanctum13
        Frame Sanctum13 = new FrameBuilder(getSubImage(2, 2))
                .withScale(tileScale)
                .build();
        MapTileBuilder SanctumTile13 = new MapTileBuilder(Sanctum13)
                .withTileType(TileType.NOT_PASSABLE);
        mapTiles.add(SanctumTile13);

        // Sanctum14
        Frame Sanctum14 = new FrameBuilder(getSubImage(2, 3))
                .withScale(tileScale)
                .build();
        MapTileBuilder SanctumTile14 = new MapTileBuilder(Sanctum14)
                .withTileType(TileType.NOT_PASSABLE);
        mapTiles.add(SanctumTile14);

        // Sanctum15
        Frame Sanctum15 = new FrameBuilder(getSubImage(2, 4))
                .withScale(tileScale)
                .build();
        MapTileBuilder SanctumTile15 = new MapTileBuilder(Sanctum15)
                .withTileType(TileType.NOT_PASSABLE);
        mapTiles.add(SanctumTile15);

        // Sanctum16
        Frame Sanctum16 = new FrameBuilder(getSubImage(3, 0))
                .withScale(tileScale)
                .build();
        MapTileBuilder SanctumTile16 = new MapTileBuilder(Sanctum16)
                .withTileType(TileType.NOT_PASSABLE);
        mapTiles.add(SanctumTile16);

        // Sanctum17
        Frame Sanctum17 = new FrameBuilder(getSubImage(3, 1))
                .withScale(tileScale)
                .build();
        MapTileBuilder SanctumTile17 = new MapTileBuilder(Sanctum17)
                .withTileType(TileType.NOT_PASSABLE);
        mapTiles.add(SanctumTile17);

        // Sanctum18
        Frame Sanctum18 = new FrameBuilder(getSubImage(3, 2))
                .withScale(tileScale)
                .build();
        MapTileBuilder SanctumTile18 = new MapTileBuilder(Sanctum18)
                .withTileType(TileType.NOT_PASSABLE);
        mapTiles.add(SanctumTile18);

        // Sanctum19
        Frame Sanctum19 = new FrameBuilder(getSubImage(3, 3))
                .withScale(tileScale)
                .build();
        MapTileBuilder SanctumTile19 = new MapTileBuilder(Sanctum19)
                .withTileType(TileType.NOT_PASSABLE);
        mapTiles.add(SanctumTile19);

        // Sanctum20
        Frame Sanctum20 = new FrameBuilder(getSubImage(3, 4))
                .withScale(tileScale)
                .build();
        MapTileBuilder SanctumTile20 = new MapTileBuilder(Sanctum20)
                .withTileType(TileType.NOT_PASSABLE);
        mapTiles.add(SanctumTile20);

        // Sanctum21
        Frame Sanctum21 = new FrameBuilder(getSubImage(4, 0))
                .withScale(tileScale)
                .build();
        MapTileBuilder SanctumTile21 = new MapTileBuilder(Sanctum21)
                .withTileType(TileType.NOT_PASSABLE);
        mapTiles.add(SanctumTile21);

        // Sanctum22
        Frame Sanctum22 = new FrameBuilder(getSubImage(4, 1))
                .withScale(tileScale)
                .build();
        MapTileBuilder SanctumTile22 = new MapTileBuilder(Sanctum22)
                .withTileType(TileType.NOT_PASSABLE);
        mapTiles.add(SanctumTile22);

        // Sanctum23
        Frame Sanctum23 = new FrameBuilder(getSubImage(4, 2))
                .withScale(tileScale)
                .build();
        MapTileBuilder SanctumTile23 = new MapTileBuilder(Sanctum23)
                .withTileType(TileType.NOT_PASSABLE);
        mapTiles.add(SanctumTile23);

        // Sanctum24
        Frame Sanctum24 = new FrameBuilder(getSubImage(4, 3))
                .withScale(tileScale)
                .build();
        MapTileBuilder SanctumTile24 = new MapTileBuilder(Sanctum24)
                .withTileType(TileType.NOT_PASSABLE);
        mapTiles.add(SanctumTile24);

        // Sanctum25
        Frame Sanctum25 = new FrameBuilder(getSubImage(4, 4))
                .withScale(tileScale)
                .build();
        MapTileBuilder SanctumTile25 = new MapTileBuilder(Sanctum25)
                .withTileType(TileType.NOT_PASSABLE);
        mapTiles.add(SanctumTile25);

        // Sanctum26
        Frame Sanctum26 = new FrameBuilder(getSubImage(5, 0))
                .withScale(tileScale)
                .build();
        MapTileBuilder SanctumTile26 = new MapTileBuilder(Sanctum26)
                .withTileType(TileType.NOT_PASSABLE);
        mapTiles.add(SanctumTile26);

        // Sanctum27
        Frame Sanctum27 = new FrameBuilder(getSubImage(5, 1))
                .withScale(tileScale)
                .build();
        MapTileBuilder SanctumTile27 = new MapTileBuilder(Sanctum27)
                .withTileType(TileType.NOT_PASSABLE);
        mapTiles.add(SanctumTile27);

        // Sanctum28
        Frame Sanctum28 = new FrameBuilder(getSubImage(5, 2))
                .withScale(tileScale)
                .build();
        MapTileBuilder SanctumTile28 = new MapTileBuilder(Sanctum28)
                .withTileType(TileType.NOT_PASSABLE);
        mapTiles.add(SanctumTile28);

        // Sanctum29
        Frame Sanctum29 = new FrameBuilder(getSubImage(5, 3))
                .withScale(tileScale)
                .build();
        MapTileBuilder SanctumTile29 = new MapTileBuilder(Sanctum29)
                .withTileType(TileType.NOT_PASSABLE);
        mapTiles.add(SanctumTile29);

        // Sanctum30
        Frame Sanctum30 = new FrameBuilder(getSubImage(5, 4))
                .withScale(tileScale)
                .build();
        MapTileBuilder SanctumTile30 = new MapTileBuilder(Sanctum30)
                .withTileType(TileType.NOT_PASSABLE);
        mapTiles.add(SanctumTile30);

        // passable 

        // Sanctum31
        Frame Sanctum31 = new FrameBuilder(getSubImage(6, 0))
                .withScale(tileScale)
                .build();
        MapTileBuilder SanctumTile31 = new MapTileBuilder(Sanctum31);
        mapTiles.add(SanctumTile31);

        // Sanctum32
        Frame Sanctum32 = new FrameBuilder(getSubImage(6, 1))
                .withScale(tileScale)
                .build();
        MapTileBuilder SanctumTile32 = new MapTileBuilder(Sanctum32);
        mapTiles.add(SanctumTile32);

        // Sanctum33
        Frame Sanctum33 = new FrameBuilder(getSubImage(6, 2))
                .withScale(tileScale)
                .build();
        MapTileBuilder SanctumTile33 = new MapTileBuilder(Sanctum33);
        mapTiles.add(SanctumTile33);

        // Sanctum34
        Frame Sanctum34 = new FrameBuilder(getSubImage(6, 3))
                .withScale(tileScale)
                .build();
        MapTileBuilder SanctumTile34 = new MapTileBuilder(Sanctum34);
        mapTiles.add(SanctumTile34);

        // Sanctum35
        Frame Sanctum35 = new FrameBuilder(getSubImage(6, 4))
                .withScale(tileScale)
                .build();
        MapTileBuilder SanctumTile35 = new MapTileBuilder(Sanctum35);
        mapTiles.add(SanctumTile35);

        // Sanctum36
        Frame Sanctum36 = new FrameBuilder(getSubImage(7, 0))
                .withScale(tileScale)
                .build();
        MapTileBuilder SanctumTile36 = new MapTileBuilder(Sanctum36);
        mapTiles.add(SanctumTile36);

        // Sanctum37
        Frame Sanctum37 = new FrameBuilder(getSubImage(7, 1))
                .withScale(tileScale)
                .build();
        MapTileBuilder SanctumTile37 = new MapTileBuilder(Sanctum37);
        mapTiles.add(SanctumTile37);

        // Sanctum38
        Frame Sanctum38 = new FrameBuilder(getSubImage(7, 2))
                .withScale(tileScale)
                .build();
        MapTileBuilder SanctumTile38 = new MapTileBuilder(Sanctum38);
        mapTiles.add(SanctumTile38);

        // Sanctum39
        Frame Sanctum39 = new FrameBuilder(getSubImage(7, 3))
                .withScale(tileScale)
                .build();
        MapTileBuilder SanctumTile39 = new MapTileBuilder(Sanctum39);
        mapTiles.add(SanctumTile39);

        // Sanctum40
        Frame Sanctum40 = new FrameBuilder(getSubImage(7, 4))
                .withScale(tileScale)
                .build();
        MapTileBuilder SanctumTile40 = new MapTileBuilder(Sanctum40);
        mapTiles.add(SanctumTile40);

        // Sanctum41
        Frame Sanctum41 = new FrameBuilder(getSubImage(8, 0))
                .withScale(tileScale)
                .build();
        MapTileBuilder SanctumTile41 = new MapTileBuilder(Sanctum41);
        mapTiles.add(SanctumTile41);

        // Sanctum42
        Frame Sanctum42 = new FrameBuilder(getSubImage(8, 1))
                .withScale(tileScale)
                .build();
        MapTileBuilder SanctumTile42 = new MapTileBuilder(Sanctum42);
        mapTiles.add(SanctumTile42);

        // Sanctum43
        Frame Sanctum43 = new FrameBuilder(getSubImage(8, 2))
                .withScale(tileScale)
                .build();
        MapTileBuilder SanctumTile43 = new MapTileBuilder(Sanctum43);
        mapTiles.add(SanctumTile43);

        // Sanctum44
        Frame Sanctum44 = new FrameBuilder(getSubImage(8, 3))
                .withScale(tileScale)
                .build();
        MapTileBuilder SanctumTile44 = new MapTileBuilder(Sanctum44);
        mapTiles.add(SanctumTile44);

        // Sanctum45
        Frame Sanctum45 = new FrameBuilder(getSubImage(8, 4))
                .withScale(tileScale)
                .build();
        MapTileBuilder SanctumTile45 = new MapTileBuilder(Sanctum45);
        mapTiles.add(SanctumTile45);

        // Sanctum46
        Frame Sanctum46 = new FrameBuilder(getSubImage(9, 0))
                .withScale(tileScale)
                .build();
        MapTileBuilder SanctumTile46 = new MapTileBuilder(Sanctum46);
        mapTiles.add(SanctumTile46);

        // Sanctum47
        Frame Sanctum47 = new FrameBuilder(getSubImage(9, 1))
                .withScale(tileScale)
                .build();
        MapTileBuilder SanctumTile47 = new MapTileBuilder(Sanctum47);
        mapTiles.add(SanctumTile47);

        // Sanctum48
        Frame Sanctum48 = new FrameBuilder(getSubImage(9, 2))
                .withScale(tileScale)
                .build();
        MapTileBuilder SanctumTile48 = new MapTileBuilder(Sanctum48);
        mapTiles.add(SanctumTile48);

        // Sanctum49
        Frame Sanctum49 = new FrameBuilder(getSubImage(9, 3))
                .withScale(tileScale)
                .build();
        MapTileBuilder SanctumTile49 = new MapTileBuilder(Sanctum49);
        mapTiles.add(SanctumTile49);

        // Sanctum50
        Frame Sanctum50 = new FrameBuilder(getSubImage(9, 4))
                .withScale(tileScale)
                .build();
        MapTileBuilder SanctumTile50 = new MapTileBuilder(Sanctum50);
        mapTiles.add(SanctumTile50);

         return mapTiles; 
    }
}

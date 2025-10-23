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
public class CommonTileset extends Tileset {

    public CommonTileset() {
        super(ImageLoader.load("CommonTileset.png"), 16, 16, 3);
    }

    @Override
    public ArrayList<MapTileBuilder> defineTiles() {
        ArrayList<MapTileBuilder> mapTiles = new ArrayList<>();

        // grass
        Frame grassFrame = new FrameBuilder(getSubImage(0, 0))
                .withScale(tileScale)
                .build();

        MapTileBuilder grassTile = new MapTileBuilder(grassFrame);

        mapTiles.add(grassTile);

        // sign
        Frame signFrame = new FrameBuilder(getSubImage(3, 0))
                .withScale(tileScale)
                .withBounds(1, 2, 14, 14)
                .build();

        MapTileBuilder signTile = new MapTileBuilder(signFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(signTile);

        // sand
        Frame sandFrame = new FrameBuilder(getSubImage(0, 1))
                .withScale(tileScale)
                .build();

        MapTileBuilder sandTile = new MapTileBuilder(sandFrame);

        mapTiles.add(sandTile);

        // rock
        Frame rockFrame = new FrameBuilder(getSubImage(3, 1))
                .withScale(tileScale)
                .build();

        MapTileBuilder rockTile = new MapTileBuilder(rockFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(rockTile);

        // tree trunk with full hole
        Frame treeTrunkWithFullHoleFrame = new FrameBuilder(getSubImage(2, 2))
                .withScale(tileScale)
                .build();

        MapTileBuilder treeTrunkWithFullHoleTile = new MapTileBuilder(grassFrame)
                .withTopLayer(treeTrunkWithFullHoleFrame)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(treeTrunkWithFullHoleTile);

        // left end branch
        Frame leftEndBranchFrame = new FrameBuilder(getSubImage(2, 4))
                .withScale(tileScale)
                .withBounds(0, 6, 16, 4)
                .build();

        MapTileBuilder leftEndBranchTile = new MapTileBuilder(grassFrame)
                .withTopLayer(leftEndBranchFrame)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(leftEndBranchTile);

        // right end branch
        Frame rightEndBranchFrame = new FrameBuilder(getSubImage(2, 4))
                .withScale(tileScale)
                .withBounds(0, 6, 16, 4)
                .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                .build();

        MapTileBuilder rightEndBranchTile = new MapTileBuilder(grassFrame)
                .withTopLayer(rightEndBranchFrame)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(rightEndBranchTile);
        
        // tree trunk
        Frame treeTrunkFrame = new FrameBuilder(getSubImage(1, 0))
                .withScale(tileScale)
                .build();

        MapTileBuilder treeTrunkTile = new MapTileBuilder(grassFrame)
                .withTopLayer(treeTrunkFrame)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(treeTrunkTile);

        // tree top leaves
        Frame treeTopLeavesFrame = new FrameBuilder(getSubImage(1, 1))
                .withScale(tileScale)
                .build();

        MapTileBuilder treeTopLeavesTile = new MapTileBuilder(grassFrame)
                .withTopLayer(treeTopLeavesFrame)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(treeTopLeavesTile);
        
        // yellow flower
        Frame[] yellowFlowerFrames = new Frame[] {
                new FrameBuilder(getSubImage(1, 2), 65)
                    .withScale(tileScale)
                    .build(),
                new FrameBuilder(getSubImage(1, 3), 65)
                        .withScale(tileScale)
                        .build(),
                new FrameBuilder(getSubImage(1, 2), 65)
                        .withScale(tileScale)
                        .build(),
                new FrameBuilder(getSubImage(1, 4), 65)
                        .withScale(tileScale)
                        .build()
        };

        MapTileBuilder yellowFlowerTile = new MapTileBuilder(yellowFlowerFrames);

        mapTiles.add(yellowFlowerTile);

        // purple flower
        Frame[] purpleFlowerFrames = new Frame[] {
                new FrameBuilder(getSubImage(0, 2), 65)
                        .withScale(tileScale)
                        .build(),
                new FrameBuilder(getSubImage(0, 3), 65)
                        .withScale(tileScale)
                        .build(),
                new FrameBuilder(getSubImage(0, 2), 65)
                        .withScale(tileScale)
                        .build(),
                new FrameBuilder(getSubImage(0, 4), 65)
                        .withScale(tileScale)
                        .build()
        };

        MapTileBuilder purpleFlowerTile = new MapTileBuilder(purpleFlowerFrames);

        mapTiles.add(purpleFlowerTile);

        // middle branch
        Frame middleBranchFrame = new FrameBuilder(getSubImage(2, 3))
                .withScale(tileScale)
                .withBounds(0, 6, 16, 4)
                .build();

        MapTileBuilder middleBranchTile = new MapTileBuilder(grassFrame)
                .withTopLayer(middleBranchFrame)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(middleBranchTile);

        // tree trunk bottom
        Frame treeTrunkBottomFrame = new FrameBuilder(getSubImage(2, 0))
                .withScale(tileScale)
                .build();

        MapTileBuilder treeTrunkBottomTile = new MapTileBuilder(treeTrunkBottomFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(treeTrunkBottomTile);

        // mushrooms
        Frame mushroomFrame = new FrameBuilder(getSubImage(2, 1))
                .withScale(tileScale)
                .build();

        MapTileBuilder mushroomTile = new MapTileBuilder(mushroomFrame)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(mushroomTile);


        // grey rock
        Frame greyRockFrame = new FrameBuilder(getSubImage(3, 2))
                .withScale(tileScale)
                .build();

        MapTileBuilder greyRockTile = new MapTileBuilder(greyRockFrame)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(greyRockTile);

        // bush
        Frame bushFrame = new FrameBuilder(getSubImage(3, 3))
                .withScale(tileScale)
                .build();

        MapTileBuilder bushTile = new MapTileBuilder(bushFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(bushTile);

        // house body
        Frame houseBodyFrame = new FrameBuilder(getSubImage(3, 4))
                .withScale(tileScale)
                .build();

        MapTileBuilder houseBodyTile = new MapTileBuilder(houseBodyFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(houseBodyTile);

        // house roof body
        Frame houseRoofBodyFrame = new FrameBuilder(getSubImage(4, 0))
                .withScale(tileScale)
                .build();

        MapTileBuilder houseRoofBodyTile = new MapTileBuilder(grassFrame)
                .withTopLayer(houseRoofBodyFrame)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(houseRoofBodyTile);

        // left house roof
        Frame leftHouseRoofFrame = new FrameBuilder(getSubImage(4, 1))
                .withScale(tileScale)
                .build();

        MapTileBuilder leftHouseRoofTile = new MapTileBuilder(grassFrame)
                .withTopLayer(leftHouseRoofFrame)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(leftHouseRoofTile);

        // right house roof
        Frame rightHouseRoofFrame = new FrameBuilder(getSubImage(4, 1))
                .withScale(tileScale)
                .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                .build();

        MapTileBuilder rightHouseRoofTile = new MapTileBuilder(grassFrame)
                .withTopLayer(rightHouseRoofFrame)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(rightHouseRoofTile);

        // left window
        Frame leftWindowFrame = new FrameBuilder(getSubImage(4, 2))
                .withScale(tileScale)
                .build();

        MapTileBuilder leftWindowTile = new MapTileBuilder(leftWindowFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(leftWindowTile);

        // right window
        Frame rightWindowFrame = new FrameBuilder(getSubImage(4, 2))
                .withScale(tileScale)
                .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                .build();

        MapTileBuilder rightWindowTile = new MapTileBuilder(rightWindowFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(rightWindowTile);

        // door
        Frame doorFrame = new FrameBuilder(getSubImage(4, 3))
                .withScale(tileScale)
                .build();

        MapTileBuilder doorTile = new MapTileBuilder(doorFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(doorTile);

        // top water
        Frame[] topWaterFrames = new Frame[] {
            new FrameBuilder(getSubImage(5, 0), 65)
                    .withScale(tileScale)
                    .build(),
            new FrameBuilder(getSubImage(5, 1), 65)
                    .withScale(tileScale)
                    .build(),
            new FrameBuilder(getSubImage(5, 2), 65)
                    .withScale(tileScale)
                    .build(),
            new FrameBuilder(getSubImage(5, 1), 65)
                    .withScale(tileScale)
                    .build(),
            new FrameBuilder(getSubImage(5, 0), 65)
                    .withScale(tileScale)
                    .build(),
            new FrameBuilder(getSubImage(5, 3), 65)
                    .withScale(tileScale)
                    .build(),
            new FrameBuilder(getSubImage(5, 4), 65)
                    .withScale(tileScale)
                    .build(),
            new FrameBuilder(getSubImage(5, 3), 65)
                    .withScale(tileScale)
                    .build()
        };

        MapTileBuilder topWaterTile = new MapTileBuilder(topWaterFrames)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(topWaterTile);

                
        // drownedGates1
        Frame drownedGates1 = new FrameBuilder(getSubImage(6, 0))
                .withScale(tileScale)
                .build();
                
        MapTileBuilder drownedGatesTile1 = new MapTileBuilder(drownedGates1)
        .withTileType(TileType.NOT_PASSABLE);
        mapTiles.add(drownedGatesTile1);

        // drownedGates2
        Frame drownedGates2 = new FrameBuilder(getSubImage(6, 1))
                .withScale(tileScale)
                .build();
        MapTileBuilder drownedGatesTile2 = new MapTileBuilder(drownedGates2)
        .withTileType(TileType.NOT_PASSABLE);
        mapTiles.add(drownedGatesTile2);

        // drownedGates3
        Frame drownedGates3 = new FrameBuilder(getSubImage(6, 2))
                .withScale(tileScale)
                .build();
        MapTileBuilder drownedGatesTile3 = new MapTileBuilder(drownedGates3)
        .withTileType(TileType.NOT_PASSABLE);
        mapTiles.add(drownedGatesTile3);

        // drownedGates4
        Frame drownedGates4 = new FrameBuilder(getSubImage(6, 3))
                .withScale(tileScale)
                .build();
        MapTileBuilder drownedGatesTile4 = new MapTileBuilder(drownedGates4)
        .withTileType(TileType.NOT_PASSABLE);
        mapTiles.add(drownedGatesTile4);

        // drownedGates5
        Frame drownedGates5 = new FrameBuilder(getSubImage(6, 4))
                .withScale(tileScale)
                .build();
        MapTileBuilder drownedGatesTile5 = new MapTileBuilder(drownedGates5)
        .withTileType(TileType.NOT_PASSABLE);
        mapTiles.add(drownedGatesTile5);

        // drownedGates6
        Frame drownedGates6 = new FrameBuilder(getSubImage(7, 0))
                .withScale(tileScale)
                .build();
        MapTileBuilder drownedGatesTile6 = new MapTileBuilder(drownedGates6)
        .withTileType(TileType.NOT_PASSABLE);
        mapTiles.add(drownedGatesTile6);

        // drownedGates7
        Frame drownedGates7 = new FrameBuilder(getSubImage(7, 1))
                .withScale(tileScale)
                .build();
        MapTileBuilder drownedGatesTile7 = new MapTileBuilder(drownedGates7);
        mapTiles.add(drownedGatesTile7);

        // drownedGates8
        Frame drownedGates8 = new FrameBuilder(getSubImage(7, 2))
                .withScale(tileScale)
                .build();
        MapTileBuilder drownedGatesTile8 = new MapTileBuilder(drownedGates8);
        mapTiles.add(drownedGatesTile8);

        // drownedGates9
        Frame drownedGates9 = new FrameBuilder(getSubImage(7, 3))
                .withScale(tileScale)
                .build();
        MapTileBuilder drownedGatesTile9 = new MapTileBuilder(drownedGates9);
        mapTiles.add(drownedGatesTile9);

        // drownedGates10
        Frame drownedGates10 = new FrameBuilder(getSubImage(7, 4))
                .withScale(tileScale)
                .build();
        MapTileBuilder drownedGatesTile10 = new MapTileBuilder(drownedGates10);
        mapTiles.add(drownedGatesTile10);

        // drownedGates11
        Frame drownedGates11 = new FrameBuilder(getSubImage(8, 0))
                .withScale(tileScale)
                .build();
        MapTileBuilder drownedGatesTile11 = new MapTileBuilder(drownedGates11)
        .withTileType(TileType.NOT_PASSABLE);
        mapTiles.add(drownedGatesTile11);

        // drownedGates12
        Frame drownedGates12 = new FrameBuilder(getSubImage(8, 1))
                .withScale(tileScale)
                .build();
        MapTileBuilder drownedGatesTile12 = new MapTileBuilder(drownedGates12);
        mapTiles.add(drownedGatesTile12);

        // drownedGates13
        Frame drownedGates13 = new FrameBuilder(getSubImage(8, 2))
                .withScale(tileScale)
                .build();
        MapTileBuilder drownedGatesTile13 = new MapTileBuilder(drownedGates13);
        mapTiles.add(drownedGatesTile13);

        // drownedGates14
        Frame drownedGates14 = new FrameBuilder(getSubImage(8, 3))
                .withScale(tileScale)
                .build();
        MapTileBuilder drownedGatesTile14 = new MapTileBuilder(drownedGates14);
        mapTiles.add(drownedGatesTile14);

        // drownedGates15
        Frame drownedGates15 = new FrameBuilder(getSubImage(8, 4))
                .withScale(tileScale)
                .build();
        MapTileBuilder drownedGatesTile15 = new MapTileBuilder(drownedGates15);
        mapTiles.add(drownedGatesTile15);

        // drownedGates16
        Frame drownedGates16 = new FrameBuilder(getSubImage(9, 0))
                .withScale(tileScale)
                .build();
        MapTileBuilder drownedGatesTile16 = new MapTileBuilder(drownedGates16)
        .withTileType(TileType.NOT_PASSABLE);
        mapTiles.add(drownedGatesTile16);

        // drownedGates17
        Frame drownedGates17 = new FrameBuilder(getSubImage(9, 1))
                .withScale(tileScale)
                .build();
        MapTileBuilder drownedGatesTile17 = new MapTileBuilder(drownedGates17);
        mapTiles.add(drownedGatesTile17);

        // drownedGates18
        Frame drownedGates18 = new FrameBuilder(getSubImage(9, 2))
                .withScale(tileScale)
                .build();
        MapTileBuilder drownedGatesTile18 = new MapTileBuilder(drownedGates18)
        .withTileType(TileType.NOT_PASSABLE);
        mapTiles.add(drownedGatesTile18);

        // drownedGates19
        Frame drownedGates19 = new FrameBuilder(getSubImage(9, 3))
                .withScale(tileScale)
                .build();
        MapTileBuilder drownedGatesTile19 = new MapTileBuilder(drownedGates19);
        mapTiles.add(drownedGatesTile19);

        // drownedGates20
        Frame drownedGates20 = new FrameBuilder(getSubImage(9, 4))
                .withScale(tileScale)
                .build();
        MapTileBuilder drownedGatesTile20 = new MapTileBuilder(drownedGates20)
        .withTileType(TileType.NOT_PASSABLE);
        mapTiles.add(drownedGatesTile20);

        // drownedGates21
        Frame drownedGates21 = new FrameBuilder(getSubImage(10, 0))
                .withScale(tileScale)
                .build();
        MapTileBuilder drownedGatesTile21 = new MapTileBuilder(drownedGates21)
        .withTileType(TileType.NOT_PASSABLE);
        mapTiles.add(drownedGatesTile21);

        // drownedGates22
        Frame drownedGates22 = new FrameBuilder(getSubImage(10, 1))
                .withScale(tileScale)
                .build();
        MapTileBuilder drownedGatesTile22 = new MapTileBuilder(drownedGates22)
        .withTileType(TileType.NOT_PASSABLE);
        mapTiles.add(drownedGatesTile22);

        // drownedGates23
        Frame drownedGates23 = new FrameBuilder(getSubImage(10, 2))
                .withScale(tileScale)
                .build();
        MapTileBuilder drownedGatesTile23 = new MapTileBuilder(drownedGates23)
        .withTileType(TileType.NOT_PASSABLE);
        mapTiles.add(drownedGatesTile23);

        // drownedGates24
        Frame drownedGates24 = new FrameBuilder(getSubImage(10, 3))
                .withScale(tileScale)
                .build();
        MapTileBuilder drownedGatesTile24 = new MapTileBuilder(drownedGates24)
        .withTileType(TileType.NOT_PASSABLE);
        mapTiles.add(drownedGatesTile24);

        // drownedGates25
        Frame drownedGates25 = new FrameBuilder(getSubImage(10, 4))
                .withScale(tileScale)
                .build();
        MapTileBuilder drownedGatesTile25 = new MapTileBuilder(drownedGates25)
        .withTileType(TileType.NOT_PASSABLE);
        mapTiles.add(drownedGatesTile25);

        // drownedGates26
        Frame drownedGates26 = new FrameBuilder(getSubImage(11, 0))
                .withScale(tileScale)
                .build();
        MapTileBuilder drownedGatesTile26 = new MapTileBuilder(drownedGates26)
        .withTileType(TileType.NOT_PASSABLE);
        mapTiles.add(drownedGatesTile26);

        // drownedGates27
        Frame drownedGates27 = new FrameBuilder(getSubImage(11, 1))
                .withScale(tileScale)
                .build();
        MapTileBuilder drownedGatesTile27 = new MapTileBuilder(drownedGates27)
        .withTileType(TileType.NOT_PASSABLE);
        mapTiles.add(drownedGatesTile27);

        // drownedGates28
        Frame drownedGates28 = new FrameBuilder(getSubImage(11, 2))
                .withScale(tileScale)
                .build();
        MapTileBuilder drownedGatesTile28 = new MapTileBuilder(drownedGates28);
        mapTiles.add(drownedGatesTile28);

        // drownedGates29
        Frame drownedGates29 = new FrameBuilder(getSubImage(11, 3))
                .withScale(tileScale)
                .build();
        MapTileBuilder drownedGatesTile29 = new MapTileBuilder(drownedGates29);
        mapTiles.add(drownedGatesTile29);

        // drownedGates30
        Frame drownedGates30 = new FrameBuilder(getSubImage(11, 4))
                .withScale(tileScale)
                .build();
        MapTileBuilder drownedGatesTile30 = new MapTileBuilder(drownedGates30)
        .withTileType(TileType.NOT_PASSABLE);
        mapTiles.add(drownedGatesTile30);

        // drownedGates31
        Frame drownedGates31 = new FrameBuilder(getSubImage(12, 0))
                .withScale(tileScale)
                .build();
        MapTileBuilder drownedGatesTile31 = new MapTileBuilder(drownedGates31)
        .withTileType(TileType.NOT_PASSABLE);
        mapTiles.add(drownedGatesTile31);

        // drownedGates32
        Frame drownedGates32 = new FrameBuilder(getSubImage(12, 1))
                .withScale(tileScale)
                .build();
        MapTileBuilder drownedGatesTile32 = new MapTileBuilder(drownedGates32)
        .withTileType(TileType.NOT_PASSABLE);
        mapTiles.add(drownedGatesTile32);

        // drownedGates33
        Frame drownedGates33 = new FrameBuilder(getSubImage(12, 2))
                .withScale(tileScale)
                .build();
        MapTileBuilder drownedGatesTile33 = new MapTileBuilder(drownedGates33)
        .withTileType(TileType.NOT_PASSABLE);
        mapTiles.add(drownedGatesTile33);

        // drownedGates34
        Frame drownedGates34 = new FrameBuilder(getSubImage(12, 3))
                .withScale(tileScale)
                .build();
        MapTileBuilder drownedGatesTile34 = new MapTileBuilder(drownedGates34)
        .withTileType(TileType.NOT_PASSABLE);
        mapTiles.add(drownedGatesTile34);

        // drownedGates35
        Frame drownedGates35 = new FrameBuilder(getSubImage(12, 4))
                .withScale(tileScale)
                .build();
        MapTileBuilder drownedGatesTile35 = new MapTileBuilder(drownedGates35)
        .withTileType(TileType.NOT_PASSABLE);
        mapTiles.add(drownedGatesTile35);

        // drownedGates36
        Frame drownedGates36 = new FrameBuilder(getSubImage(13, 0))
                .withScale(tileScale)
                .build();
        MapTileBuilder drownedGatesTile36 = new MapTileBuilder(drownedGates36);
        mapTiles.add(drownedGatesTile36);

        // drownedGates37
        Frame drownedGates37 = new FrameBuilder(getSubImage(13, 1))
                .withScale(tileScale)
                .build();
        MapTileBuilder drownedGatesTile37 = new MapTileBuilder(drownedGates37);
        mapTiles.add(drownedGatesTile37);

        // drownedGates38
        Frame drownedGates38 = new FrameBuilder(getSubImage(13, 2))
                .withScale(tileScale)
                .build();
        MapTileBuilder drownedGatesTile38 = new MapTileBuilder(drownedGates38);
        mapTiles.add(drownedGatesTile38);

        // drownedGates39
        Frame drownedGates39 = new FrameBuilder(getSubImage(13, 3))
                .withScale(tileScale)
                .build();
        MapTileBuilder drownedGatesTile39 = new MapTileBuilder(drownedGates39);
        mapTiles.add(drownedGatesTile39);

        // drownedGates40
        Frame drownedGates40 = new FrameBuilder(getSubImage(13, 4))
                .withScale(tileScale)
                .build();
        MapTileBuilder drownedGatesTile40 = new MapTileBuilder(drownedGates40);
        mapTiles.add(drownedGatesTile40);

        // drownedGates41
        Frame drownedGates41 = new FrameBuilder(getSubImage(14, 0))
                .withScale(tileScale)
                .build();
        MapTileBuilder drownedGatesTile41 = new MapTileBuilder(drownedGates41);
        mapTiles.add(drownedGatesTile41);

        // drownedGates42
        Frame drownedGates42 = new FrameBuilder(getSubImage(14, 1))
                .withScale(tileScale)
                .build();
        MapTileBuilder drownedGatesTile42 = new MapTileBuilder(drownedGates42);
        mapTiles.add(drownedGatesTile42);

        // drownedGates43
        Frame drownedGates43 = new FrameBuilder(getSubImage(14, 2))
                .withScale(tileScale)
                .build();
        MapTileBuilder drownedGatesTile43 = new MapTileBuilder(drownedGates43);
        mapTiles.add(drownedGatesTile43);

        // drownedGates44
        Frame drownedGates44 = new FrameBuilder(getSubImage(14, 3))
                .withScale(tileScale)
                .build();
        MapTileBuilder drownedGatesTile44 = new MapTileBuilder(drownedGates44);
        mapTiles.add(drownedGatesTile44);

        // drownedGates45
        Frame drownedGates45 = new FrameBuilder(getSubImage(14, 4))
                .withScale(tileScale)
                .build();
        MapTileBuilder drownedGatesTile45 = new MapTileBuilder(drownedGates45);
        mapTiles.add(drownedGatesTile45);

        // drownedGates46
        Frame drownedGates46 = new FrameBuilder(getSubImage(15, 0))
                .withScale(tileScale)
                .build();
        MapTileBuilder drownedGatesTile46 = new MapTileBuilder(drownedGates46);
        mapTiles.add(drownedGatesTile46);

        // drownedGates47
        Frame drownedGates47 = new FrameBuilder(getSubImage(15, 1))
                .withScale(tileScale)
                .build();
        MapTileBuilder drownedGatesTile47 = new MapTileBuilder(drownedGates47);
        mapTiles.add(drownedGatesTile47);

        // drownedGates48
        Frame drownedGates48 = new FrameBuilder(getSubImage(15, 2))
                .withScale(tileScale)
                .build();
        MapTileBuilder drownedGatesTile48 = new MapTileBuilder(drownedGates48);
        mapTiles.add(drownedGatesTile48);

        // drownedGates49
        Frame drownedGates49 = new FrameBuilder(getSubImage(15, 3))
                .withScale(tileScale)
                .build();
        MapTileBuilder drownedGatesTile49 = new MapTileBuilder(drownedGates49);
        mapTiles.add(drownedGatesTile49);

        // drownedGates50
        Frame drownedGates50 = new FrameBuilder(getSubImage(15, 4))
                .withScale(tileScale)
                .build();
        MapTileBuilder drownedGatesTile50 = new MapTileBuilder(drownedGates50);
        mapTiles.add(drownedGatesTile50);

        // drownedGates51
        Frame drownedGates51 = new FrameBuilder(getSubImage(16, 0))
                .withScale(tileScale)
                .build();
        MapTileBuilder drownedGatesTile51 = new MapTileBuilder(drownedGates51);
        mapTiles.add(drownedGatesTile51);

        // drownedGates52
        Frame drownedGates52 = new FrameBuilder(getSubImage(16, 1))
                .withScale(tileScale)
                .build();
        MapTileBuilder drownedGatesTile52 = new MapTileBuilder(drownedGates52);
        mapTiles.add(drownedGatesTile52);

        // drownedGates53
        Frame drownedGates53 = new FrameBuilder(getSubImage(16, 2))
                .withScale(tileScale)
                .build();
        MapTileBuilder drownedGatesTile53 = new MapTileBuilder(drownedGates53);
        mapTiles.add(drownedGatesTile53);

        // drownedGates54
        Frame drownedGates54 = new FrameBuilder(getSubImage(16, 3))
                .withScale(tileScale)
                .build();
        MapTileBuilder drownedGatesTile54 = new MapTileBuilder(drownedGates54)
        .withTileType(TileType.NOT_PASSABLE);
        mapTiles.add(drownedGatesTile54);

        // drownedGates55
        Frame drownedGates55 = new FrameBuilder(getSubImage(16, 4))
                .withScale(tileScale)
                .build();
        MapTileBuilder drownedGatesTile55 = new MapTileBuilder(drownedGates55);
        mapTiles.add(drownedGatesTile55);

        // drownedGates56
        Frame drownedGates56 = new FrameBuilder(getSubImage(17, 0))
                .withScale(tileScale)
                .build();
        MapTileBuilder drownedGatesTile56 = new MapTileBuilder(drownedGates56);
        mapTiles.add(drownedGatesTile56);

        // drownedGates57
        Frame drownedGates57 = new FrameBuilder(getSubImage(17, 1))
                .withScale(tileScale)
                .build();
        MapTileBuilder drownedGatesTile57 = new MapTileBuilder(drownedGates57);
        mapTiles.add(drownedGatesTile57);

        // drownedGates58
        Frame drownedGates58 = new FrameBuilder(getSubImage(17, 2))
                .withScale(tileScale)
                .build();
        MapTileBuilder drownedGatesTile58 = new MapTileBuilder(drownedGates58);
        mapTiles.add(drownedGatesTile58);

        // drownedGates59
        Frame drownedGates59 = new FrameBuilder(getSubImage(17, 3))
                .withScale(tileScale)
                .build();
        MapTileBuilder drownedGatesTile59 = new MapTileBuilder(drownedGates59)
        .withTileType(TileType.NOT_PASSABLE);
        mapTiles.add(drownedGatesTile59);

        // drownedGates60
        Frame drownedGates60 = new FrameBuilder(getSubImage(17, 4))
                .withScale(tileScale)
                .build();
        MapTileBuilder drownedGatesTile60 = new MapTileBuilder(drownedGates60);
        mapTiles.add(drownedGatesTile60);

        // Now its time for the The Abyssal Sanctum next time 


        return mapTiles; 
    }
}

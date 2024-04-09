package com.mike;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
public class Main {
    static ArrayList<BedrockBlock> blocks = new ArrayList<>();
    static BedrockReader bedrockReader;

    public static void main(String[] args) throws IOException  {
        long seed = Long.parseLong(args[0]);
        // java -jar bedrockformation.jar 124352345 floor file.txt

        BedrockReader.BedrockType bedrockType = switch (args[1]) {
            case "floor" -> BedrockReader.BedrockType.BEDROCK_FLOOR;
            case "roof" -> BedrockReader.BedrockType.BEDROCK_ROOF;
            default -> throw new RuntimeException("must declare a bedrock type (second argument) either floor or roof");
        };

        BufferedReader reader = new BufferedReader(new FileReader(args[2]));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] myargs = line.split("\\s+");
            int x = Integer.parseInt(myargs[0]);
            int y = Integer.parseInt(myargs[1]);
            int z = Integer.parseInt(myargs[2]);
            boolean shouldBeBedrock = Boolean.parseBoolean(myargs[3]);
            blocks.add(new BedrockBlock(x, y, z, shouldBeBedrock));
        }

        // to verify the blocks have been added correctly
        blocks.forEach(block -> System.out.println(block));
        if (blocks.size() == 0) return;

        // we need to adjust all block coords based on the origin (the first block in the formation)
        BedrockBlock og = blocks.get(0);
        for (int i = 0; i < blocks.size(); i++) {
            BedrockBlock inplace = blocks.get(i);
            blocks.set(i, new BedrockBlock(inplace.x - og.x, inplace.y - og.y, inplace.z - og.z, inplace.shouldBeBedrock));
        }
        System.out.println();
        blocks.forEach(block -> System.out.println(block));
        System.out.println();

        // I don't think the following sort is actually needed
        // we can then sort the blocks based on their y level
        blocks.sort((b1, b2) -> {
            if (b1.y == b2.y) return 0;
            switch (bedrockType) {
                case BEDROCK_FLOOR -> {
                    return b1.y < b2.y ? 1 : -1; // descending order
                }
                case BEDROCK_ROOF -> {
                    return b1.y < b2.y ? -1 : 1; // ascending order
                }
                default -> throw new RuntimeException("must declare a bedrock type (second argument) either floor or roof");
            }
        });
        //blocks.forEach(block -> System.out.println(block));
        bedrockReader = new BedrockReader(seed, bedrockType);

        int minX = -1000, maxX = 1000;
        int minZ = -1000, maxZ = 1000;
        // if you know the y level of the first block in the file, this should be it (minY == maxY == yLevel)
        // if you're not sure, you can widen the range
        int minY = -60, maxY = -60;

        for (int x = minX; x <= maxX; x++) {
            for (int z = minZ; z <= maxZ; z++) {
                for (int y = minY; y <= maxY; y++) {
                    if (checkFormation(x, y, z)) {
                        System.out.println("Found Bedrock Formation at X:" + x + " Y: " + y + " Z:" + z);
                    }
                }
            }
        }
    }

    static boolean checkFormation(int x, int y, int z) {
        for (BedrockBlock block : blocks) {
            if (block.shouldBeBedrock != bedrockReader.isBedrock(x + block.x, y + block.y, z + block.z)) return false;
        }
        return true;
    }
}

# Minecraft-Bedrock-Formation-Finder-1.18
Tool to find any bedrock formation in a 1.18 (or more recent, probably) minecraft world.

## Usage
You need to build a jar file first: open with IntelliJ, Build -> Build Artifacts.
You most likely want to change the searchspace, you can do so by editing min or max x, y, z in Main.java.

`java -jar bedrockformation.jar seed bedrocktype /path/to/file.txt`
- seed (long)
  - Seed of the World
- bedrocktype (enum)
  - floor -> Searches on Bedrock floor
  - roof -> Searches on Bedrock roof
- file path
  contents: x y z shouldBeBedrock
  - shouldBeBedrock -> true for bedrock, false otherwise (deepslate, air, anything)
  see blocks.txt

Example:
`java -jar bedrockformation.jar -6710424978534945632 floor C:\javaprojects\Minecraft-Bedrock-Formation-Finder-1.18\blocks.txt`

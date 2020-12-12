package SeedFinder;

import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        String fileName = "D:\\all_quad_region_seeds.txt";

        BufferedReader objReader = new BufferedReader(new FileReader(fileName));

        String strCurrentLine;
        while ((strCurrentLine = objReader.readLine()) != null) {
            RegionSeeds regionSeed = new RegionSeeds(Long.parseLong(strCurrentLine));

            regionSeed.getStructureSeeds().parallelStream().forEach(structureSeed -> {
                structureSeed.getWorldSeeds().parallelStream().filter(WorldSeed::evaluate).forEach(worldSeed -> {
                    try {
                        ResultGenerator.simpleOutput(worldSeed);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            });
        }
    }
}
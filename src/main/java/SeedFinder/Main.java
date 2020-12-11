package SeedFinder;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        String fileName = "D:\\all_quad_region_seeds.txt";

        BufferedReader objReader = new BufferedReader(new FileReader(fileName));


        String strCurrentLine;
        while ((strCurrentLine = objReader.readLine()) != null) {
            RegionSeeds regionSeed = new RegionSeeds(Long.parseLong(strCurrentLine));
            regionSeed.getStructureSeeds().parallelStream().forEach( structureSeed -> {
                structureSeed.getWorldSeeds().parallelStream().forEach(worldSeed -> {
                    if(worldSeed.evaluate()) {
                        ResultGenerator.simpleOutput(worldSeed);
                    }
                });
            });
        }
/*
        seeds.parallelStream().forEach( regionSeed -> {
            regionSeed.getStructureSeeds().parallelStream().forEach( structureSeed -> {
                structureSeed.getWorldSeeds().parallelStream().forEach(worldSeed -> {
                    if(worldSeed.evaluate()) {
                        ResultGenerator.simpleOutput(worldSeed);
                    }
                });
            });
        });
    }
}
         */ /*

        List<RegionSeeds> seeds = new ArrayList<>();
        seeds.add(new RegionSeeds(Long.parseLong(objReader.readLine())));
        seeds.add(new RegionSeeds(Long.parseLong(objReader.readLine())));
        seeds.add(new RegionSeeds(Long.parseLong(objReader.readLine())));
        seeds.add(new RegionSeeds(Long.parseLong(objReader.readLine())));
*/ /*
        seeds.parallelStream().forEach( regionSeed -> {
            regionSeed.getStructureSeeds().parallelStream().forEach( structureSeed -> {
                structureSeed.getWorldSeeds().parallelStream().forEach(worldSeed -> {
                    if(worldSeed.evaluate()) {
                        ResultGenerator.simpleOutput(worldSeed);
                    }
                });
            });
        });
        */
    }
}
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
        List<RegionSeed> seeds = new ArrayList<>();
        while ((strCurrentLine = objReader.readLine()) != null) {
            seeds.add(new RegionSeed(Long.parseLong(strCurrentLine)));
        }

        seeds.parallelStream().forEach( regionSeed -> {
            regionSeed.getStructureSeeds().parallelStream().forEach( structureSeed -> {
                structureSeed.getWorldSeeds().parallelStream().forEach(worldSeed -> {
                    System.out.println(worldSeed);
                });
            });
        });
    }
}

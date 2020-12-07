import kaptainwutax.biomeutils.source.OverworldBiomeSource;
import kaptainwutax.featureutils.structure.SwampHut;
import kaptainwutax.seedutils.mc.ChunkRand;
import kaptainwutax.seedutils.mc.MCVersion;
import kaptainwutax.seedutils.mc.pos.CPos;
import kaptainwutax.seedutils.mc.seed.RegionSeed;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Test {
    public static void main(String[] args) throws IOException {

        final SwampHut WITCH_HUT = new SwampHut(MCVersion.v1_16_2);

        String fileName = "D:\\all_quad_region_seeds.txt";

        BufferedReader objReader = new BufferedReader(new FileReader(fileName));

        String strCurrentLine;
        ArrayList<Long> seeds = new ArrayList<>();
        while ((strCurrentLine = objReader.readLine()) != null) {
            seeds.add(Long.parseLong(strCurrentLine));
        }

        System.out.println(seeds.size());

        ChunkRand rand = new ChunkRand();

        int i;
        for (i=0; i < seeds.size(); i++ ) {

            Long structureSeed = RegionSeed.toWorldSeed(seeds.get(i), 1, 1, new SwampHut(MCVersion.v1_16_2).getSalt());

            CPos hut1 = WITCH_HUT.getInRegion(structureSeed, 1, 1, rand);
            CPos hut2 = WITCH_HUT.getInRegion(structureSeed, 1, 0, rand);
            CPos hut3 = WITCH_HUT.getInRegion(structureSeed, 0, 1, rand);
            CPos hut4 = WITCH_HUT.getInRegion(structureSeed, 0, 0, rand);

            for (long upperBits = 0; upperBits < 1L << 16; upperBits++) {
                long worldSeed = (upperBits << 48) | structureSeed;

            OverworldBiomeSource source = new OverworldBiomeSource(MCVersion.v1_16_2, worldSeed);

            if (!WITCH_HUT.canSpawn(hut1.getX(), hut1.getZ(), source)) continue;
            if (!WITCH_HUT.canSpawn(hut2.getX(), hut2.getZ(), source)) continue;
            if (!WITCH_HUT.canSpawn(hut3.getX(), hut3.getZ(), source)) continue;
            if (!WITCH_HUT.canSpawn(hut4.getX(), hut4.getZ(), source)) continue;

            System.out.println("Success at " + worldSeed);
            }
        }
    }
}
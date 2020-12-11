package SeedFinder;

import kaptainwutax.featureutils.structure.SwampHut;
import kaptainwutax.seedutils.mc.MCVersion;
import kaptainwutax.seedutils.mc.seed.RegionSeed;

import java.util.ArrayList;
import java.util.List;

public class RegionSeeds {
    private Long seed;
    private Long iteration;
    private static final int TWELVE_DISTANCE = 16;
    private final SwampHut SWAMP_HUT = new SwampHut(MCVersion.v1_12_2);
    List<StructureSeed> structureSeeds = new ArrayList<>();

    RegionSeeds(Long seed){
        this.seed = seed;

        for (int regionX = -TWELVE_DISTANCE; regionX < TWELVE_DISTANCE; regionX++) {
            structureSeeds.add(new StructureSeed(RegionSeed.toWorldSeed(seed, regionX, 0, SWAMP_HUT.getSalt()),this,regionX,0));
        }

        for (int regionZ = -TWELVE_DISTANCE; regionZ < TWELVE_DISTANCE; regionZ++) {
            structureSeeds.add(new StructureSeed(RegionSeed.toWorldSeed(seed, 0, regionZ, SWAMP_HUT.getSalt()),this,0,regionZ));
        }
    }

    public Long getSeed() { return this.seed; }
    public List<StructureSeed> getStructureSeeds() { return this.structureSeeds; }
}

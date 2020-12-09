package SeedFinder;

import kaptainwutax.featureutils.structure.SwampHut;
import kaptainwutax.seedutils.mc.MCVersion;

import java.util.ArrayList;
import java.util.List;

public class RegionSeed {
    private Long seed;
    private static final int TWELVE_DISTANCE = 32;
    private final SwampHut SWAMP_HUT = new SwampHut(MCVersion.v1_12_2);
    List<StructureSeed> structureSeeds = new ArrayList<>();

    RegionSeed(Long seed){
        this.seed = seed;

        for (int regionX = -TWELVE_DISTANCE; regionX < TWELVE_DISTANCE; regionX++) {
            structureSeeds.add(new StructureSeed(kaptainwutax.seedutils.mc.seed.RegionSeed.toWorldSeed(seed, regionX, 0, SWAMP_HUT.getSalt()),this,regionX,0));
            structureSeeds.add(new StructureSeed(kaptainwutax.seedutils.mc.seed.RegionSeed.toWorldSeed(seed, regionX, 1, SWAMP_HUT.getSalt()),this,regionX,1));
        }

        for (int regionZ = -TWELVE_DISTANCE; regionZ < TWELVE_DISTANCE; regionZ++) {
            structureSeeds.add(new StructureSeed(kaptainwutax.seedutils.mc.seed.RegionSeed.toWorldSeed(seed, 0, regionZ, SWAMP_HUT.getSalt()),this,0,regionZ));
            structureSeeds.add(new StructureSeed(kaptainwutax.seedutils.mc.seed.RegionSeed.toWorldSeed(seed, 1, regionZ, SWAMP_HUT.getSalt()),this,0,regionZ));
        }
    }

    public Long getSeed() { return this.seed; }
    public List<StructureSeed> getStructureSeeds() { return this.structureSeeds; }
}

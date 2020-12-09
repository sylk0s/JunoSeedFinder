package SeedFinder;

import kaptainwutax.featureutils.structure.SwampHut;
import kaptainwutax.seedutils.mc.ChunkRand;
import kaptainwutax.seedutils.mc.MCVersion;
import kaptainwutax.seedutils.mc.pos.CPos;

import java.util.ArrayList;
import java.util.List;

public class StructureSeed {
    private Long seed;
    private RegionSeed regionSeed;
    List<WorldSeed> worldSeeds = new ArrayList<>();
    private final SwampHut SWAMP_HUT = new SwampHut(MCVersion.v1_12_2);

    private CPos hut1;
    private CPos hut2;
    private CPos hut3;
    private CPos hut4;

    StructureSeed(Long seed, RegionSeed regionSeed, int quadX, int quadZ) {
        this.seed = seed;
        this.regionSeed = regionSeed;

        ChunkRand rand= new ChunkRand();
        this.hut1 = SWAMP_HUT.getInRegion(seed,quadX,quadZ,rand);
        this.hut2 = SWAMP_HUT.getInRegion(seed,quadX-1,quadZ,rand);
        this.hut3 = SWAMP_HUT.getInRegion(seed,quadX,quadZ-1,rand);
        this.hut4 = SWAMP_HUT.getInRegion(seed,quadX-1,quadZ-1,rand);

        for (int upperBits = 0; upperBits < 1L << 16; upperBits++) {
            worldSeeds.add(new WorldSeed((upperBits << 48) | seed, this));
        }
    }

    public Long getSeed () { return this.seed; }
    public List<WorldSeed> getWorldSeeds() { return this.worldSeeds; }
}

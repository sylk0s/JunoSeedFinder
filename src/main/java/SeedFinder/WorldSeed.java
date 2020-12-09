package SeedFinder;

import kaptainwutax.featureutils.structure.SwampHut;
import kaptainwutax.seedutils.mc.MCVersion;

import java.util.ArrayList;
import java.util.List;

public class WorldSeed {
    private Long seed;
    private StructureSeed structureSeed;

    WorldSeed(Long seed, StructureSeed structureSeed) {
        this.seed = seed;
        this.structureSeed = structureSeed;
    }

    public Long getSeed () { return this.seed; }
}
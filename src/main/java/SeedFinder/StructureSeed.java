package SeedFinder;

import kaptainwutax.biomeutils.source.OverworldBiomeSource;
import kaptainwutax.featureutils.structure.Monument;
import kaptainwutax.featureutils.structure.SwampHut;
import kaptainwutax.mathutils.util.Mth;
import kaptainwutax.seedutils.mc.ChunkRand;
import kaptainwutax.seedutils.mc.MCVersion;
import kaptainwutax.seedutils.mc.pos.CPos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StructureSeed {
    private Long seed;
    private Long iteration;
    private RegionSeeds regionSeed;
    List<WorldSeed> worldSeeds = new ArrayList<>();
    private final SwampHut SWAMP_HUT = new SwampHut(MCVersion.v1_12_2);
    private final int SIXTEEN_DISTANCE = 64;
    private List<CPos> monuments;
    private Map<String,CPos> monumentMap = new HashMap<>();

    private CPos[] huts = new CPos[4];

    //public CPos hut1;
    //public CPos hut2;
    //public CPos hut3;
    //public CPos hut4;

    private int quadX;
    private int quadZ;

    StructureSeed(Long seed, RegionSeeds regionSeed, int quadX, int quadZ) {
        this.seed = seed;
        this.regionSeed = regionSeed;
        this.quadX = quadX;
        this.quadZ = quadZ;
        getMonumentsFromSeed();

        ChunkRand rand= new ChunkRand();
        //this.hut1 = SWAMP_HUT.getInRegion(seed,quadX,quadZ,rand);
        //this.hut2 = SWAMP_HUT.getInRegion(seed,quadX-1,quadZ,rand);
        //this.hut3 = SWAMP_HUT.getInRegion(seed,quadX,quadZ-1,rand);
        //this.hut4 = SWAMP_HUT.getInRegion(seed,quadX-1,quadZ-1,rand);

        this.huts[0] = SWAMP_HUT.getInRegion(seed,quadX,quadZ,rand);
        this.huts[1] = SWAMP_HUT.getInRegion(seed,quadX-1,quadZ,rand);
        this.huts[2] = SWAMP_HUT.getInRegion(seed,quadX,quadZ-1,rand);
        this.huts[3] = SWAMP_HUT.getInRegion(seed,quadX-1,quadZ-1,rand);

        for (long upperBits = 0; upperBits < 1L << 16; upperBits++) {
            worldSeeds.add(new WorldSeed((upperBits << 48) | seed, this, upperBits));
        }
    }

    public Long getSeed () { return this.seed; }
    public List<WorldSeed> getWorldSeeds() { return this.worldSeeds; }
    public CPos getHut(int index) { return this.huts[index]; }
    public Map<String, CPos> getMonuments() { return monumentMap; }

    private void getMonumentsFromSeed() {
        ChunkRand rand = new ChunkRand();
        Monument OCEAN_MONUMENT = new Monument(MCVersion.v1_16_2);


        for (int regionX = -SIXTEEN_DISTANCE; regionX < SIXTEEN_DISTANCE; regionX++) {
            CPos structure = OCEAN_MONUMENT.getInRegion(seed, regionX, 0, rand);
            if (structure != null) {
                //monuments.add(structure);
                monumentMap.put(String.format("%1$s_%2$s",structure.getX(),structure.getZ()),structure);
            }
            structure = OCEAN_MONUMENT.getInRegion(seed, regionX, -1, rand);
            if (structure != null) {
                //monuments.add(structure);
                monumentMap.put(String.format("%1$s_%2$s",structure.getX(),structure.getZ()),structure);
            }
        }

        for (int regionZ = -SIXTEEN_DISTANCE; regionZ < SIXTEEN_DISTANCE; regionZ++) {
            CPos structure = OCEAN_MONUMENT.getInRegion(seed, 0, regionZ, rand);
            if (structure != null) {
                //monuments.add(structure);
                monumentMap.put(String.format("%1$s_%2$s",structure.getX(),structure.getZ()),structure);
            }
            structure = OCEAN_MONUMENT.getInRegion(seed, -1, regionZ, rand);
            if (structure != null) {
                //monuments.add(structure);
                monumentMap.put(String.format("%1$s_%2$s",structure.getX(),structure.getZ()),structure);
            }
        }
        //return monuments;
    }
}

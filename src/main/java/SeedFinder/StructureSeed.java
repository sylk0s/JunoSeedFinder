package SeedFinder;

import kaptainwutax.featureutils.structure.Monument;
import kaptainwutax.featureutils.structure.SwampHut;
import kaptainwutax.seedutils.mc.ChunkRand;
import kaptainwutax.seedutils.mc.MCVersion;
import kaptainwutax.seedutils.mc.pos.CPos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StructureSeed {
    private Long seed;
    private RegionSeeds regionSeed;
    List<WorldSeed> worldSeeds = new ArrayList<>();
    private final SwampHut SWAMP_HUT = new SwampHut(MCVersion.v1_12_2);
    private final int SIXTEEN_DISTANCE = 32;
    private Map<String,CPos> monumentMap = new HashMap<>();

    private CPos[] huts = new CPos[4];

    StructureSeed(Long seed, RegionSeeds regionSeed, int quadX, int quadZ) {
        this.seed = seed;
        this.regionSeed = regionSeed;
        getMonumentsFromSeed();

        if(canMonumentSpawn()) {
            ChunkRand rand= new ChunkRand();

            this.huts[0] = SWAMP_HUT.getInRegion(seed,quadX,quadZ,rand);
            this.huts[1] = SWAMP_HUT.getInRegion(seed,quadX-1,quadZ,rand);
            this.huts[2] = SWAMP_HUT.getInRegion(seed,quadX,quadZ-1,rand);
            this.huts[3] = SWAMP_HUT.getInRegion(seed,quadX-1,quadZ-1,rand);
                for (long upperBits = 0; upperBits < 1L << 16; upperBits++) {
                    worldSeeds.add(new WorldSeed((upperBits << 48) | seed, this));
                }
        }
    }

    public Long getSeed () { return this.seed;}
    public List<WorldSeed> getWorldSeeds() { return this.worldSeeds; }
    public CPos getHut(int index) { return this.huts[index]; }
    public Map<String, CPos> getMonuments() { return monumentMap; }

    private void getMonumentsFromSeed() {
        ChunkRand rand = new ChunkRand();
        Monument OCEAN_MONUMENT = new Monument(MCVersion.v1_12_2);

        for (int regionX = -SIXTEEN_DISTANCE; regionX < SIXTEEN_DISTANCE; regionX++) {
            CPos structure = OCEAN_MONUMENT.getInRegion(seed, regionX, 0, rand);
            if (structure != null) {
                monumentMap.put(String.format("%1$s_%2$s",structure.getX(),structure.getZ()),structure);
            }
            structure = OCEAN_MONUMENT.getInRegion(seed, regionX, -1, rand);
            if (structure != null) {
                monumentMap.put(String.format("%1$s_%2$s",structure.getX(),structure.getZ()),structure);
            }
        }

        for (int regionZ = -SIXTEEN_DISTANCE; regionZ < SIXTEEN_DISTANCE; regionZ++) {
            CPos structure = OCEAN_MONUMENT.getInRegion(seed, 0, regionZ, rand);
            if (structure != null) {
                monumentMap.put(String.format("%1$s_%2$s",structure.getX(),structure.getZ()),structure);
            }
            structure = OCEAN_MONUMENT.getInRegion(seed, -1, regionZ, rand);
            if (structure != null) {
                monumentMap.put(String.format("%1$s_%2$s",structure.getX(),structure.getZ()),structure);
            }
        }
    }

    private boolean canMonumentSpawn() {
        for (CPos monument1 : monumentMap.values()) {
            String[] keys = new String[4];
            keys[0] = String.format("%1$s_%2$s", (monument1.getX() + 10), monument1.getZ());
            keys[1] = String.format("%1$s_%2$s", (monument1.getX() - 10), monument1.getZ());
            keys[2] = String.format("%1$s_%2$s", monument1.getX(), (monument1.getZ() + 10));
            keys[3] = String.format("%1$s_%2$s", monument1.getX(), (monument1.getZ() - 10));
            for (String key : keys) {
                if (monumentMap.containsKey(key)) {
                    return true;
                }
            }
        }
        return false;
    }
}

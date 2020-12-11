package SeedFinder;

import kaptainwutax.biomeutils.source.OverworldBiomeSource;
import kaptainwutax.featureutils.structure.Monument;
import kaptainwutax.featureutils.structure.PillagerOutpost;
import kaptainwutax.featureutils.structure.SwampHut;
import kaptainwutax.mathutils.util.Mth;
import kaptainwutax.seedutils.mc.ChunkRand;
import kaptainwutax.seedutils.mc.MCVersion;
import kaptainwutax.seedutils.mc.pos.CPos;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class WorldSeed {
    private Long seed;
    private Long iteration;
    private StructureSeed structureSeed;
    private static final int SIXTEEN_DISTANCE = 32;

    private CPos mon1;
    private CPos mon2;

    WorldSeed(Long seed, StructureSeed structureSeed, Long iteration) {
        this.seed = seed;
        this.structureSeed = structureSeed;
        this.iteration = iteration;
    }

    public Long getIteration() { return this.iteration; }

    public Long getSeed() {
        return this.seed;
    }

    public StructureSeed getStructureSeed() { return this.structureSeed; }

    public CPos getMon1() {return mon1;}
    public CPos getMon2() {return mon2;}

    public boolean evaluate() {
        return this.qwhBiomeCheck() &&
                this.hasDoubleMonument(); //&&
                //this.forValidArea(this::hasOutpost);
    }

    private boolean qwhBiomeCheck() {
        OverworldBiomeSource source12 = new OverworldBiomeSource(MCVersion.v1_12_2, seed);
        SwampHut SWAMP_HUT = new SwampHut(MCVersion.v1_12_2);
        return SWAMP_HUT.canSpawn(structureSeed.getHut(0).getX(), structureSeed.getHut(0).getZ(), source12) &&
                SWAMP_HUT.canSpawn(structureSeed.getHut(1).getX(), structureSeed.getHut(1).getZ(), source12) &&
                SWAMP_HUT.canSpawn(structureSeed.getHut(2).getX(), structureSeed.getHut(2).getZ(), source12) &&
                SWAMP_HUT.canSpawn(structureSeed.getHut(3).getX(), structureSeed.getHut(3).getZ(), source12);
    }

    private boolean forValidArea(Function<CPos, Boolean> check) {

        for (int regionX = -SIXTEEN_DISTANCE; regionX < SIXTEEN_DISTANCE; regionX++) {
            if (check.apply(new CPos(regionX, 0))) return true;
            if (check.apply(new CPos(regionX, -1))) return true;
        }

        for (int regionZ = -SIXTEEN_DISTANCE; regionZ < SIXTEEN_DISTANCE; regionZ++) {
            if (check.apply(new CPos(0, regionZ))) return true;
            if (check.apply(new CPos(-1, regionZ))) return true;
        }
        return false;
    }

    private boolean hasOutpost(CPos reg) {
        ChunkRand rand = new ChunkRand();
        OverworldBiomeSource source16 = new OverworldBiomeSource(MCVersion.v1_16_2, seed);
        PillagerOutpost OUTPOST = new PillagerOutpost(MCVersion.v1_16_2);
        CPos structure = OUTPOST.getInRegion(seed & Mth.MASK_48, reg.getX(), reg.getZ(), rand);
        return structure != null && OUTPOST.canSpawn(structure.getX(), structure.getZ(), source16);
    }

    /* private List<CPos> getMonuments() {
        ChunkRand rand = new ChunkRand();
        OverworldBiomeSource source16 = new OverworldBiomeSource(MCVersion.v1_16_2, seed);
        Monument OCEAN_MONUMENT = new Monument(MCVersion.v1_16_2);
        List<CPos> monuments = new ArrayList<>();

        for (int regionX = -SIXTEEN_DISTANCE; regionX < SIXTEEN_DISTANCE; regionX++) {
            if (OCEAN_MONUMENT.getInRegion(seed & Mth.MASK_48, regionX, 0, rand) != null) {
                CPos structure = OCEAN_MONUMENT.getInRegion(seed & Mth.MASK_48, regionX, 0, rand);
                if (OCEAN_MONUMENT.canSpawn(structure.getX(), structure.getZ(), source16)) {
                    monuments.add(structure);
                }
            }
            if (OCEAN_MONUMENT.getInRegion(seed & Mth.MASK_48, regionX, -1, rand) != null) {
                CPos structure = OCEAN_MONUMENT.getInRegion(seed & Mth.MASK_48, regionX, -1, rand);
                if (OCEAN_MONUMENT.canSpawn(structure.getX(), structure.getZ(), source16)) {
                    monuments.add(structure);
                }
            }
        }

        for (int regionZ = -SIXTEEN_DISTANCE; regionZ < SIXTEEN_DISTANCE; regionZ++) {
            if (OCEAN_MONUMENT.getInRegion(seed & Mth.MASK_48, 0, regionZ, rand) != null) {
                CPos structure = OCEAN_MONUMENT.getInRegion(seed & Mth.MASK_48, 0, regionZ, rand);
                if (OCEAN_MONUMENT.canSpawn(structure.getX(), structure.getZ(), source16)) {
                    monuments.add(structure);
                }
            }
            if (OCEAN_MONUMENT.getInRegion(seed & Mth.MASK_48, -1, regionZ, rand) != null) {
                CPos structure = OCEAN_MONUMENT.getInRegion(seed & Mth.MASK_48, -1, regionZ, rand);
                if (OCEAN_MONUMENT.canSpawn(structure.getX(), structure.getZ(), source16)) {
                    monuments.add(structure);
                }
            }
        }
        return monuments;
    } */

    private boolean hasDoubleMonument () {

       // List<CPos> monumentList = new ArrayList<>();
        OverworldBiomeSource source16 = new OverworldBiomeSource(MCVersion.v1_16_2, seed);
        Monument OCEAN_MONUMENT = new Monument(MCVersion.v1_16_2);

        //for (CPos monument : structureSeed.getMonuments().values()) {
        //    if (OCEAN_MONUMENT.canSpawn(monument.getX(), monument.getZ(), source16)) { monumentList.add(monument); }
      // }

        int diff = 10;
        //for (CPos monument1 : monumentList) {
        for (CPos monument1 : structureSeed.getMonuments().values()) {
            if (OCEAN_MONUMENT.canSpawn(monument1.getX(), monument1.getZ(), source16)) {
                String[] keys = new String[4];
                keys[0] = String.format("%1$s_%2$s", (monument1.getX() + 10), monument1.getZ());
                keys[1] = String.format("%1$s_%2$s", (monument1.getX() - 10), monument1.getZ());
                keys[2] = String.format("%1$s_%2$s", monument1.getX(), (monument1.getZ() + 10));
                keys[3] = String.format("%1$s_%2$s", monument1.getX(), (monument1.getZ() - 10));
                for (String key : keys) {
                    if (structureSeed.getMonuments().containsKey(key)) {
                        CPos monument2 = structureSeed.getMonuments().get(key);
                        if (OCEAN_MONUMENT.canSpawn(monument2.getX(), monument2.getZ(), source16)) {
                            mon1 = monument1;
                            mon2 = monument2;
                            return true;
                        }
                    }
                }
                return false;
            }
            /*for (CPos monument2 : monumentList) {
                if (!(monument1.getX() == monument2.getX() && monument1.getZ() == monument2.getZ())) {
                    if ((Math.abs(monument1.getX() - monument2.getX()) == diff && Math.abs(monument1.getZ() - monument2.getZ()) == 0)||(Math.abs(monument1.getX() - monument2.getX()) == 0 && Math.abs(monument1.getZ() - monument2.getZ()) == diff)) { //TODO can make this better by using 128 radius
                        mon1 = monument1;
                        mon2 = monument2;
                        return true;
                    }
                }
            }*/
        }
        return false;
    }
}
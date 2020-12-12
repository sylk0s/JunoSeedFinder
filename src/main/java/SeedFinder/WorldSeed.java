package SeedFinder;

import kaptainwutax.biomeutils.Biome;
import kaptainwutax.biomeutils.source.OverworldBiomeSource;
import kaptainwutax.featureutils.structure.Monument;
import kaptainwutax.featureutils.structure.PillagerOutpost;
import kaptainwutax.featureutils.structure.SwampHut;
import kaptainwutax.mathutils.util.Mth;
import kaptainwutax.seedutils.mc.ChunkRand;
import kaptainwutax.seedutils.mc.MCVersion;
import kaptainwutax.seedutils.mc.pos.CPos;

import java.util.function.Function;

public class WorldSeed {
    private Long seed;
    private StructureSeed structureSeed;
    private static final int SIXTEEN_DISTANCE = 32;

    private CPos mon1;
    private CPos mon2;

    WorldSeed(Long seed, StructureSeed structureSeed) {
        this.seed = seed;
        this.structureSeed = structureSeed;
    }

    public StructureSeed getStructureSeed() { return this.structureSeed; }

    public Long getSeed() {return seed;}
    public CPos getMon1() {return mon1;}
    public CPos getMon2() {return mon2;}

    public boolean evaluate() {
        return this.qwhBiomeCheck() &&
                this.hasDoubleMonument() &&
                this.forValidArea(this::hasMushroomIsland) &&
                this.forValidArea(this::hasOutpost);
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

    private boolean hasDoubleMonument () {

        OverworldBiomeSource source16 = new OverworldBiomeSource(MCVersion.v1_12_2, seed);
        Monument OCEAN_MONUMENT = new Monument(MCVersion.v1_12_2);

        int diff = 10;
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
        }
        return false;
    }

    private boolean hasMushroomIsland(CPos pos) {
        OverworldBiomeSource source16 = new OverworldBiomeSource(MCVersion.v1_12_2, seed);
        Biome biome = source16.getBiomeForNoiseGen(pos.toBlockPos().getX(),0,pos.toBlockPos().getZ());
        return biome.getName() == "mushroom_fields";
    }
}
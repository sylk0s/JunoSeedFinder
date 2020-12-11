package SeedFinder;

public class ResultGenerator {

    public static void simpleOutput(WorldSeed seed) {
        System.out.println(seed.getSeed());
        System.out.println("Hut 1 at: " + seed.getStructureSeed().getHut(0).toBlockPos().getX() + " " + seed.getStructureSeed().getHut(0).toBlockPos().getZ());
        System.out.println("Hut 2 at: " + seed.getStructureSeed().getHut(1).toBlockPos().getX() + " " + seed.getStructureSeed().getHut(1).toBlockPos().getZ());
        System.out.println("Hut 3 at: " + seed.getStructureSeed().getHut(2).toBlockPos().getX() + " " + seed.getStructureSeed().getHut(2).toBlockPos().getZ());
        System.out.println("Hut 4 at: " + seed.getStructureSeed().getHut(3).toBlockPos().getX() + " " + seed.getStructureSeed().getHut(3).toBlockPos().getZ());
        System.out.println("Monument 1 at: " + seed.getMon1().toBlockPos().getX() + " " + seed.getMon1().toBlockPos().getZ());
        System.out.println("Monument 2 at: " + seed.getMon2().toBlockPos().getX() + " " + seed.getMon2().toBlockPos().getZ());
        System.out.println("Number " + seed.getIteration() + " of 65536");
    }
}
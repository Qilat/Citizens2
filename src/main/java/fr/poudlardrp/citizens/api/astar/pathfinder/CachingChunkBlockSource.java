package fr.poudlardrp.citizens.api.astar.pathfinder;

import com.google.common.collect.Maps;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.Map;

public abstract class CachingChunkBlockSource<T> extends BlockSource {
    protected final World world;
    private final Map<ChunkCoord, ChunkCache> chunkCache = Maps.newHashMap();
    private final Object[][] chunks;
    private final int chunkX;
    private final int chunkZ;

    protected CachingChunkBlockSource(Location location, float radius) {
        this(location.getWorld(), location.getBlockX(), location.getBlockZ(), radius);
    }

    protected CachingChunkBlockSource(World world, int x, int z, float radius) {
        this(world, (int) (x - radius), (int) (z - radius), (int) (x + radius), (int) (z + radius));
    }

    protected CachingChunkBlockSource(World world, int minX, int minZ, int maxX, int maxZ) {
        this.world = world;
        this.chunkX = minX >> 4;
        this.chunkZ = minZ >> 4;
        int maxChunkX = maxX >> 4, maxChunkZ = maxZ >> 4;

        chunks = new Object[maxChunkX - chunkX + 1][maxChunkZ - chunkZ + 1];
        for (int x = chunkX; x < maxChunkX; x++) {
            for (int z = chunkZ; z < maxChunkZ; z++) {
                chunks[x - chunkX][z - chunkZ] = getChunkObject(x, z);
            }
        }
    }

    @Override
    public int getBlockTypeIdAt(int x, int y, int z) {
        T chunk = getSpecific(x, z);
        if (chunk != null)
            return getId(chunk, x & 15, y, z & 15);
        return world.getBlockTypeIdAt(x, y, z);
    }

    protected abstract T getChunkObject(int x, int z);

    protected abstract int getId(T chunk, int x, int y, int z);

    protected abstract int getLightLevel(T chunk, int x, int y, int z);

    @SuppressWarnings("unchecked")
    private T getSpecific(int x, int z) {
        int xx = (x >> 4) - chunkX;
        int zz = (z >> 4) - chunkZ;
        if (xx >= 0 && xx < chunks.length) {
            Object[] inner = chunks[xx];
            if (zz >= 0 && zz < inner.length) {
                return (T) inner[zz];
            }
        }
        ChunkCoord key = new ChunkCoord(x >> 4, z >> 4);
        ChunkCache prev = chunkCache.get(key);
        if (prev == null) {
            chunkCache.put(key, prev = new ChunkCache());
        } else if (prev.obj != null) {
            return prev.obj;
        } else if (++prev.hitCount >= 2) {
            return prev.obj = getChunkObject(x >> 4, z >> 4);
        }
        return null;
    }

    @Override
    public World getWorld() {
        return world;
    }

    private static class ChunkCoord {
        int x, z;

        public ChunkCoord(int xx, int zz) {
            this.x = xx;
            this.z = zz;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            ChunkCoord other = (ChunkCoord) obj;
            return x == other.x && z == other.z;
        }

        @Override
        public int hashCode() {
            int result = 31 * x;
            return 31 * result + z;
        }
    }

    private class ChunkCache {
        int hitCount;
        T obj;
    }
}
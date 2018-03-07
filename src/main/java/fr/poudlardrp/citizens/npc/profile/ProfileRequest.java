package fr.poudlardrp.citizens.npc.profile;

import com.google.common.base.Preconditions;
import com.mojang.authlib.GameProfile;
import fr.poudlardrp.citizens.api.CitizensAPI;
import org.bukkit.Bukkit;

import javax.annotation.Nullable;
import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Stores basic information about a single profile used to request profiles from the Mojang servers.
 * <p>
 * <p>
 * Also stores the result of the request.
 * </p>
 */
public class ProfileRequest {
    private final String playerName;
    private Deque<ProfileFetchHandler> handlers;
    private GameProfile profile;
    private volatile ProfileFetchResult result = ProfileFetchResult.PENDING;

    /**
     * Constructor.
     *
     * @param playerName The name of the player whose profile is being requested.
     * @param handler    Optional handler to handle the result for the profile. Handler always invoked from the main thread.
     */
    ProfileRequest(String playerName, @Nullable ProfileFetchHandler handler) {
        Preconditions.checkNotNull(playerName);

        this.playerName = playerName;

        if (handler != null) {
            addHandler(handler);
        }
    }

    /**
     * Add one time result handler.
     * <p>
     * <p>
     * Handler is always invoked from the main thread.
     * </p>
     *
     * @param handler The result handler.
     */
    public void addHandler(ProfileFetchHandler handler) {
        Preconditions.checkNotNull(handler);

        if (result != ProfileFetchResult.PENDING) {
            handler.onResult(this);
            return;
        }

        if (handlers == null)
            handlers = new ArrayDeque<ProfileFetchHandler>();

        handlers.addLast(handler);
    }

    /**
     * Get the name of the player the requested profile belongs to.
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Get the game profile that was requested.
     *
     * @return The game profile or null if the profile has not been retrieved yet or there was an error while retrieving
     * the profile.
     */
    @Nullable
    public GameProfile getProfile() {
        return profile;
    }

    /**
     * Get the result of the profile fetch.
     */
    public ProfileFetchResult getResult() {
        return result;
    }

    /**
     * Invoked to set the profile result.
     * <p>
     * <p>
     * Can be invoked from any thread, always executes on the main thread.
     * </p>
     *
     * @param profile The profile. Null if there was an error.
     * @param result  The result of the request.
     */
    void setResult(final @Nullable GameProfile profile, final ProfileFetchResult result) {
        if (!CitizensAPI.hasImplementation())
            return;
        Bukkit.getScheduler().scheduleSyncDelayedTask(CitizensAPI.getPlugin(), new Runnable() {
            @Override
            public void run() {
                ProfileRequest.this.profile = profile;
                ProfileRequest.this.result = result;

                if (handlers == null)
                    return;

                while (!handlers.isEmpty()) {
                    handlers.removeFirst().onResult(ProfileRequest.this);
                }

                handlers = null;
            }
        });
    }
}
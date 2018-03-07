package fr.poudlardrp.citizens.nms.v1_10_R1.network;

import net.minecraft.server.v1_10_R1.*;

public class EmptyNetHandler extends PlayerConnection {
    public EmptyNetHandler(MinecraftServer minecraftServer, NetworkManager networkManager, EntityPlayer entityPlayer) {
        super(minecraftServer, networkManager, entityPlayer);
    }

    @Override
    public void sendPacket(Packet<?> packet) {
    }
}
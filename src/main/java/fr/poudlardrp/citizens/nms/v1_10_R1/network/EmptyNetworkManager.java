package fr.poudlardrp.citizens.nms.v1_10_R1.network;

import net.minecraft.server.v1_10_R1.EnumProtocolDirection;
import net.minecraft.server.v1_10_R1.NetworkManager;
import net.poudlardcitizens.nms.v1_10_R1.util.NMSImpl;

import java.io.IOException;

public class EmptyNetworkManager extends NetworkManager {
    public EmptyNetworkManager(EnumProtocolDirection flag) throws IOException {
        super(flag);
        NMSImpl.initNetworkManager(this);
    }

    @Override
    public boolean isConnected() {
        return true;
    }
}
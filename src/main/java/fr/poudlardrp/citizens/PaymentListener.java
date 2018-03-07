package fr.poudlardrp.citizens;

import com.google.common.base.Preconditions;
import fr.poudlardrp.citizens.util.Messages;
import fr.poudlardrp.citizens.api.event.PlayerCreateNPCEvent;
import fr.poudlardrp.citizens.api.util.Messaging;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import net.poudlardcitizens.Settings.Setting;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PaymentListener implements Listener {
    private final Economy provider;

    public PaymentListener(Economy provider) {
        Preconditions.checkNotNull(provider, "provider cannot be null");
        this.provider = provider;
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerCreateNPC(PlayerCreateNPCEvent event) {
        boolean hasAccount = provider.hasAccount(event.getCreator());
        if (!hasAccount || event.getCreator().hasPermission("citizens.npc.ignore-cost"))
            return;
        double cost = Setting.NPC_COST.asDouble();
        EconomyResponse response = provider.withdrawPlayer(event.getCreator(), cost);
        if (!response.transactionSuccess()) {
            event.setCancelled(true);
            event.setCancelReason(response.errorMessage);
            return;
        }
        String formattedCost = provider.format(cost);
        Messaging.sendTr(event.getCreator(), Messages.MONEY_WITHDRAWN, formattedCost);
    }
}

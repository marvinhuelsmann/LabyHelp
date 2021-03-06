package de.labyhelp.addon.commands.feature;

import de.labyhelp.addon.LabyHelp;
import de.labyhelp.addon.LabyPlayer;
import de.labyhelp.addon.util.TranslationManager;
import de.labyhelp.addon.util.commands.HelpCommand;
import net.labymod.utils.UUIDFetcher;


import java.util.UUID;

public class InvitesCMD implements HelpCommand {
    @Override
    public String getName() {
        return "lhinvites";
    }

    @Override
    public void execute(LabyPlayer labyPlayer, String[] args) {
        TranslationManager transManager = LabyHelp.getInstance().getTranslationManager();
        if (args.length == 2) {

            LabyHelp.getInstance().getExecutor().submit(() -> {
                final UUID uuid = UUIDFetcher.getUUID(args[1]);

                if (LabyHelp.getInstance().getGroupManager().userGroups.containsKey(uuid)) {
                    if (LabyHelp.getInstance().getInviteManager().getNowInvites().equalsIgnoreCase("1")) {
                        labyPlayer.sendDefaultMessage("§f" + args[1].toUpperCase() +  transManager.getTranslation("likes.has.only") + LabyHelp.getInstance().getInviteManager().getInvites(uuid) + transManager.getTranslation("invite.points") + "!");
                    } else {
                        labyPlayer.sendDefaultMessage("§f" + args[1].toUpperCase() + transManager.getTranslation("likes.has")  + LabyHelp.getInstance().getInviteManager().getInvites(uuid) + transManager.getTranslation("invite.points") +"!");
                    }
                } else {
                    labyPlayer.sendTranslMessage("main.hasnot");
                }
            });

        } else {
            labyPlayer.sendDefaultMessage("/lhinvites -"  + transManager.getTranslation("player"));
        }
    }
}

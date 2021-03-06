package de.labyhelp.addon;

import de.labyhelp.addon.enums.SocialMediaType;
import net.labymod.main.LabyMod;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.Map;
import java.util.UUID;

public class LabyPlayer {

    private final UUID uuid;

    public LabyPlayer(UUID uuid) {
        this.uuid = uuid;
    }

    public static final String prefix = "§8[" + "§e" + "Helper" + "§8" + "]" + "§f";
    public final String developerPrefix = "§8" + "[" + "§e" + "Dev-Helper" + "§8" + "]" + "§8";


    public String getSocialMedia(SocialMediaType socialMedia) {
        LabyHelp.getInstance().getTagManager().readServerPartner();


        LabyHelp.getInstance().sendDeveloperMessage("get exact social media: " + socialMedia.getName() + socialMedia.getUrlName());

        if (socialMedia.getMap().containsKey(uuid)) {
            for (final Map.Entry<UUID, String> entry : socialMedia.getMap().entrySet()) {
                if (entry.getKey() != null) {
                    if (entry.getKey().equals(uuid)) {
                        return entry.getValue();
                    }
                }
            }
        } else {
            sendTranslMessage("social.null");
        }
        return null;
    }

    public void openSocial(UUID uuid, String name) {
        try {
            LabyMod.getInstance().openWebpage("https://www.labyhelp.de/p.php?search=" + name, false);
        } catch (Exception ignored) {
            sendError();
        }
    }

    public void sendServer(String name) {

        StringSelection stringSelection = new StringSelection(name);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);

        if (name != null && !name.equals("")) {
            sendDefaultMessage(LabyHelp.getInstance().getTranslationManager().getTranslation("social.server") + " " + "§c" + name + "§7" + LabyHelp.getInstance().getTranslationManager().getTranslation("main.clipboard"));

            LabyMod.getInstance().getLabyModAPI().connectToServer(name);
        } else {
            LabyHelp.getInstance().sendDefaultMessage("§c" + "No Server found! Error 404");
        }
    }


    public void sendAdversting(boolean tip) {
        sendTranslMessage("main.adversting");
        sendDefaultMessage("§e" + "LabyHelp Teamspeak:" + "§l" + " https://labyhelp.de/teamspeak");
        sendDefaultMessage("§e" + "LabyHelp Discord:" + "§l" + " https://labyhelp.de/discord");

        if (tip) {
            sendTranslMessage("adversting.turnoff");
        }
    }

    public void openCapeUrl(UUID uuid) {
        if (uuid != null) {
            try {
                if (getPermissions(uuid)) {
                    LabyMod.getInstance().openWebpage("https://labyhelp.de/cape.php?uuid=" + uuid, false);
                }
            } catch (Exception ignored) {
                sendError();
            }
        } else {
            sendTranslMessage("main.not.exist");
        }
    }

    public void openDashBoard(UUID uuid, String token) {
        if (uuid != null) {
            try {
                LabyMod.getInstance().openWebpage("https://labyhelp.de/account/dashboard?token=" + token, false);
            } catch (Exception ignored) {
                sendError();
            }
        } else {
            sendTranslMessage("main.not.exist");
        }
    }

    public void openBandanaUrl(UUID uuid) {
        if (uuid != null) {
            try {
                if (getPermissions(uuid)) {
                    LabyMod.getInstance().openWebpage("https://labyhelp.de/bandana.php?uuid=" + uuid, false);
                }
            } catch (Exception ignored) {
                sendError();
            }
        } else {
            sendTranslMessage("main.not.exist");
        }
    }

    public void openSkin(UUID uuid) {
        if (uuid != null) {
            try {
                if (getPermissions(uuid)) {
                    LabyMod.getInstance().openWebpage("https://de.namemc.com/profile/" + uuid, false);
                }
            } catch (Exception ignored) {
                sendError();
            }
        } else {
            sendTranslMessage("main.not.exist");
        }
    }

    public boolean getPermissions(UUID uuid) {
        if (!LabyHelp.getInstance().getGroupManager().isPremium(uuid) || LabyHelp.getInstance().getGroupManager().isPremium(LabyMod.getInstance().getPlayerUUID())) {
            if (!LabyHelp.getInstance().getGroupManager().isTeam(uuid)) {
                return true;
            } else {
                sendTranslMessage("main.team");
            }
        } else {
            sendNoPermsMessage();
        }
        return false;
    }

    public String getCurrentServer() {
        if (LabyHelp.getInstance().getSettingsManager().isOnServer()) {
            return LabyMod.getInstance().getCurrentServerData().getIp();
        }
        return null;
    }

    public void sendNoPermsMessage() {
        sendTranslMessage("main.premium");
    }

    public void sendNoPerms() {
        sendTranslMessage("main.noperms");
    }

    public void sendError() {
        sendTranslMessage("main.error");
    }

    public void sendTranslMessage(String key) {
        if (LabyHelp.getInstance().getSettingsManager().onServer) {
            LabyMod.getInstance().displayMessageInChat(prefix + " " + LabyHelp.getInstance().getTranslationManager().getTranslation(key));
        }
    }

    public void sendSpecificTranslMessage(String key, String start) {
        if (LabyHelp.getInstance().getSettingsManager().onServer) {
            LabyMod.getInstance().displayMessageInChat(prefix + start + " " + LabyHelp.getInstance().getTranslationManager().getTranslation(key));
        }
    }

    public void sendAlertTranslMessage(String key) {
        if (LabyHelp.getInstance().getSettingsManager().onServer) {
            LabyMod.getInstance().displayMessageInChat(prefix + "§c" + " " + LabyHelp.getInstance().getTranslationManager().getTranslation(key));
        }
    }

    public void sendWarningTranslMessage(String key) {
        if (LabyHelp.getInstance().getSettingsManager().onServer) {
            LabyMod.getInstance().displayMessageInChat(prefix + "§e" + " " + LabyHelp.getInstance().getTranslationManager().getTranslation(key));
        }
    }


    public void sendSuccessTranslMessage(String key) {
        if (LabyHelp.getInstance().getSettingsManager().onServer) {
            LabyMod.getInstance().displayMessageInChat(prefix + "§a" + " " + LabyHelp.getInstance().getTranslationManager().getTranslation(key));
        }
    }

    public void sendDefaultMessage(String message) {
        if (LabyHelp.getInstance().getSettingsManager().onServer) {
            LabyMod.getInstance().displayMessageInChat(prefix + " " + message);
        }
    }

    public void sendDeveloperMessage(String message) {
        if (LabyHelp.getInstance().getSettingsManager().developerMode) {
            if (LabyHelp.getInstance().getSettingsManager().onServer) {
                LabyMod.getInstance().displayMessageInChat(developerPrefix + " " + message);
            }
        }
    }


    public UUID getUuid() {
        return this.uuid;
    }
}

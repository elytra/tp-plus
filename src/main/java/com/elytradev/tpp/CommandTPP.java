package com.elytradev.tpp;

import net.minecraft.command.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.WorldServer;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommandTPP extends CommandBase {
    @Override
    public String getName()
    {
        return "tpp";
    }

    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException{
        if(args.length < 1){return;}

        int dimensionid;
        EntityPlayer target;
        double x,y,z;

        if(args.length == 4) {
            target = (EntityPlayer) sender.getCommandSenderEntity();
            if(target==null){
                ITextComponent component = new TextComponentString(TextFormatting.RED + "/tpp cannot be run from the server!");
                sender.sendMessage(component);
            }
            dimensionid = parseInt(args[0]);
            x = parseDouble(args[1]);
            y = parseDouble(args[2]);
            z = parseDouble(args[3]);
        }
        else if(args.length == 5){
            if(EntitySelector.isSelector(args[0])){
                target = EntitySelector.matchOnePlayer(sender,args[0]);
            }
            else{
                target = server.getPlayerList().getPlayerByUsername(args[0]);
            }
            if(target==null){
                ITextComponent component = new TextComponentString(TextFormatting.RED + "player not found");
                sender.sendMessage(component);
            }
            dimensionid = parseInt(args[1]);
            x = parseDouble(args[2]);
            y = parseDouble(args[3]);
            z = parseDouble(args[4]);
        }
        else{
            return;
        }

        if(dimensionid == target.dimension){
            List<String> newArgs = new ArrayList<String>(){};
            newArgs.add(target.getName());
            newArgs.add(Double.toString(x));
            newArgs.add(Double.toString(y));
            newArgs.add(Double.toString(z));

            server.commandManager.executeCommand(sender, "tp " + String.join(" ", newArgs));
        }
        else{
            teleportToDimension(target, dimensionid, x,y,z);
        }
    }

    public static void teleportToDimension(EntityPlayer player, int dimension, double x, double y, double z) {
        int oldDimension = player.getEntityWorld().provider.getDimension();
        EntityPlayerMP entityPlayerMP = (EntityPlayerMP) player;
        MinecraftServer server = player.getEntityWorld().getMinecraftServer();
        WorldServer worldServer = server.getWorld(dimension);
        player.addExperienceLevel(0);


        worldServer.getMinecraftServer().getPlayerList().transferPlayerToDimension(entityPlayerMP, dimension, new RfToolsTeleporter(worldServer, x, y, z));
        player.setPositionAndUpdate(x, y, z);
        if (oldDimension == 1) {
            // For some reason teleporting out of the end does weird things.
            player.setPositionAndUpdate(x, y, z);
            worldServer.spawnEntity(player);
            worldServer.updateEntityWithOptionalForce(player, false);
        }
    }

    public String getUsage(ICommandSender sender)
    {
        return TPPlus.modId + "commands.tpp.usage";
    }

    /**
     * Get a list of options for when the user presses the TAB key
     */
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos)
    {
        return args.length != 1 ? Collections.emptyList() : getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames());
    }

    /**
     * Return whether the specified command parameter index is a username parameter.
     */
    public boolean isUsernameIndex(String[] args, int index)
    {
        return index == 0;
    }
}

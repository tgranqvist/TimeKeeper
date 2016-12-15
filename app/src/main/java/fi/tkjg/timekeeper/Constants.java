package fi.tkjg.timekeeper;

/**
 * Created by Tom Granqvist on 12-12-2016.
 */

public final class Constants {

    // Extras key for the command the background service performs
    public static final String COMMAND = "fi.tkjg.timekeeper.COMMAND";


    //** Command values **//
    // List all entries in the database
    public static final String COMMAND_LIST_ENTRIES = "fi.tkjg.timekeeper.LIST";

    // Add a new entry
    public static final String COMMAND_ADD_ENTRY = "fi.tkjg.timekeeper.ADD";


    //** Command secondary extras keys **//
    public static final String ITEMS = "fi.tkjg.timekeeper.ITEMS";
    public static final String ITEM = "fi.tkjg.timekeeper.ITEM";


    // This is the broadcast key for communicating with the service
    public static final String SERVICE_REPLY = "fi.tkjg.timekeeper.REPLY";


    // PRIVATE //
    // Don't allow instantiation of this class
    private Constants() {}
}

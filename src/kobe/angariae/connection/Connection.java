package kobe.angariae.connection;

import java.util.ArrayList;
import java.util.LinkedList;

import android.os.Parcelable;

import kobe.angariae.Track;
import kobe.angariae.exception.AnException;

public interface Connection{
    public static final String klass = "connection";
    public String getServerAddress();
    public String getUserName();
    public String getPassword();
    public String getLabel();
    public String getType();
    public void setServerAddress(String sa);
    public void setUserName(String un);
    public void setPassword(String p);
    public void setLabel(String l);
    public void connect() throws AnException;
    public void disconnect() throws AnException;
    public String download(String file) throws AnException;
    public ArrayList<Track> browse(String path) throws AnException;
    public ArrayList<Track> browse()throws AnException;
    public ArrayList<Track> browseUp()throws AnException;
}

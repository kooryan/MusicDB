package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataSource {

    public static final String DB_NAME = "music.db";

//    public static final String CONNECTION_STRING = "jdbc:sqlite:C:\\Users\\Ryan\\MusicDB\\" + DB_NAME;
    public static final String CONNECTION_STRING = "jdbc:sqlite:/Users/ryankoo/MusicDB/" + DB_NAME;

    public static final String TABLE_ALBUMS = "albums";
    public static final String COLUMN_ALBUM_ID = "_id";
    public static final String COLUMN_ALBUM_NAME = "name";
    public static final String COLUMN_ALBUM_ARTIST = "artist";
    public static final int INDEX_ALBUM_ID = 1;
    public static final int INDEX_ALBUM_NAME =  2;
    public static final int INDEX_ALBUM_ARTIST = 3;

    public static final String TABLE_ARTISTS = "artists";
    public static final String COLUMN_ARTIST_ID = "_id";
    public static final String COLUMN_ARTIST_NAME = "name";
    public static final int INDEX_ARTIST_ID = 1;
    public static final int INDEX_ARTIST_NAME = 2;

    public static final String TABLE_SONGS = "songs";
    public static final String COLUMN_SONG_ID = "_id";
    public static final String COLUMN_SONG_TRACK = "track";
    public static final String COLUMN_SONG_TITLE = "title";
    public static final String COLUMN_SONG_ALBUM = "album";
    public static final int INDEX_SONG_ID = 1;
    public static final int INDEX_SONG_TRACK = 2;
    public static final int INDEX_SONG_TITLE = 3;
    public static final int INDEX_SONG_ALBUM = 4;

    public static final int ORDER_BY_NONE = 1;
    public static final int ORDER_BY_ASC = 2; //ascending
    public static final int ORDER_BY_DESC = 3; //descending


    private Connection conn;

    public boolean open() {
        try {
            conn = DriverManager.getConnection(CONNECTION_STRING); // wouldn't need a close method if we did try with resources
            return true;
        } catch (SQLException e) {
            System.out.println("Couldn't connect to database");
            e.printStackTrace();
            return false;
        }
    }

    public void close() {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println("Couldn't close the connection");
            e.printStackTrace();
        }
    }

    public List<Artist> queryArtists(int sortOrder) { // Try with resources ensures that Statement and ResultSet are closed

       StringBuilder sb = new StringBuilder("SELECT * FROM ");
       sb.append(TABLE_ARTISTS);

       if (sortOrder != ORDER_BY_NONE) { // We could use an ENUM here too
           sb.append(" ORDER BY ");
           sb.append(COLUMN_ARTIST_NAME);
           sb.append(" COLLATE NOCASE ");
           if (sortOrder == ORDER_BY_DESC) {
               sb.append("DESC");
           } else {
               sb.append("ASC");

           }
       }


        try (Statement statement = conn.createStatement();
             ResultSet results = statement.executeQuery(sb.toString())) {


            List<Artist> artists = new ArrayList<>();

            while (results.next()) {
                Artist artist = new Artist();
                artist.set_id(results.getInt(INDEX_ARTIST_ID));
                artist.setName(results.getString(INDEX_ARTIST_NAME));
                artists.add(artist);
            }

            return artists;

        } catch (SQLException e) {
            System.out.println("Query failed" + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}

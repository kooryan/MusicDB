import model.Artist;
import model.DataSource;
import model.SongArtist;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        DataSource dataSource = new DataSource();

        if (!dataSource.open()) {
            System.out.println("Can't open data source");
            return;
        }

        List<Artist> artists = dataSource.queryArtists(DataSource.ORDER_BY_NONE);
        if (artists == null) {
            System.out.println("No artists!");
            return;
        }

        for (Artist artist : artists) {
            System.out.println("id = " + artist.get_id() + ", name = " + artist.getName());
        }

        List<String> albumsForArtist =
                dataSource.queryAlbumsForArtist("Carole King", DataSource.ORDER_BY_ASC);

        for (String album : albumsForArtist) {
            System.out.println(album);
        }

        List<SongArtist> songArtists =
                dataSource.queryArtistsForSong("Go Your Own Way", DataSource.ORDER_BY_ASC);

        if (songArtists == null) {
            System.out.println("Couldn't find artist for the song");
            return;
        }

        for (SongArtist artist : songArtists) {
            System.out.println("Artists name = " + artist.getArtistName() +
                    ", Album name = " + artist.getAlbumName() +
                    ", Track = " + artist.getTrack());
        }

        dataSource.querySongsMetadata();

        int count = dataSource.getCount(DataSource.TABLE_SONGS);
        System.out.println("Number of songs is: " + count);

        dataSource.createViewSongArtists();

//        Scanner sc = new Scanner(System.in);
//        System.out.println("Enter a song title: " );
//        String title = sc.nextLine();
//
//        songArtists = dataSource.querySongInfoView(title);
//        if (songArtists.isEmpty()) {
//            System.out.println("Couldn't find the artist for the song");
//            return;
//        }
//
//        for (SongArtist artist : songArtists) {
//            System.out.println("FROM VIEW - Artist name = " + artist.getArtistName() +
//                    ", Album name = " + artist.getAlbumName() +
//                    ", Track number = " + artist.getTrack());
//        }
//
        dataSource.insertSong("Bird Dog", "Everly Brothers", "Everly Greatest Hits", 5);

        dataSource.close();

    }
}

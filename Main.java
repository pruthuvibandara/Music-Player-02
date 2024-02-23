import java.util.Scanner;

public class Main {

    static class SongNode {
        String name;
        SongNode next;
        SongNode prev;

        SongNode(String songName) {
            this.name = songName;
            this.next = null;
            this.prev = null;
        }
    }

    static class Playlist {
        private SongNode head;
        private SongNode currentSong; // Track the current song

        Playlist() {
            this.head = null;
            this.currentSong = null;
        }

        void insertLast(String songName) {
            SongNode newNode = new SongNode(songName);

            if (head == null) {
                head = newNode;
                currentSong = head; // Set the current song if the playlist was empty
            } else {
                SongNode current = head;
                while (current.next != null) {
                    current = current.next;
                }
                current.next = newNode;
                newNode.prev = current;
            }
        }

        void insertFirst(String songName) {
            SongNode newNode = new SongNode(songName);

            if (head != null) {
                newNode.next = head;
                head.prev = newNode;
            }
            head = newNode;
            currentSong = head; // Set the current song after inserting at the beginning
        }

        boolean containsSong(String songName) {
            SongNode current = head;
            while (current != null) {
                if (current.name.equals(songName)) {
                    return true;
                }
                current = current.next;
            }
            return false;
        }

        void displaySongs() {
            SongNode current = head;
            while (current != null) {
                System.out.println(current.name);
                current = current.next;
            }
        }

        void playNextSong() {
            if (currentSong != null && currentSong.next != null) {
                currentSong = currentSong.next;
                System.out.println("Now playing next song: " + currentSong.name);
            } else {
                System.out.println("No next song available.");
            }
        }

        void playPreviousSong() {
            if (currentSong != null && currentSong.prev != null) {
                currentSong = currentSong.prev;
                System.out.println("Now playing previous song: " + currentSong.name);
            } else {
                System.out.println("No previous song available.");
            }
        }




        SongNode getHead() {
            return head;
        }

        void clear() {
            head = null;
            currentSong = null;
        }

        public SongNode getCurrentSong() {
            return currentSong;
        }

        public void setCurrentSong(SongNode currentSong) {
            this.currentSong = currentSong;
        }
    }

    static class MusicPlayer {
        private Playlist home;
        private Playlist favorites;
        private Playlist[] playlists;
        private int playlistCount;
        private Scanner scanner;

        MusicPlayer() {
            home = new Playlist();
            favorites = new Playlist();
            playlists = new Playlist[3]; // Assuming there are 3 playlists
            for (int i = 0; i < 3; i++) {
                playlists[i] = new Playlist();
            }
            playlistCount = 0;
            scanner = new Scanner(System.in); // Initialize the scanner

            // Initialize initial songs
            initializeSongs();
        }

        // Method to initialize initial songs
        private void initializeSongs() {
            home.insertLast("Song 1");
            home.insertLast("Song 2");
            home.insertLast("Song 3");
            home.insertLast("Song 4");
        }

        // Method to display all songs
        private void displayAllSongs() {
            System.out.println("All Songs:");
            System.out.println("Home Playlist:");
            home.displaySongs();
            System.out.println("Favorites Playlist:");
            favorites.displaySongs();
            for (int i = 0; i < playlistCount; i++) {
                System.out.println("Playlist " + (i + 1) + ":");
                playlists[i].displaySongs();
            }
        }

        void displayMenu() {
            System.out.println("Navigation Pages:");
            System.out.println("1. HOME" + (home.getCurrentSong() != null ? " - Now Playing: " + home.getCurrentSong().name : ""));
            System.out.println("2. Favorites");
            System.out.println("3. Playlists");
            System.out.println("4. All Songs");
            System.out.print("Enter a number to navigate: ");
        }

        void homePage() {
            int choice;
            do {
                System.out.println();
                System.out.println("Currently Playing: " + (home.getCurrentSong() != null ? home.getCurrentSong().name : "No song playing")); // Show currently playing song
                System.out.println();
                System.out.println("Home Page:");
                System.out.println("1. Add a new song");
                System.out.println("2. Listen to songs");
                System.out.println("3. Go to the previous menu");
                System.out.print("Enter a number to proceed: ");
                choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        addNewSong();
                        break;
                    case 2:
                        listenToSongs();
                        break;
                    case 3:
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a valid number.");
                }
            } while (choice != 3);
        }

        void addNewSong() {
            scanner.nextLine(); // Consume newline
            System.out.print("Enter the name of the new song: ");
            String songName = scanner.nextLine();

            home.insertLast(songName);
            System.out.println("Song added to HOME playlist.");
        }

        void listenToSongs() {
            System.out.println("Listen to Songs:");
            home.displaySongs();

            int choice;
            do {
                System.out.println("1. Play a song");
                System.out.println("2. Play next song");
                System.out.println("3. Play previous song");
                System.out.println("4. Add to Favorites");
                System.out.println("5. Add to Playlist");
                System.out.println("6. Go to the previous menu");
                System.out.print("Enter a number to proceed: ");
                choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        playSong();
                        break;
                    case 2:
                        home.playNextSong();
                        break;
                    case 3:
                        home.playPreviousSong();
                        break;
                    case 4:
                        addSongToFavorites();
                        break;
                    case 5:
                        addSongToPlaylist();
                        break;
                    case 6:
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a valid number.");
                }
            } while (choice != 6);
        }



        void addSongToPlaylist() {
            // Display currently playing song
            if (home.getCurrentSong() != null) {
                System.out.println("Currently Playing: " + home.getCurrentSong().name);
            } else {
                System.out.println("No song is currently playing.");
                return;
            }

            displayPlaylists();

            System.out.print("Enter the number of the playlist to add the song: ");
            int playlistChoice = scanner.nextInt();

            if (playlistChoice >= 1 && playlistChoice <= playlistCount) {
                if (home.getCurrentSong() != null) {
                    String currentSongName = home.getCurrentSong().name;
                    if (!playlists[playlistChoice - 1].containsSong(currentSongName)) {
                        playlists[playlistChoice - 1].insertLast(currentSongName);
                        System.out.println("Currently playing song added to Playlist " + playlistChoice + ".");
                    } else {
                        System.out.println("Song is already in the playlist.");
                    }
                } else {
                    System.out.println("No song is currently playing.");
                }
            } else {
                System.out.println("Invalid playlist choice.");
            }
        }






        void playlistPage() {
            System.out.println("Playlists Page:");
            System.out.println("1. Create a new playlist");
            System.out.println("2. Display all playlists");
            System.out.println("3. View songs in a playlist");
            System.out.println("4. Go to the previous menu");
            System.out.print("Enter a number to proceed: ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    createNewPlaylist();
                    playlistPage(); // Go back to the playlist page after creating a new playlist
                    break;
                case 2:
                    displayPlaylists();
                    playlistPage(); // Go back to the playlist page after displaying all playlists
                    break;
                case 3:
                    viewPlaylistSongs();
                    playlistPage(); // Go back to the playlist page after viewing songs in a playlist
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a valid number.");
                    break;
            }
        }



        void createNewPlaylist() {
            if (playlistCount < 3) { // Assuming there is a limit of 3 playlists
                playlists[playlistCount].insertLast("Playlist " + (playlistCount + 1));
                System.out.println("Playlist created: Playlist " + (playlistCount + 1));
                playlistCount++;
            } else {
                System.out.println("Maximum number of playlists reached (3).");
            }
        }
    }

    public static void main(String[] args) {
        MusicPlayer player = new MusicPlayer();

        int choice;
        do {
            player.displayMenu();
            choice = player.scanner.nextInt();

            switch (choice) {
                case 1:
                    player.homePage();
                    break;
                case 2:
                    player.displayFavorites();
                    break;
                case 3:
                    player.playlistPage();
                    break;
                case 4:
                    player.displayAllSongs();
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid number.");
            }
        } while (choice >= 1 && choice <= 3);
    }
}

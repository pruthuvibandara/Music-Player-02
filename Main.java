import java.util.*;

public class Main {

    static class SongNode {
        String name;
        SongNode next;
        SongNode prev;
        List<Edge> edges;

        SongNode(String songName) {
            this.name = songName;
            this.next = null;
            this.prev = null;
        }
        public void addEdge(SongNode destination, int weight)
        {
            edges.add(new Edge(this, destination, weight));
        }

        public String toString()
        {
            return name; // Return the name of the song as the string representation
        }
    }

    static class Edge
    {
        SongNode source;
        SongNode destination;
        int weight;
        public Edge(SongNode source, SongNode destination, int weight)
        {
            this.source = source;
            this.destination = destination;
            this.weight = weight;
        }
    }
    static class Playlist {
        private SongNode head;
        private SongNode currentSong; // Track the current song
        hashmap map1;

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
//                map1.playSong2(currentSong.name);
                System.out.println('\n'+"Now playing next song: " + currentSong.name);
            } else {
                System.out.println('\n'+"No next song available.");
            }
        }

        void playPreviousSong() {
            if (currentSong != null && currentSong.prev != null) {
                currentSong = currentSong.prev;
//                map1.playSong2(currentSong.name);
                System.out.println('\n'+"Now playing previous song: " + currentSong.name);
            } else {
                System.out.println('\n'+"No previous song available.");
            }
        }

        SongNode getSongByName(String songName) {
            SongNode current = head;
            while (current != null) {
                if (current.name.equals(songName)) {
                    return current;
                }
                current = current.next;
            }
            return null; // Song not found
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

        void mergeSort(ArrayList<SongNode> sort)
        {
            if(sort.size()<= 1)
            {
                return;
            }

            int mid = sort.size() / 2;
            ArrayList<SongNode> left = new ArrayList<>(sort.subList(0, mid));
            ArrayList<SongNode> right = new ArrayList<>(sort.subList(mid, sort.size()));

            mergeSort(left);
            mergeSort(right);

            merge(sort, left, right);
        }
        void merge(ArrayList<SongNode> sort, ArrayList<SongNode> left, ArrayList<SongNode> right)
        {
            int leftIndex = 0;
            int rightIndex = 0;
            int sortIndex = 0;

            while(leftIndex < left.size() && rightIndex < right.size())
            {
                if (left.get(leftIndex).name.compareTo(right.get(rightIndex).name) < 0)
                {
                    sort.set(sortIndex++, left.get(leftIndex++));
                }
                else
                {
                    sort.set(sortIndex++, right.get(rightIndex++));
                }
            }

            while (leftIndex < left.size())
            {
                sort.set(sortIndex++, left.get(leftIndex++));
            }

            while (rightIndex < right.size())
            {
                sort.set(sortIndex++, right.get(rightIndex++));
            }
        }
    }

    static class MusicPlayer {
        private Playlist home;
        private Playlist favorites;
        private Playlist[] playlists;
        private int playlistCount;
        private Scanner scanner;
        hashmap map1;

        MusicPlayer() {
            home = new Playlist();
            favorites = new Playlist();
            playlists = new Playlist[3]; // Assuming there are 3 playlists
            for (int i = 0; i < 3; i++) {
                playlists[i] = new Playlist();
            }
            playlistCount = 0;
            scanner = new Scanner(System.in); // Initialize the scanner

            map1 = new hashmap();

            // Initialize initial songs
            initializeSongs();
        }

        // Method to initialize initial songs
        private void initializeSongs() {
            home.insertLast("Song A");
            home.insertLast("Song E");
            home.insertLast("Song C");
            home.insertLast("Song B");
            home.insertLast("Song T");
        }

        void mostplayed()
        {
            //get 5 most played songs
            map1.listMostPlayedSongs(3);
        }

        // Method to display all songs
        private void displayAllSongs() {
            System.out.println(">>All Songs<<");
            System.out.println('\n'+"Home Playlist:");
            home.displaySongs();
            System.out.println('\n'+"Favorites Playlist:");
            favorites.displaySongs();
            for (int i = 0; i < playlistCount; i++) {
                System.out.println('\n'+"Playlist " + (i + 1) + ":");
                playlists[i].displaySongs();
            }
        }

        void displayMenu() {
            System.out.println('\n'+"Navigation Pages:");
            System.out.println('\n'+"1. HOME" + (home.getCurrentSong() != null ? " - Now Playing: " + home.getCurrentSong().name : ""));
            System.out.println("2. Favorites");
            System.out.println("3. Playlists");
            System.out.println("4. All Songs");
            System.out.println("5. Most Played Songs");
            System.out.print('\n'+"Enter a number to navigate: ");
        }

        void homePage() {
            int choice;
            do {
                System.out.println();
                System.out.println('\n'+"Currently Playing: " + (home.getCurrentSong() != null ? home.getCurrentSong().name : "No song playing")); // Show currently playing song
                System.out.println();
                System.out.println('\n'+"<<Home Page>>");
                System.out.println('\n'+"1. Add a new song");
                System.out.println("2. Listen to songs");
                System.out.println("3. Go to the previous menu");
                System.out.print('\n'+"Enter a number to proceed: ");
                choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        addNewSong();
                        break;
                    case 2:
                        listenToSongs();
                        break;
                    case 3:
                        displayMenu();
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a valid number.");
                }
            } while (choice != 3);
        }

        void addNewSong() {
            try
            {
                scanner.nextLine(); // Consume newline
                System.out.print('\n'+"Enter the name of the new song: ");
                String songName = scanner.nextLine();

                home.insertLast(songName);
                System.out.println('\n'+"Song added to HOME playlist.");
            }
           catch(Exception ex)
           {
               System.out.println("enter a song name");
           }
        }

        void removeSongLast(String removeSong)
        {
            if (home.containsSong(removeSong))
            {
                SongNode current = home.getHead();
                while (current != null) {
                    if (current.name.equals(removeSong)) {
                        // Adjust the previous and next pointers
                        if (current.prev != null) {
                            current.prev.next = current.next;
                        }
                        if (current.next != null) {
                            current.next.prev = current.prev;
                        }
                        // Handle head removal
                        if (current == favorites.getHead()) {
                            home.head = current.next;
                        }
                        // Handle tail removal
                        if (current.next == null) {
                            home.head = current.prev;
                        }
                        System.out.println('\n' + "This song has removed from all songs: " + removeSong);
                        return;
                    }
                    current = current.next;
                }
            }
            else
            {
                System.out.println('\n'+"This song is not in the all songs.");
            }
        }
        void listenToSongs() {
            System.out.println('\n'+"Listen to Songs:");
            home.displaySongs();
            Scanner sc2=new Scanner(System.in);
            int choice;
            do {
                System.out.println('\n'+"1. Play a song");
                System.out.println("2. Play next song");
                System.out.println("3. Play previous song");
                System.out.println("4. Add to Favorites");
                System.out.println("5. Add to Playlist");
                System.out.println("6. Remove a song");
                System.out.println("7. Go to the previous menu");
                System.out.print('\n'+"Enter a number to proceed: ");
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
                        System.out.println('\n'+"Enter the name of the song to be removed: ");
                        String removeSong = sc2.nextLine();
                        removeSongLast(removeSong);
                        break;
                    case 7:
                        homePage();
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a valid number.");
                }
            } while (choice != 7);
        }

        void addSongToFavorites() {
            if (home.getCurrentSong() != null) {
                String currentSongName = home.getCurrentSong().name;
                if (!favorites.containsSong(currentSongName)) {
                    favorites.insertFirst(currentSongName);
                    System.out.println('\n'+"Currently playing song added to Favorites playlist.");
                } else {
                    System.out.println('\n'+"Song is already favorite.");
                }
            } else {
                System.out.println('\n'+"No song is currently playing.");
            }
        }

        void displayPlaylists() {
            System.out.println('\n'+"Available Playlists:");
            for (int i = 0; i < playlistCount; ++i) {
                System.out.println((i + 1) + ". Playlist " + (i + 1));
            }
        }

        void playSong() {
            scanner.nextLine(); // Consume newline
            System.out.print('\n'+"Enter the name of the song to play: ");
            String songName = scanner.nextLine();

            if (home.containsSong(songName)) {
                map1.playSong2(songName);
                System.out.println('\n'+"Now playing: " + songName);

                home.setCurrentSong(home.getSongByName(songName)); // Set the currently playing song
            } else {
                System.out.println('\n'+"Song is not in the HOME playlist.");
            }
        }


        void displayFavorites() {
            int choice;
            do {
                System.out.println('\n'+"<<Favorites Page>>");
                System.out.println("1. Display all favorite songs and additional options:");
                System.out.println("2. Go to the previous menu");
                System.out.print('\n'+"Enter a number to proceed: ");
                choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        do {
                            System.out.println('\n'+"Now Playing: " + (favorites.getCurrentSong() != null ? favorites.getCurrentSong().name : "No song playing"));
                            System.out.println('\n'+"<<All favorite songs>>");
                            favorites.displaySongs();
                            System.out.println('\n'+"<<Additional options>>");
                            System.out.println("1. Play a song from Favorites");
                            System.out.println("2. Play next song from Favorites");
                            System.out.println("3. Play previous song from Favorites");
                            System.out.println("4. Remove a song from Favorites");
                            System.out.println("5. Go back to the main options");
                            System.out.print('\n'+"Enter a number to proceed: ");
                            int subChoice = scanner.nextInt();
                            switch (subChoice) {
                                case 1:
                                    playSongFromFavorites();
                                    break;
                                case 2:
                                    favorites.playNextSong();
                                    break;
                                case 3:
                                    favorites.playPreviousSong();
                                    break;
                                case 4:
                                    removeSongFromFavorites();
                                    break;
                                case 5:
                                    displayFavorites();
                                    break; // Go back to the main options
                                default:
                                    System.out.println("Invalid choice. Please enter a valid number.");
                                    break;
                            }
                        } while (choice != 5); // Stay in this section until the user chooses to go back
                        break;
                    case 2:
                        displayMenu();
                        return;
                    default:
                        System.out.println("Invalid choice. Please enter a valid number.");
                        break;
                }
            } while (choice != 2);
        }



        void playSongFromFavorites() {
            scanner.nextLine(); // Consume newline
            System.out.print('\n'+"Enter the name of the song to play from Favorites: ");
            String songName = scanner.nextLine();

            if (favorites.containsSong(songName)) {
                System.out.println('\n'+"Now playing from Favorites: " + songName);
            } else {
                System.out.println('\n'+"Song is not in the Favorites playlist.");
            }
        }

        void removeSongFromFavorites() {
            if (favorites.getCurrentSong() != null) {
                String currentSongName = favorites.getCurrentSong().name;
                if (favorites.containsSong(currentSongName)) {
                    SongNode current = favorites.getHead();
                    while (current != null) {
                        if (current.name.equals(currentSongName)) {
                            // Adjust the previous and next pointers
                            if (current.prev != null) {
                                current.prev.next = current.next;
                            }
                            if (current.next != null) {
                                current.next.prev = current.prev;
                            }
                            // Handle head removal
                            if (current == favorites.getHead()) {
                                favorites.head = current.next;
                            }
                            // Handle tail removal
                            if (current.next == null) {
                                favorites.head = current.prev;
                            }
                            System.out.println('\n'+"Currently playing song removed from Favorites: " + currentSongName);
                            return;
                        }
                        current = current.next;
                    }
                } else {
                    System.out.println('\n'+"Currently playing song is not in the Favorites playlist.");
                }
            } else {
                System.out.println('\n'+"No song is currently playing.");
            }
        }

        void addSongToPlaylist() {
            // Display currently playing song
            if (home.getCurrentSong() != null) {
                System.out.println('\n'+"Currently Playing: " + home.getCurrentSong().name);
            } else {
                System.out.println('\n'+"No song is currently playing.");
                return;
            }

            displayPlaylists();

            System.out.print('\n'+"Enter the number of the playlist to add the song: ");
            int playlistChoice = scanner.nextInt();

            if (playlistChoice >= 1 && playlistChoice <= playlistCount) {
                if (home.getCurrentSong() != null) {
                    String currentSongName = home.getCurrentSong().name;
                    if (!playlists[playlistChoice - 1].containsSong(currentSongName)) {
                        playlists[playlistChoice - 1].insertLast(currentSongName);
                        System.out.println('\n'+"Currently playing song added to Playlist " + playlistChoice + ".");
                    } else {
                        System.out.println('\n'+"Song is already in the playlist.");
                    }
                } else {
                    System.out.println('\n'+"No song is currently playing.");
                }
            } else {
                System.out.println('\n'+"Invalid playlist choice.");
            }
        }

//        void viewPlaylistSongs() {
//            displayPlaylists();
//
//            System.out.print("Enter the number of the playlist to view songs: ");
//            int playlistChoice = scanner.nextInt();
//
//            if (playlistChoice >= 1 && playlistChoice <= playlistCount) {
//                System.out.println("Songs in Playlist " + playlistChoice + ":");
//                playlists[playlistChoice - 1].displaySongs();
//            } else {
//                System.out.println("Invalid playlist choice.");
//            }
//        }




        void playlistPage() {
            System.out.println('\n'+"<<Playlists Page>>");
            System.out.println("1. Create a new playlist");
            System.out.println("2. Display all playlists");
            System.out.println("3. View songs in a playlist");
            System.out.println("4. Go to the previous menu");
            System.out.print('\n'+"Enter a number to proceed: ");
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

        void viewPlaylistSongs() {
            displayPlaylists();

            System.out.print('\n'+"Enter the number of the playlist to view songs: ");
            int playlistChoice = scanner.nextInt();

            if (playlistChoice >= 1 && playlistChoice <= playlistCount) {
                Playlist selectedPlaylist = playlists[playlistChoice - 1];
                int choice;
                do {
                    System.out.println("<<Songs in Playlist>> " + playlistChoice + ":");
                    selectedPlaylist.displaySongs();
                    System.out.println('\n'+"Currently Playing: " + (selectedPlaylist.getCurrentSong() != null ? selectedPlaylist.getCurrentSong().name : "No song playing"));
                    System.out.println("<<Additional options>>");
                    System.out.println("1. Play a song from Playlist");
                    System.out.println("2. Play next song from Playlist");
                    System.out.println("3. Play previous song from Playlist");
                    System.out.println("4. Remove a song from Playlist");
                    System.out.println("5. Go back to the main options");
                    System.out.print('\n'+"Enter a number to proceed: ");
                    choice = scanner.nextInt();

                    switch (choice) {
                        case 1:
                            playSongFromPlaylist(selectedPlaylist);
                            break;
                        case 2:
                            selectedPlaylist.playNextSong();
                            break;
                        case 3:
                            selectedPlaylist.playPreviousSong();
                            break;
                        case 4:
                            removeCurrentSongFromPlaylist(selectedPlaylist);
                            break;
                        case 5:
                            return; // Go back to the main options
                        default:
                            System.out.println("Invalid choice. Please enter a valid number.");
                            break;
                    }
                } while (choice != 5);
            } else {
                System.out.println("Invalid playlist choice.");
            }
        }

        void playSongFromPlaylist(Playlist playlist) {
            scanner.nextLine(); // Consume newline
            System.out.print('\n'+"Enter the name of the song to play from the playlist: ");
            String songName = scanner.nextLine();

            if (playlist.containsSong(songName)) {
                System.out.println('\n'+"Now playing from Playlist: " + songName);
                map1.playSong2(songName);
                playlist.setCurrentSong(playlist.getSongByName(songName)); // Set the currently playing song
            } else {
                System.out.println('\n'+"Song is not in the playlist.");
            }
        }

        void removeCurrentSongFromPlaylist(Playlist playlist) {
            SongNode currentSong = playlist.getCurrentSong();
            if (currentSong != null) {
                if (playlist.getHead() == currentSong) {
                    // If the current song is the head of the playlist
                    playlist.head = currentSong.next;
                    if (playlist.head != null) {
                        playlist.head.prev = null;
                    }
                } else {
                    // Adjust the previous and next pointers of the adjacent nodes
                    currentSong.prev.next = currentSong.next;
                    if (currentSong.next != null) {
                        currentSong.next.prev = currentSong.prev;
                    }
                }
                // Set the next song as the current song
                if (currentSong.next != null) {
                    playlist.setCurrentSong(currentSong.next);
                    System.out.println('\n'+"Now playing: " + currentSong.next.name);
                } else {
                    // If there's no next song, set the current song to null
                    playlist.setCurrentSong(null);
                    System.out.println('\n'+"No song is currently playing from this playlist.");
                }
                System.out.println("Currently playing song removed from Playlist.");
            } else {
                System.out.println("No song is currently playing from this playlist.");
            }
        }

        List<SongNode> shortestPath(List<SongNode> playlist,SongNode source,SongNode destination)
        {
            Map<SongNode, Integer> distance = new HashMap<>();
            Map<SongNode, SongNode> predecessor = new HashMap<>();
            PriorityQueue<SongNode> pq=new PriorityQueue<>(Comparator.comparingInt(distance::get));

            /*ArrayList<SongNode> plays=new ArrayList<>();
            SongNode current = home.head;
            while(current!=null)
            {
                plays.add(current);
                current=current.next;
            }*/

            for(SongNode songNode: playlist)
            {
                if(songNode.equals(source))
                {
                    distance.put(songNode,0);
                }
                else
                {
                    distance.put(songNode,Integer.MAX_VALUE);
                }
                pq.add(songNode);
            }
            while(!pq.isEmpty())
            {
                SongNode current1=pq.poll();
                for(Edge edge: current1.edges)
                {
                    int altdist= distance.get(current1) + edge.weight;
                    if(altdist < distance.get(edge.destination))
                    {
                        distance.put(edge.destination, altdist);
                        predecessor.put(edge.destination, current1);
                        pq.remove(edge.destination);
                        pq.add(edge.destination);
                    }
                }
            }
            List<SongNode> path=new ArrayList<>();
            SongNode current2=destination;
            while(current2!=null)
            {
                path.add(current2);
                current2=predecessor.get(current2);
            }
            Collections.reverse(path);
            return path;
        }

    }


    public static void main(String[] args) {
        MusicPlayer player = new MusicPlayer();
        Scanner sc=new Scanner(System.in);
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
                    System.out.println('\n'+"1.Sort songs in A-Z: ");
                    System.out.println("2.Go back to Menu: ");
                    int back=sc.nextInt();
                    switch(back)
                    {
                        case 1:
                            if(player.home.head != null)
                            {
                                ArrayList<SongNode> sort=new ArrayList<>();
                                SongNode current = player.home.head;
                                while(current!=null)
                                {
                                    sort.add(current);
                                    current=current.next;
                                }

                                player.home.mergeSort(sort);
                                System.out.println("\nAfter sorting:");
                                for (SongNode song : sort)
                                {
                                    System.out.println(song.name);
                                }
                            }
                            else
                            {
                                System.out.println("Playlist is empty.");
                            }
                            break;
                        case 2:
                            player.displayMenu();
                            break;
                        default:
                            System.out.println("Invalid number");
                    }
                    break;
                case 5:
                    player.mostplayed();
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid number.");
            }
        } while (choice >= 1 && choice <= 5);
    }
}

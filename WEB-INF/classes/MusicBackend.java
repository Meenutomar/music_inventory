import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;


public class MusicBackend {
    private Connection connection;
    private MusicDTOTransformer transformer;

    public MusicBackend() {
        this.transformer = new MusicDTOTransformer();
        String url = "jdbc:oracle:thin:@localhost:1521:xe";
        String user = "bpm";
        String password = "bpm";
        try {
             // Register JDBC driver
        Class.forName("oracle.jdbc.driver.OracleDriver"); // Type-4 Driver

        // Open a connection
        connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e);
        }
       
    }

    public int registerUser(Music music) {
        // JDBC URL, username, and password of MySQL server

        int numberOfRecordsUpdated = 0;
        try {

            // Insert record in table
            String sql = "insert into  Music_Invertory( Name,Quenty,EntryDate) VALUES(?,?,?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            Date date = Date.valueOf(music.getDateString());
            statement.setString(1, music.getName());
            statement.setInt(2, music.getQuantity());
            statement.setDate(3, date);
            numberOfRecordsUpdated = statement.executeUpdate();

            // Close resources
            statement.close();
          //  connection.close();

        } catch (SQLException e) {
            throw new MusicException(e);
        }
        return numberOfRecordsUpdated;

    }

    public List<Music> showInventory() {
        // JDBC URL, username, and password of MySQL server
        List<MusicDTO> list = new ArrayList<MusicDTO>();
        List<Music> musicList = new ArrayList<>();
        try {
            
            // Insert record in table
            String sql = "SELECT Name, Quenty, EntryDate FROM Music_Invertory";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                MusicDTO music = new MusicDTO();
                music.setName(resultSet.getString("Name"));
                music.setQuantity(resultSet.getInt("Quenty"));
                music.setDateString(String.valueOf(resultSet.getDate("EntryDate")));
                list.add(music);
            }
            musicList = transformer.convert(list);
            System.out.println("MusicList::" + musicList);
            // Close resources
            resultSet.close();
            statement.close();
           // connection.close();

        } catch (SQLException e) {
            throw new MusicException(e);
        }
        return musicList;

    }

}

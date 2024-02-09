import java.util.ArrayList;
import java.util.List;

public class MusicDTOTransformer {

    public List<Music> convert(List<MusicDTO> musicDtoList) {
        List<Music> musicList = new ArrayList<>();
        for (MusicDTO musicDto : musicDtoList) {
            Music music = new Music();
            music.setName(musicDto.getName());
            music.setDateString(musicDto.getDateString());
            music.setQuantity(musicDto.getQuantity());
            musicList.add(music);
        }
        return musicList;
    }
    
}

package Suara.Suara.DTO;

import lombok.*;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class AlbumDTO {
    private String name;
    private List<Long> songIds;
}

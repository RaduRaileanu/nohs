package ro.bynaus.nohs.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CheckPostInfo {
    
    private PostDTO postDTO;
    private Double cost;
}

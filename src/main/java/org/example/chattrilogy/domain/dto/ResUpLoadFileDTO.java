package org.example.chattrilogy.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResUpLoadFileDTO {
    private String pathToFile;
    private String timestamp;
}

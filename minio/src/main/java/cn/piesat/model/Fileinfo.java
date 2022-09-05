package cn.piesat.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhouxp
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Fileinfo {
    private String filename;
    private Boolean directory;
    private Long fileSize;
}

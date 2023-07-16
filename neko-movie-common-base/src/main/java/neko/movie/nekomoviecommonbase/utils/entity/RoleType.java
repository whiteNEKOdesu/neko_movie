package neko.movie.nekomoviecommonbase.utils.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class RoleType {
    public static final String ROOT = "root";

    public static final String ADMIN = "admin";
}

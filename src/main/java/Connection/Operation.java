package Connection;

import java.io.Serializable;

public enum Operation implements Serializable {
    CREATE_SESSION,

    JOIN_SESSION,

    JOIN_SESSION_SUCCESS,

    JOIN_SESSION_FAILED,

    SHOW_ROOMS,

    INVITE,

    OCCUPY,

    ATTACK,

    FORTIFY,

    RED,

    BLUE,

    ORANGE,

    PINK,

    YELLOW,

    GRAY

}

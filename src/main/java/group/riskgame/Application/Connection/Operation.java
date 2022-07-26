package group.riskgame.Application.Connection;

import java.io.Serializable;

public enum Operation implements Serializable {
    CREATE_SESSION,

    JOIN_SESSION,

    JOIN_SESSION_SUCCESS,

    JOIN_SESSION_FAILED,

    SHOW_ROOMS,

    UPDATE,

    NEXT_PHASE,

    OCCUPY,

    ATTACK,

    FORTIFY,

    REINFORCE,

    EXCHANGE,

    END,

    RESET_WAIT_NEXT,

    RED,

    BLUE,

    ORANGE,

    PINK,

    YELLOW,

    GRAY

}

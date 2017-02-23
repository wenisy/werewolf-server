package com.werewolf.models;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.concurrent.TimeUnit;

public class GameState {
    private GameSnapshot gameSnapshot;

    public enum stateDefinition {
        NIL(""),
        INIT("游戏创建成功"),
        NIGHT_START("天黑了,请大家闭眼"),
        WOLF_APPEAR("狼人请睁眼"),
        WOLF_KILL("狼人请杀人,给我一个座位号"),
        WOLF_UNIFY_OPINION("狼人请统一意见"),
        WOLF_VANISH("狼人请闭眼"),
        WITCH_APPEAR("女巫请睁眼"),
        WITCH_VANISH("女巫请闭眼"),
        PROPHET_APPEAR("预言家请睁眼"),
        PROPHET_VANISH("预言家请闭眼"),
        HUNTER_APPEAR("猎人请睁眼"),
        HUNTER_VANISH("猎人请闭眼"),
        DAY_START("天亮了,请大家睁眼"),
        APPLY_SHERIFF("请大家考虑,是否要上警"),
        VOTE_FOR_APPLY_SHERIFF("要上警的请投票"),
        SHERIFF_CANDIDATE_RESULT("上警的人有"),
        SHERIFF_CANDIDATE_SPEECH("请上警的人从小号开始发言"),
        VOTE_FOR_SHERIFF("请大家选举警长"),
        SHERIFF_RESULT("警长是"),
        NIGHT_RESULT("昨天晚上死的人是"),
        DAY_SPEECH("请警长组织发言"),
        VOTE_FOR_DAY_DEATH("请大家投票,选出要投死的人"),
        DAY_RESULT("被投死的是"),
        DEATH_SPEECH("请留遗言");

        private String message;

        stateDefinition(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }

    private stateDefinition currentState;

    GameState(GameSnapshot gameSnapshot) {
        this.gameSnapshot = gameSnapshot;
        currentState = stateDefinition.NIL;
    }

    public void setGameSnapshot(GameSnapshot gameSnapshot) {
        this.gameSnapshot = gameSnapshot;
    }

    public void initState() {
        currentState = stateDefinition.INIT;
    }

    public stateDefinition getCurrentState() {
        return currentState;
    }

    public String getStateMessage(stateDefinition currentState) {
        return currentState.getMessage();
    }

    public void transfer(GameSnapshot incomingSnapshot) {
        stateDefinition nextState = stateDefinition.NIL;
        switch (currentState) {
            case INIT: {
                if(incomingSnapshot.playersAreReady()) {
                    nextState = stateDefinition.NIGHT_START;
                }
                break;
            }
            case NIGHT_START: {
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException ignored) {}
                nextState = stateDefinition.WOLF_APPEAR;
                break;
            }
            case WOLF_APPEAR: {
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException ignored) {}
                nextState = stateDefinition.WOLF_KILL;
                break;
            }
            case WOLF_KILL: {
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException ignored) {}

                if (incomingSnapshot.wolfAgreed()) {
                    nextState = stateDefinition.WOLF_VANISH;
                } else {
                    nextState = stateDefinition.WOLF_UNIFY_OPINION;
                }
                break;
            }
            case WOLF_UNIFY_OPINION: {
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException ignored) {}

                if (incomingSnapshot.wolfAgreed()) {
                    nextState = stateDefinition.WOLF_VANISH;
                } else {
                    nextState = stateDefinition.WOLF_UNIFY_OPINION;
                }
                break;
            }
            case WOLF_VANISH: {
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException ignored) {}
                nextState = stateDefinition.PROPHET_APPEAR;
                break;
            }

            case PROPHET_APPEAR: {
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException ignored) {}
                nextState = stateDefinition.PROPHET_VANISH;
                break;
            }

            case PROPHET_VANISH: {
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException ignored) {
                }
                nextState = stateDefinition.WITCH_APPEAR;
                break;
            }

            case WITCH_APPEAR: {
                try {
                    TimeUnit.SECONDS.sleep(20);
                } catch (InterruptedException ignored) {
                }
                nextState = stateDefinition.WITCH_VANISH;
                break;
            }

            case WITCH_VANISH: {
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException ignored) {
                }
                nextState = stateDefinition.HUNTER_APPEAR;
                break;
            }

            case HUNTER_APPEAR: {
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException ignored) {
                }
                nextState = stateDefinition.HUNTER_VANISH;
                break;
            }

            case HUNTER_VANISH: {
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException ignored) {
                }
                nextState = stateDefinition.DAY_START;
                break;
            }

            default: break;
        }
        currentState = nextState;
    }
}

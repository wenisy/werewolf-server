package com.werewolf.models;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class GameState {
    private GameSnapshot currentSnapshot;

    public GameSnapshot getCurrentSnapshot() {
        return currentSnapshot;
    }

    public enum StateDefinition {
        INIT("游戏创建成功"),
        WAITING_PLAYERS("等待更多玩家加入"),
        NIGHT_START("天黑了,大家请闭眼"),
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
        DAY_START("天亮了,大家请睁眼"),
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

        StateDefinition(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String newMessage) {
            message = newMessage;
        }
    }

    private StateDefinition currentState;

    GameState(GameSnapshot currentSnapshot) {
        this.currentSnapshot = currentSnapshot;
    }

    GameState(GameState origin) {
        this.currentSnapshot = origin.getCurrentSnapshot();
        this.currentState = origin.getCurrentState();
    }

    GameState(StateDefinition stateDefinition, GameSnapshot currentSnapshot) {
        this.currentState = stateDefinition;
        this.currentSnapshot = currentSnapshot;
    }

    public void initState() {
        currentState = StateDefinition.INIT;
    }

    public StateDefinition getCurrentState() {
        return currentState;
    }

    public String getStateMessage() {
        return this.currentState.getMessage();
    }

    public void setCurrentSnapshot(GameSnapshot currentSnapshot) {
        this.currentSnapshot = currentSnapshot;
    }

    public GameState transfer(GameSnapshot incomingSnapshot) {
        StateDefinition nextState = currentState;
        switch (currentState) {
            case INIT: {
                if(incomingSnapshot.playersAreReady() && incomingSnapshot.getNumberOfEmptySeat() == 0) {
                    nextState = StateDefinition.NIGHT_START;
                }
                break;
            }
            case NIGHT_START: {
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException ignored) {}
                nextState = StateDefinition.WOLF_APPEAR;
                break;
            }
            case WOLF_APPEAR: {
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException ignored) {}
                nextState = StateDefinition.WOLF_KILL;
                break;
            }
            case WOLF_KILL: {
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException ignored) {}

                if (incomingSnapshot.wolfAgreed()) {
                    nextState = StateDefinition.WOLF_VANISH;
                } else {
                    nextState = StateDefinition.WOLF_UNIFY_OPINION;
                }
                break;
            }
            case WOLF_UNIFY_OPINION: {
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException ignored) {}

                if (incomingSnapshot.wolfAgreed()) {
                    nextState = StateDefinition.WOLF_VANISH;
                } else {
                    nextState = StateDefinition.WOLF_UNIFY_OPINION;
                }
                break;
            }
            case WOLF_VANISH: {
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException ignored) {}
                nextState = StateDefinition.PROPHET_APPEAR;
                break;
            }

            case PROPHET_APPEAR: {
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException ignored) {}
                nextState = StateDefinition.PROPHET_VANISH;
                break;
            }

            case PROPHET_VANISH: {
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException ignored) {
                }
                nextState = StateDefinition.WITCH_APPEAR;
                break;
            }

            case WITCH_APPEAR: {
                try {
                    TimeUnit.SECONDS.sleep(20);
                } catch (InterruptedException ignored) {
                }
                nextState = StateDefinition.WITCH_VANISH;
                break;
            }

            case WITCH_VANISH: {
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException ignored) {
                }
                nextState = StateDefinition.HUNTER_APPEAR;
                break;
            }

            case HUNTER_APPEAR: {
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException ignored) {
                }
                nextState = StateDefinition.HUNTER_VANISH;
                break;
            }

            case HUNTER_VANISH: {
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException ignored) {
                }

                if(incomingSnapshot.needApplySheriff()) {
                    nextState = StateDefinition.DAY_START;
                }
                else {
                    nextState = StateDefinition.NIGHT_RESULT;
                }
                break;
            }

            case DAY_START: {
                nextState = StateDefinition.APPLY_SHERIFF;
                break;
            }

            case APPLY_SHERIFF: {
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException ignored) {
                }
                nextState = StateDefinition.VOTE_FOR_APPLY_SHERIFF;
                break;
            }

            case VOTE_FOR_APPLY_SHERIFF: {
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException ignored) {
                }

                nextState = StateDefinition.SHERIFF_CANDIDATE_RESULT;
                ArrayList<Integer> campaignPlayers = incomingSnapshot.getApplySheriffID();
                String resultMessage = "";
                for(int campaignID : campaignPlayers) {
                    resultMessage += String.valueOf(campaignID);
                    resultMessage += "号玩家,";
                }
                nextState.setMessage(resultMessage);
            }

            case SHERIFF_CANDIDATE_RESULT: {
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException ignored) {
                }
                nextState = StateDefinition.SHERIFF_CANDIDATE_SPEECH;
                break;
            }

            case SHERIFF_CANDIDATE_SPEECH: {
                try {
                    int totalAlivePlayerCount = incomingSnapshot.getAlivePlayerCount();
                    //everyone's speech time is 60s
                    TimeUnit.SECONDS.sleep(totalAlivePlayerCount *  60);
                } catch (InterruptedException ignored) {
                }
                nextState = StateDefinition.VOTE_FOR_SHERIFF;
                break;
            }

            case VOTE_FOR_SHERIFF: {
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException ignored) {
                }
                nextState = StateDefinition.SHERIFF_RESULT;
                nextState.setMessage(nextState.getMessage() + String.valueOf(incomingSnapshot.getSheriffID()) + "号玩家");
                break;
            }

            case SHERIFF_RESULT: {
                nextState = StateDefinition.NIGHT_RESULT;
                ArrayList<Integer> oldDeadPlayers = currentSnapshot.getDeadPlayer();
                ArrayList<Integer> newDeadPlayers = incomingSnapshot.getDeadPlayer();
                newDeadPlayers.removeAll(oldDeadPlayers);
                String resultMessage = "";

                for(int newDaadPlayerID : newDeadPlayers) {
                    resultMessage += String.valueOf(newDaadPlayerID);
                    resultMessage += "号玩家,";
                }
                nextState.setMessage(resultMessage);
                break;
            }

            case NIGHT_RESULT: {
                nextState = StateDefinition.DAY_SPEECH;
                break;
            }

            case DAY_SPEECH: {
                try {
                    int totalAlivePlayerCount = incomingSnapshot.getAlivePlayerCount();
                    //everyone's speech time is 60s
                    TimeUnit.SECONDS.sleep(totalAlivePlayerCount *  60);
                } catch (InterruptedException ignored) {
                }
                nextState = StateDefinition.VOTE_FOR_DAY_DEATH;
                break;
            }

            case VOTE_FOR_DAY_DEATH: {
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException ignored) {
                }
                nextState = StateDefinition.DAY_RESULT;

                ArrayList<Integer> oldDeadPlayers = currentSnapshot.getDeadPlayer();
                ArrayList<Integer> newDeadPlayers = incomingSnapshot.getDeadPlayer();
                newDeadPlayers.removeAll(oldDeadPlayers);
                String resultMessage = "";

                for(int newDaadPlayerID : newDeadPlayers) {
                    resultMessage += String.valueOf(newDaadPlayerID);
                    resultMessage += "号玩家,";
                }
                nextState.setMessage(resultMessage);
                break;
            }

            case DAY_RESULT: {
                nextState = StateDefinition.DEATH_SPEECH;
                break;
            }

            case DEATH_SPEECH: {
                try {
                    TimeUnit.SECONDS.sleep(30);
                } catch (InterruptedException ignored) {
                }
                nextState = StateDefinition.NIGHT_START;
                break;
            }

            default: break;
        }
        currentState = nextState;
        return new GameState(nextState, incomingSnapshot);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GameState gameState = (GameState) o;

        return currentState == gameState.currentState;

    }

    @Override
    public int hashCode() {
        return currentState != null ? currentState.hashCode() : 0;
    }
}

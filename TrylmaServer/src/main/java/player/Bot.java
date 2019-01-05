package player;

import gamemanager.GameManager;
import gamemanager.MoveManager;
import serializable.Field;

import java.util.Random;

/**
 * Bot is an abstract class, design to perform moves as player using AI. Algorithm calculates special points of bots pawns actual position and then finds best move, that will grant bot the most points
 * during this round. Each field on board has its points. Of course the ones in opposite home space value the most, so bot endeavors to reach them. The points are calculated for each player
 * differently.
 */
public abstract class Bot {
    /**
     * Number of millis that indicates the delay between bots moves.
     */
    public static int delay = 100;

    /**
     * Main Bot function, that perform bot moves. The algorithm calculates the best bot move it can actually make.
     * @param player Player for who AI will perform move.
     */
    public static void botMove(Player player) {

        player.clear();
        int points = 0;
        for (Pawn pawn : player.getPawns()) {
            MoveManager.generateMovePaths(pawn);
            if(player.getBotchoosenpath() == null && MoveManager.getPaths().size() >0)
            {
                player.setBotchoosenpath(MoveManager.getPaths().get(0));
                player.setBotchoosenpawn(pawn);
                delay = MoveManager.getPaths().get(0).getPath().size() * 300;
            }
            if (MoveManager.getPaths() != null) {
                for (int i = 0; i < MoveManager.getPaths().size(); i++) {
                    int pointstemp = calculatePoints(MoveManager.getPaths().get(i).getStart(),MoveManager.getPaths().get(i).getEnd(),player);
                    if(pointstemp>points)
                    {
                        player.setBotchoosenpath(MoveManager.getPaths().get(i));
                        delay = MoveManager.getPaths().get(i).getPath().size() * 300;

                        player.setBotchoosenpawn(pawn);
                        points = pointstemp;
                    }
                    else if (pointstemp == points)
                    {
                        int rand = (new Random(System.currentTimeMillis())).nextInt();
                        if(rand%2 == 0)
                        {
                            player.setBotchoosenpath(MoveManager.getPaths().get(i));
                            delay = MoveManager.getPaths().get(i).getPath().size() * 300;

                            player.setBotchoosenpawn(pawn);
                            points = pointstemp;
                        }
                    }
                }
            }
        }
        assert player.getBotchoosenpath() != null;
        player.setBotchoosendestination(player.getBotchoosenpath().getEnd());
    }

    /**
     * Function that calculates special points after distinct move.
     * @param start Move start field.
     * @param end Move end field.
     * @param player Player AI is performing move for.
     * @return Number of points after that move.
     */
    private static int calculatePoints(Field start, Field end, Player player)
    {
        int points = 0;
        for(Pawn pawn:player.getPawns())
        {
            if(pawn.getId().equals(start.getId()))
            {
                switch (player.getId())
                {
                    case "1":
                    {
                        points += calculateFieldPointsPlayer1(end);
                        break;
                    }
                    case "2":
                    {
                        points += calculateFieldPointsPlayer2(end);
                        break;
                    }
                    case  "3":
                    {
                        points += calculateFieldPointsPlayer3(end);
                        break;
                    }
                    case "4":
                    {
                        points += calculateFieldPointsPlayer4(end);
                        break;
                    }
                    case "5":
                    {
                        points += calculateFieldPointsPlayer5(end);
                        break;
                    }
                    case "6":
                    {
                        points += calculateFieldPointsPlayer6(end);
                        break;
                    }
                }
            }
            else
            {
                switch (player.getId())
                {
                    case "1":
                    {
                        points += calculateFieldPointsPlayer1(GameManager.getBoard().getFieldById(pawn.getId()));
                        break;
                    }
                    case "2":
                    {
                        points += calculateFieldPointsPlayer2(GameManager.getBoard().getFieldById(pawn.getId()));
                        break;
                    }
                    case  "3":
                    {
                        points += calculateFieldPointsPlayer3(GameManager.getBoard().getFieldById(pawn.getId()));
                        break;
                    }
                    case "4":
                    {
                        points += calculateFieldPointsPlayer4(GameManager.getBoard().getFieldById(pawn.getId()));
                        break;
                    }
                    case "5":
                    {
                        points += calculateFieldPointsPlayer5(GameManager.getBoard().getFieldById(pawn.getId()));
                        break;
                    }
                    case "6":
                    {
                        points += calculateFieldPointsPlayer6(GameManager.getBoard().getFieldById(pawn.getId()));
                        break;
                    }
                }
            }
        }
        return points;
    }

    /**
     * Function calculates distinct field points for player 1.
     * @param end Field for which points will be calculated.
     * @return Returns points this field has.
     */
    private static int calculateFieldPointsPlayer1(Field end)
    {
        int points = 0;
        if(end.getY()>=1 && end.getY()<=GameManager.getNumberOfPawns())
        {
            points += 4*GameManager.getNumberOfPawns();
        }
        else
        {
            points += 2*GameManager.getNumberOfPawns() - Math.abs(end.getY() - GameManager.getNumberOfPawns());
        }

        if(end.getZ()>= 1 && end.getZ() <= GameManager.getNumberOfPawns())
        {
            points += 4*GameManager.getNumberOfPawns();
        }
        else
        {
            points += 2*GameManager.getNumberOfPawns() - Math.abs(end.getZ() - GameManager.getNumberOfPawns());
        }

        points += 4*GameManager.getNumberOfPawns() - Math.abs(end.getX() - 2*GameManager.getNumberOfPawns());
        return points;
    }
    /**
     * Function calculates distinct field points for player 2.
     * @param end Field for which points will be calculated.
     * @return Returns points this field has.
     */
    static int calculateFieldPointsPlayer2(Field end)
    {
        int points = 0;
        if(end.getY()>=-GameManager.getNumberOfPawns() && end.getY()<=-1)
        {
            points += 4*GameManager.getNumberOfPawns();
        }
        else
        {
            points += 2*GameManager.getNumberOfPawns() - Math.abs(end.getY() - (-GameManager.getNumberOfPawns()));
        }

        if(end.getZ()>= -GameManager.getNumberOfPawns() && end.getZ() <= -1)
        {
            points += 4*GameManager.getNumberOfPawns();
        }
        else
        {
            points += 2*GameManager.getNumberOfPawns() - Math.abs(end.getZ() - (-GameManager.getNumberOfPawns()));
        }

        points += 4*GameManager.getNumberOfPawns() - Math.abs(end.getX() - (-2*GameManager.getNumberOfPawns()));
        return points;
    }
    /**
     * Function calculates distinct field points for player 3.
     * @param end Field for which points will be calculated.
     * @return Returns points this field has.
     */
    static int calculateFieldPointsPlayer3(Field end)
    {
        int points = 0;
        if(end.getY()>=1 && end.getY()<=GameManager.getNumberOfPawns())
        {
            points += 4*GameManager.getNumberOfPawns();
        }
        else
        {
            points += 2*GameManager.getNumberOfPawns() - Math.abs(end.getY() - (GameManager.getNumberOfPawns()));
        }

        if(end.getX()>= -GameManager.getNumberOfPawns() && end.getX() <= -1)
        {
            points += 4*GameManager.getNumberOfPawns();
        }
        else
        {
            points += 2*GameManager.getNumberOfPawns() - Math.abs(end.getX() - (-GameManager.getNumberOfPawns()));
        }

        points += 4*GameManager.getNumberOfPawns() - Math.abs(end.getZ() - (-2*GameManager.getNumberOfPawns()));
        return points;
    }
    /**
     * Function calculates distinct field points for player 4.
     * @param end Field for which points will be calculated.
     * @return Returns points this field has.
     */
    static int calculateFieldPointsPlayer4(Field end)
    {
        int points = 0;
        if(end.getY()>=-GameManager.getNumberOfPawns() && end.getY()<=-1)
        {
            points += 4*GameManager.getNumberOfPawns();
        }
        else
        {
            points += 2*GameManager.getNumberOfPawns() - Math.abs(end.getY() - (-GameManager.getNumberOfPawns()));
        }

        if(end.getX()>= 1 && end.getX() <= GameManager.getNumberOfPawns())
        {
            points += 4*GameManager.getNumberOfPawns();
        }
        else
        {
            points += 2*GameManager.getNumberOfPawns() - Math.abs(end.getX() - (GameManager.getNumberOfPawns()));
        }

        points += 4*GameManager.getNumberOfPawns() - Math.abs(end.getZ() - (2*GameManager.getNumberOfPawns()));
        return points;
    }
    /**
     * Function calculates distinct field points for player 5.
     * @param end Field for which points will be calculated.
     * @return Returns points this field has.
     */
    static int calculateFieldPointsPlayer5(Field end)
    {
        int points = 0;
        if(end.getZ()>=-GameManager.getNumberOfPawns() && end.getZ()<=-1)
        {
            points += 4*GameManager.getNumberOfPawns();
        }
        else
        {
            points += 2*GameManager.getNumberOfPawns() - Math.abs(end.getZ() - (-GameManager.getNumberOfPawns()));
        }

        if(end.getX()>= 1 && end.getX() <= GameManager.getNumberOfPawns())
        {
            points += 4*GameManager.getNumberOfPawns();
        }
        else
        {
            points += 2*GameManager.getNumberOfPawns() - Math.abs(end.getX() - (GameManager.getNumberOfPawns()));
        }

        points += 4*GameManager.getNumberOfPawns() - Math.abs(end.getY() - (2*GameManager.getNumberOfPawns()));
        return points;
    }
    /**
     * Function calculates distinct field points for player 6.
     * @param end Field for which points will be calculated.
     * @return Returns points this field has.
     */
    static int calculateFieldPointsPlayer6(Field end)
    {
        int points = 0;
        if(end.getZ()>=1 && end.getZ()<=GameManager.getNumberOfPawns())
        {
            points += 4*GameManager.getNumberOfPawns();
        }
        else
        {
            points += 2*GameManager.getNumberOfPawns() - Math.abs(end.getZ() - (GameManager.getNumberOfPawns()));
        }

        if(end.getX()>= -GameManager.getNumberOfPawns() && end.getX() <= -1)
        {
            points += 4*GameManager.getNumberOfPawns();
        }
        else
        {
            points += 2*GameManager.getNumberOfPawns() - Math.abs(end.getX() - (-GameManager.getNumberOfPawns()));
        }

        points += 4*GameManager.getNumberOfPawns() - Math.abs(end.getY() - (-2*GameManager.getNumberOfPawns()));
        return points;
    }
}

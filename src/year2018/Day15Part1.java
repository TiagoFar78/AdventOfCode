package year2018;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

import main.Challenge;

public class Day15Part1 extends Challenge {
    
    private static final int HP = 200;
    private static final int DAMAGE = 3;
    private static final int ELF = 1;
    private static final int GOBLIN = -1;
    private static final int[][] DIR = {
            { 0, -1 },
            { -1, 0 },
            { 1, 0 },
            { 0, 1 }
    };
    
    private int solve(List<String> gridString) {
        int[][] grid = createGrid(gridString);
        
        Queue<int[]> activeAgents = new PriorityQueue<>((a, b) -> a[0] - b[0] == 0 ? a[1] - b[1] : a[0] - b[0]);
        Queue<int[]> unactiveAgents = new PriorityQueue<>((a, b) -> a[0] - b[0] == 0 ? a[1] - b[1] : a[0] - b[0]);
        
        List<int[]> agents = getAgents(grid);
        int[] agentsHP = new int[agents.size()];
        for (int[] agent : agents) {
            activeAgents.add(agent);
            agentsHP[hpId(agent[2])] = HP;
        }
        
        int rounds = 0;
        out:while (true) {
            while (!activeAgents.isEmpty()) {
                int[] agent = activeAgents.poll();
                play(grid, agent, agentsHP);
                if (!hasBothTeamsAlive(grid)) {
                    break out;
                }
                
                unactiveAgents.add(agent);
            }
            
            rounds++;
            Queue<int[]> temp = unactiveAgents;
            unactiveAgents = activeAgents;
            activeAgents = temp;
        }
        
        int totalHP = 0;
        for (int HP : agentsHP) {
            if (HP > 0) {
                totalHP += HP;
            }
        }
        
        return rounds * totalHP;
    }
    
    private boolean play(int[][] grid, int[] agent, int[] agentsHP) {
        if (agentsHP[hpId(agent[2])] <= 0) {
            return hasBothTeamsAlive(grid);
        }
        
        return attack(grid, agent, agentsHP) || move(grid, agent, agentsHP);
    }
    
    private boolean hasBothTeamsAlive(int[][] grid) {
        boolean hasElf = false;
        boolean hasGoblin = false;
        
        int n = grid.length;
        int m = grid[0].length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] >= 2) {
                    hasElf = true;
                }
                else if (grid[i][j] <= -2) {
                    hasGoblin = true;
                }
            }
        }
        
        return hasElf && hasGoblin;
    }
    
    private boolean attack(int[][] grid, int[] agent, int[] agentsHP) {
        int targetId = -1;
        int line = -1;
        int col = -1;
        
        for (int[] dir : DIR) {
            int c = grid[agent[0] + dir[0]][agent[1] + dir[1]];
            if (c == 0 || c == -1 || c > 0 == agent[2] > 0) {
                continue;
            }
            
            if (targetId == -1 || agentsHP[hpId(targetId)] > agentsHP[hpId(c)]) {
                targetId = c;
                line = agent[0] + dir[0];
                col = agent[1] + dir[1];
            }
        }
        
        if (targetId != -1) {
            agentsHP[hpId(targetId)] -= DAMAGE;
            if (agentsHP[hpId(targetId)] <= 0) {
                grid[line][col] = 0;
            }
        }
        
        return targetId != -1;
    }
    
    private boolean move(int[][] grid, int[] agent, int[] agentsHP) {
        int[] target = selectTarget(grid, agent);
        if (target == null) {
            return true;
        }
        
        grid[agent[0]][agent[1]] = 0;
        int[] move = moveTo(grid, agent[0], agent[1], target[0], target[1]);
        agent[0] = move[0];
        agent[1] = move[1];
        grid[agent[0]][agent[1]] = agent[2];
        
        return attack(grid, agent, agentsHP) || true;
    }
    
    private int[] selectTarget(int[][] grid, int[] agent) {
        boolean isElf = grid[agent[0]][agent[1]] > 0;
        int n = grid.length;
        int m = grid[0].length;

        Queue<int[]> queue = new PriorityQueue<>((a, b) -> a[2] - b[2] != 0 ? a[2] - b[2] : a[0] - b[0] != 0 ? a[0] - b[0] : a[1] - b[1]);
        boolean[][] visited = new boolean[n][m];

        queue.offer(new int[]{agent[0], agent[1], 0});
        visited[agent[0]][agent[1]] = true;

        while (!queue.isEmpty()) {
            int[] current = queue.poll();

            for (int[] dir : DIR) {
                int x = current[0] + dir[0];
                int y = current[1] + dir[1];

                if (x >= 0 && y >= 0 && x < n && y < m && !visited[x][y] && grid[x][y] != -1) {
                    visited[x][y] = true;

                    if (grid[x][y] == 0) {
                        queue.offer(new int[]{ x, y, current[2] + 1 });
                    }
                    else if (isElf != grid[x][y] > 0) {
                        return new int[] { current[0], current[1] };
                    }
                }
            }
        }

        return null;
    }
    
    private int[] moveTo(int[][] grid, int startX, int startY, int targetX, int targetY) {
        int n = grid.length;
        int m = grid[0].length;

        Queue<int[]> queue = new PriorityQueue<>((a, b) -> a[2] - b[2] != 0 ? a[2] - b[2] : a[0] - b[0] != 0 ? a[0] - b[0] : a[1] - b[1]);
        boolean[][] visited = new boolean[n][m];

        queue.offer(new int[] { targetX, targetY, 0 });
        visited[targetX][targetY] = true;

        while (!queue.isEmpty()) {
            int[] current = queue.poll();

            for (int[] dir : DIR) {
                int x = current[0] + dir[0];
                int y = current[1] + dir[1];

                if (x >= 0 && y >= 0 && x < n && y < m && !visited[x][y] && grid[x][y] != -1) {
                    visited[x][y] = true;

                    if (grid[x][y] == 0) {
                        queue.offer(new int[]{ x, y, current[2] + 1 });
                    }
                    
                    if (x == startX && y == startY) {
                        return new int[] { current[0], current[1] };
                    }
                }
            }
        }
        
        return null;
    }
    
    private List<int[]> getAgents(int[][] grid) {
        List<int[]> agents = new ArrayList<>();
        int n = grid.length;
        int m = grid[0].length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] != 0 && grid[i][j] != -1) {
                    agents.add(new int[] {i, j, grid[i][j]});
                }
            }
        }
        
        return agents;
    }
    
    private int hpId(int id) {
        return Math.abs(id) - 2;
    }
    
    private int[][] createGrid(List<String> gridString) {
        int id = 2;
        
        int[][] grid = new int[gridString.size()][gridString.get(0).length()];
        for (int i = 0; i < gridString.size(); i++) {
            for (int j = 0; j < gridString.get(i).length(); j++) {
                char c = gridString.get(i).charAt(j);
                if (c == '#') {
                    grid[i][j] = -1;
                }
                else if (c == '.') {
                    grid[i][j] = 0;
                }
                else if (c == 'E'){
                    grid[i][j] = ELF * id;
                    id++;
                }
                else if (c == 'G') {
                    grid[i][j] = GOBLIN * id;
                    id++;
                }
            }
        }
        
        return grid;
    }
    
    @Override
    public long solve() {
        List<String> grid = new ArrayList<>();
        
        Scanner reader = getInputFile();
        while (reader.hasNextLine()) {
            grid.add(reader.nextLine());
        }
        reader.close();
        
        return solve(grid);
    }
    
}

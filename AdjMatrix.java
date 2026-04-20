import java.util.*;

public class AdjMatrix {
    private int[][] mat;
    private String[][] owners;
    private int size;
    private int inf = (int)1e8;

    public AdjMatrix(int n) {
        size = n;
        mat = new int[n][n];
        owners = new String[n][n];
        for (int i=0;i<n;i++) {
            Arrays.fill(mat[i], inf);
            mat[i][i] = 0;
        }
    }

    public void addConnection(String owner, int i, int j, int weight) {
        mat[i][j] = weight;
        mat[j][i] = weight;

        owners[i][j] = owner;
        owners[j][i] = owner;
    }

    public void removeConnection(int i, int j) {
        mat[i][j] = inf;
        mat[j][i] = inf;

        owners[i][j] = null;
        owners[j][i] = null;
    }

    public String whoOwnsIt(int i, int j) {
        if (owners[i][j]==null) {
            return "NO OWNER";
        }
        return owners[i][j];
    }

    private int[][] floydWarshall(String filter) {
        int[][] dist = new int[size][size];
        for (int i=0;i<size;i++) {
            Arrays.fill(dist[i], inf);
            dist[i][i] = 0;

            for (int j=0;j<size;j++) {
                if (mat[i][j] != inf && (filter==null || filter.equals(whoOwnsIt(i,j)))) {
                    dist[i][j] = mat[i][j];
                }
            }
        }
        for (int k=0;k<size;k++) {
            for (int i=0;i<size;i++) {
                for (int j=0;j<size;j++) {
                    if (dist[i][k] != inf && dist[k][j] != inf) {
                        dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
                    }
                }
            }
        }
        return dist;
    }

    public int shortestPath (int start, int end) {
        int[][] dist = floydWarshall(null);
        return dist[start][end];
    }

    public boolean connectivityCheck(int start, int end, String player) {
        int[][] dist = floydWarshall(player);
        return dist[start][end] != inf;
    }
}
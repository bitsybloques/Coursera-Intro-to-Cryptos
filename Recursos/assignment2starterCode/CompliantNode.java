//Importamos clases HashSet y Set para poder implementar una hash table. Esto nos resultará muy útil porque la HashSet no permite elementos duplicados.
import java.util.HashSet;
import java.util.Set;

//Importamos clase StreamCollectorstoSet para agrupar datos de los emisores
import static java.util.stream.Collectors.toSet;

/* CompliantNode refers to a node that follows the rules (not malicious)*/
public class CompliantNode implements Node {

//Declaramos las variables que vamos a usar
    private double p_graph;
    private double p_malicious;
    private double p_tXDistribution;
    private int numRounds;

    private boolean[] followees;

    private Set<Transaction> pendingTransactions;

    private boolean[] blackListed;

    public CompliantNode(double p_graph, double p_malicious, double p_txDistribution, int numRounds) {
//Asignamos valor a cada variabe que hemos declarado
	this.p_graph = p_graph;
        this.p_malicious = p_malicious;
        this.p_tXDistribution = p_txDistribution;
        this.numRounds = numRounds;
    }

    public void setFollowees(boolean[] followees) {
//Asignamos valor a la variable       
	this.followees = followees;

//Creamos un array vacío de tantos elementos como followees
        this.blackListed = new boolean[followees.length];
    }

    public void setPendingTransaction(Set<Transaction> pendingTransactions) {
//Asignamos valor a la variable          
	this.pendingTransactions = pendingTransactions;
    }

    public Set<Transaction> sendToFollowers() {
//enviar transacciones pendientes sin duplicados        
	Set<Transaction> toSend = new HashSet<>(pendingTransactions);
        pendingTransactions.clear();
        return toSend;
    }

    public void receiveFromFollowees(Set<Candidate> candidates) {
    	Set<Integer> senders = candidates.stream().map(c -> c.sender).collect(toSet());
        for (int i = 0; i < followees.length; i++) {
            if (followees[i] && !senders.contains(i))
                blackListed[i] = true;
        }
        for (Candidate c : candidates) {
            if (!blackListed[c.sender]) {
                pendingTransactions.add(c.tx);
            }
        }
    }
}

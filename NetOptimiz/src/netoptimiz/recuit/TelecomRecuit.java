package netoptimiz.recuit;

import edu.uci.ics.jung.graph.UndirectedSparseMultigraph;
import netoptimiz.Application;
import netoptimiz.graphe.Arc;
import netoptimiz.graphe.Graphe;
import netoptimiz.graphe.Noeud;
import netoptimiz.NetOptimizApp;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;
import java.awt.Dimension;
import javax.swing.JFrame;
import netoptimiz.Methode;


public class TelecomRecuit extends ModeleRecuit {

    private double cout;
    private double deltaCout;
    private double capaciteInitiale;
    private Arc monArc;
    protected Graphe monGraphe;
    // pour savoir si on a ajouté ou supprimé une capacité
    private boolean suppression;
    // Variables nécessaires à déterminer la température initiale
    private double transGenerees=0;
    private double transAcceptees=0;

    public TelecomRecuit () {
        // On crée une copie du graphe original
        monGraphe = Application.getSingleton().getgrapheOriginal().clone();
    }

    // Permet d'afficher des infos dans l'interface
    public void afficherInfos(String type,String s) {
        // options d'affichage :
        // console
        // principal
        // température
        // itérations
        NetOptimizApp.getApplication().getView().refresh(type,s);
    }

    public double resoudre (int nombrePalliers, int iterationsInternes) {
        // On récupère le premier arc afin d'avoir la capacité initiale
        capaciteInitiale = monGraphe.getarcs().get(0).getCapacite();
        // Initialisation du nombre de paliers de température
        this.setNombrePalliers(nombrePalliers);
        // Initialisation du nombre d'itérations internes
        this.setIterationsInternes(iterationsInternes);
        // Calcule et affecte la température initiale
        tempInitiale(monGraphe);
        // Affiche des informations dans l'interface
        this.afficherInfos("principal","Température initiale = " + this.getTemperature());
        this.afficherInfos("température",""+(double)Math.round(this.getTemperature()*100)/100);
        // On déroule l'algo tant que l'état gelé n'est pas atteint (nombre de paliers atteint)
        for (int nbPalliers = 1; nbPalliers <= this.getNombrePalliers(); nbPalliers++) {
            // Calcul du cout du graphe
            cout=calculerCout(monGraphe);
            // boucle pour les itérations par température
            for (int nbIterationsEnCours = 1; nbIterationsEnCours <= this.getIterationsInternes(); nbIterationsEnCours++) {
                this.afficherInfos("itérations",String.valueOf(nbIterationsEnCours));
                // On fait un mouvement
                faireMvt(monGraphe);
                // Si le mouvement n'est pas accepté on fait le mouvement inverse pour se remettre à l'état précédent
                if (accepterMVT(monGraphe)==false) {
                    if (monArc.getCapacite()==0) monArc.setCapacite(capaciteInitiale);
                    else monArc.setCapacite(0);
                }
            }
            // Les itérations ont été faites => On baisse la température
            this.setTemperature(this.getTemperature() * this.getDecroissanceTemp());
            // On affiche la température courante sur l'interface (2 chiffres après la virgule)
            this.afficherInfos("température",""+(double)Math.round(this.getTemperature()*100)/100);
            // On décrémente le palier
            this.setNombrePalliers(nombrePalliers - 1);
        } // Fin du recuit
        // Afin de connaître le nombre d'arcs ayant une capacité pour la solution
        int nbArcsSolution=0;
        // On affiche la liste des arcs ayant une capacité
        this.afficherInfos("principal","Liste des arcs:");
        for (Arc a : monGraphe.getarcs()) {
            if (a.getCapacite()>0) {
                this.afficherInfos("principal",a.getNoeudOrigine().getNom()+"-"+a.getNoeudExtremite().getNom()+" Cout:"+a.getCout());
                nbArcsSolution++;
            }
        }
        this.afficherInfos("principal","Nombre d'arcs = " + nbArcsSolution);
        // Dessine le graph
        //drawGraph(monGraphe);
        NetOptimizApp.getApplication().getView().drawGraph(monGraphe, Methode.Recuit);
        // On retourne la soultion
        return calculerCout(monGraphe);
    }

    // Calcul de la température initiale selon Kirkpatrick
    public void tempInitiale (Graphe g) {
        // On travaille sur un clone du graphe
        Graphe tempGrapheTinit = g.clone();
        cout=calculerCout(g);
        double tauxAcceptation;
        // Définition d'une température de départ: fixée à la moyenne des couts des arcs
        double coutMoyen=0;
        for (Arc a : g.getarcs()) {
            coutMoyen=coutMoyen+a.getCout();
        }
        coutMoyen=coutMoyen/g.getarcs().size();
        this.setTemperature(coutMoyen);
        // boucle pour déterminer la température initiale
        do {
            // On défini le nombre de transformations couteuses à évaluer
            //  On a déterminé que ce nombre serait égal au nombre d'arcs du graphe original
            double nbTransCouteuses=tempGrapheTinit.getarcs().size();
            do {
                faireMvt(tempGrapheTinit);
                accepterMVT(tempGrapheTinit);
            }
            while (transGenerees<nbTransCouteuses); // On a déterminé que l'on s'arrétait à ce nombre de transformations couteuses
            tauxAcceptation = transAcceptees/transGenerees;
            // Dans le cas où le while serait vérifié on double la température et on recommence
            this.setTemperature(getTemperature()*2);
            transAcceptees=0;
            transGenerees=0;
        }
        while (tauxAcceptation<0.80); // objectif à atteindre : taux acceptation doit être au moins égal à 80%
    }

    public void faireMvt (Graphe g) {
        // On récupère le nombre d'arcs du graphe
        int taille=g.getarcs().size();
        // On prend au hasard un des arcs et on change sa capacité
        monArc = g.getarcs().get((int)(Math.random()*taille));
        //System.out.print("Arc a modifier:" + monArc.getNoeudOrigine().getNom() + monArc.getNoeudExtremite().getNom()+"\n");
        if (monArc.getCapacite()==0) {
            monArc.setCapacite(capaciteInitiale);
            suppression=false;
        }
        else {
            monArc.setCapacite(0);
            suppression=true;
        }
    }

    public boolean accepterMVT (Graphe g) {
        // On clone le grpahe pour ne pas travailler dessus directement pour l'acceptation ou non du mouvement
        Graphe tempGraphe = g.clone();
        // On commence par le calcul du deltaCout et de la probabilité d'acceptation car c'est moins lourd à
        //  calculer que de vérifier que chaque noeud est bien relié au réseau

        // on calcule le cout du nouveau graphe et on fait le delta
        deltaCout = deltaCout(tempGraphe);
        // si deltaCout>0 alors on calcule la probabilité qu'il soit intérressant d'explorer ce système
        if (deltaCout>0) {
            double exp=Math.exp(-deltaCout/getTemperature());
            double p = Math.random();
            transGenerees++;
            if (p>exp) return false;
            else transAcceptees++;
        }

        //*** En cas d'ajout de capacité il est inutile de faire les tests suivants ***//
        if (suppression) {
        // 1) On  cherche à vérifier que chaque Noeud à au moins un arc de capacité!=0
            // Devenu presque "inutile" après le test des demandes sauf qu'il est intéressant de le faire pour
            //  gagner du temps de traitement car pas très couteux et peut éviter le calcul lourd des demandes
            boolean flagNoeudOrphelin=true;
            for (Noeud n : g.getnoeuds()) {
                for (Arc a : g.getarcs()) {
                    // On regarde si la capacité de l'arc est !=0 et on regarde si son noeud origine ou extremité correspond au noeud courant
                    if (a.getCapacite()!=0 && (a.getNoeudOrigine().equals(n) || a.getNoeudExtremite().equals(n))) {
                        flagNoeudOrphelin=false;
                        break;
                    }
                }
            }
            // si le noeud a aucun arc de capacité!=0 alors le mouvement n'est pas accepté
            if (flagNoeudOrphelin) { //System.out.print("***** Refusé *****\n");
                return false;
            }
            
        /*
        // 2) vérification que le réseau n'a pas été coupé en 2 sous-réseaux
        // pour cela on vérifie que les 2 noeuds concernés peuvent se joindre via d'autres arcs
        //**** Cette vérification a été abandonnée car n'apporte rien en temps de traitement et le test des
        //      demandes vérifie cet aspect ****
            // On récupère les noeuds extrémité et origine
            Noeud n1=monArc.getNoeudOrigine();
            Noeud n2=monArc.getNoeudExtremite();
            // On crée un graphe Jung (non orienté)
            UndirectedSparseMultigraph<Noeud, Arc> gJung = new UndirectedSparseMultigraph<Noeud, Arc>();
            // On l'alimente pas les arcs de notre graphe
            for (Arc a : Graphe.getSingleton().getarcs()) {
              gJung.addEdge(a,a.getNoeudOrigine(), a.getNoeudExtremite());
            }
            // On regarde si les noeuds sont toujours reliés grace au Dijkstra
            List<Arc> chemin = Application.getSingleton().TrouverCheminPlusCourt(gJung, n1, n2);
            // Si la liste de retour est vide, c'est que le réseau est coupé en 2 sous réseaux => non valide
            if (chemin.isEmpty()) return false;
        */    
            
        // 3) On vérifie que les demandes sont remplies
            // On crée un graphe Jung (non orienté)
            UndirectedSparseMultigraph<Noeud, Arc> gJung = new UndirectedSparseMultigraph<Noeud, Arc>();
            // On l'alimente pas les arcs de notre graphe
            for (Arc a : tempGraphe.getarcs()) {
                // On ne crée que les arcs qui ont une capacité
                if (a.getCapacite()!=0) gJung.addEdge(a,a.getNoeudOrigine(), a.getNoeudExtremite());
            }
            // On alimente les noeuds à notre graphe
            for (Noeud n : tempGraphe.getnoeuds()) {
              gJung.addVertex(n);
            }
            // On appelle la fonction de vérification des demandes

            if (!Application.getSingleton().verifierDemandes(gJung)) {
                //System.out.print("***** Refusé *****\n");
                return false;
            }
        }
        //*******************//
        // Si tout est vérifié
        cout = calculerCout(tempGraphe);
        //System.out.print("***** Accepté *****\n");
        return true;
    }

    // pour déterminer le cout du graphe
    public double calculerCout (Graphe g) {
        double coutTransformation=0;
        for (Arc a : g.getarcs()) {
            // Si la capacité!=0 alors on ajoute le cout de l'arc
            if (a.getCapacite()!=0) {
                coutTransformation = coutTransformation + a.getCout();
            }
        }
        return coutTransformation;
    }

    public double deltaCout (Graphe tempGraphe) {
        deltaCout=calculerCout(tempGraphe)-cout;
        return deltaCout;
    }

    public void drawGraph (Graphe gJungGraphDraw) {
        UndirectedSparseMultigraph<Noeud, Arc> gJungGraph = new UndirectedSparseMultigraph<Noeud, Arc>();
        // On l'alimente pas les arcs de notre graphe
        for (Arc a : gJungGraphDraw.getarcs()) {
            // On ne créer que les arcs qui ont une capacité
            if (a.getCapacite()!=0) gJungGraph.addEdge(a,a.getNoeudOrigine(), a.getNoeudExtremite());
        }
        // On alimente les noeuds à notre graphe
        for (Noeud n : gJungGraphDraw.getnoeuds()) {
          //gJungGraph.addVertex(n);
        }
        // Layout<V, E>, BasicVisualizationServer<V,E>
        Layout<Integer, String> layout = new CircleLayout(gJungGraph);
        layout.setSize(new Dimension(300,300));
        BasicVisualizationServer<Integer,String> vv = new BasicVisualizationServer<Integer,String>(layout);
        vv.setPreferredSize(new Dimension(500,500));
        // Setup up a new vertex to paint transformer...
        /*Transformer<Integer,Paint> vertexPaint = new Transformer<Integer,Paint>() {
            public Paint transform(Integer i) {
            return Color.GREEN;
            }
        };*/
        // Set up a new stroke Transformer for the edges
        /*float dash[] = {10.0f};
        final Stroke edgeStroke = new BasicStroke(1.0f, BasicStroke.CAP_BUTT,
        BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f);
        Transformer<String, Stroke> edgeStrokeTransformer = new Transformer<String, Stroke>() {
        public Stroke transform(String s) {
        return edgeStroke;
        }
        };*/
        //vv.getRenderContext().setVertexFillPaintTransformer(vertexPaint);
        //vv.getRenderContext().setEdgeStrokeTransformer(edgeStrokeTransformer);
        vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
        vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller());
        vv.getRenderer().getVertexLabelRenderer().setPosition(Position.CNTR);
        JFrame frame = new JFrame("Graph Recuit");
        frame.getContentPane().add(vv);
        frame.pack();
        frame.setVisible(true);
    }

    // devenu inutile après avoir utilisé un boolen pour accepterMvt()
    public void refuserMvt () {
    }

}

package the.project.core.agents.price;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import java.io.IOException;
import java.util.ArrayList;
import the.project.core.objects.Request;
import the.project.core.objects.RequestSearch;
import the.project.core.objects.Restaurant;

public class PriceAgent extends Agent {

    @Override
    protected void setup() {

        addBehaviour(new OneShotBehaviour() {
            @Override
            public void action() {
                System.out.println("Olá. Eu sou o " + getLocalName() + ".");
            }
        });

        addBehaviour(new CyclicBehaviour(this) {
            @Override
            public void action() {
                ACLMessage rec = myAgent.receive();
                if (rec != null) {
                    System.out.println(getLocalName() + ": Recebi uma mensagem de " + rec.getSender().getLocalName() + ".");
                    try {
                        RequestSearch req = (RequestSearch) rec.getContentObject();

                        Request preferencias = req.getPreferencias();
                        ArrayList<Restaurant> restaurantes = req.getRestaurantes();
                        for (Restaurant rest : restaurantes) {
                            if (!rest.getPrice().equalsIgnoreCase(preferencias.getPrice())) {
                                restaurantes.remove(rest);
                            }
                        }
                        
                        ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
                        msg.addReceiver(new AID("SearchAgent", AID.ISLOCALNAME));
                        msg.setContentObject(req);
                        send(msg);
                        System.out.println(getLocalName() + ": Enviei uma mensagem de " + rec.getSender().getLocalName() + ".");

                    } catch (UnreadableException e) {
                    } catch (IOException ex) {
                        System.out.println("Erro ao enviar mensagem: " + getLocalName() + " -> " + rec.getSender().getLocalName() + ".");
                    }
                } else {
                    block();
                }
            }
        });

    }

}

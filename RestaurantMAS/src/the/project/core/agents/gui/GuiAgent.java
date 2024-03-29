package the.project.core.agents.gui;

import jade.core.*;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import java.awt.Container;
import java.awt.Dimension;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.TitledBorder;
import the.project.core.RestaurantCard;
import the.project.core.data.RestaurantData;
import the.project.core.objects.Request;
import the.project.core.objects.RequestSearch;
import the.project.core.objects.Restaurant;

public class GuiAgent extends Agent {

    private final JFrame frame;
    
    private JButton btnFind;
    private JComboBox<String> cbType;
    private JComboBox<String> cbScore;
    private JComboBox<String> cbPrice;
    private JLabel lblType;
    private JLabel lblDistance;
    private JLabel lblPrice;
    private JLabel lblScore;
    private JLabel lblTo2;
    private JPanel paneResult;
    private JPanel paneFilter;
    private JScrollPane scroll;
    private JTextField txtDistanceFrom;
    private JTextField txtDistanceTo;
    
    public GuiAgent() {
        super();
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {}
        frame = new JFrame();
        frame.setSize(new Dimension(355, 500));
        frame.setTitle("Restaurant MAS");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.getContentPane().setLayout(null);
        
        createInterface();
        
        frame.setVisible(true);
    }
    
    @Override
    protected void setup(){

        addBehaviour(new OneShotBehaviour() {
            @Override
            public void action() {
                System.out.println("Olá. Eu sou o " + getLocalName() + ".");
            }
        });
        
        addBehaviour(new CyclicBehaviour(this) {
            @Override
            public void action() {
                ACLMessage rec = receive();
                String receiver = "SearchAgent";
                if (rec != null) {
                    System.out.println(getLocalName() + ": Recebi uma mensagem de " + rec.getSender().getLocalName() + ".");
                    if(rec.getSender().getLocalName().contains("Usuário")){
                        try {
                            Request req = (Request) rec.getContentObject();
                            ArrayList<Restaurant> restaurantes = new ArrayList<>();
                            restaurantes.addAll(Arrays.asList(RestaurantData.data));
                            ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
                            msg.addReceiver(new AID(receiver, AID.ISLOCALNAME));
                            msg.setContentObject(new RequestSearch(restaurantes,req));
                            send(msg);
                            System.out.println(getLocalName() + ": Enviei uma mensagem para " + receiver + ".\n");
                        
                        } catch (UnreadableException e) {
                        } catch (IOException e) {
                            System.out.println("Erro ao enviar mensagem: " + getLocalName() + " -> " + receiver + ". Erro: " + e.toString());
                        }
                    }else{
                        try {
                            RequestSearch req = (RequestSearch) rec.getContentObject();
                            paneResult.removeAll();
                            req.getRestaurantes().stream().map(r -> {
                                paneResult.add(new RestaurantCard(r));
                                return r;
                            }).map(_item -> {
                                paneResult.revalidate();
                                return _item;
                            }).forEachOrdered(_item -> {
                                paneResult.repaint();
                            });

                            System.out.println(getLocalName() + ": Entreguei as informações requisitadas pelo Usuário.\n");                        
                        } catch (UnreadableException e) {}
                    }
                    
                } else {
                    block();
                }
            }
        });
        
        /*addBehaviour(new CyclicBehaviour(this) {
            @Override
            public void action() {
                ACLMessage msg = receive();
                if (msg != null) {
                    System.out.println(getLocalName() + ": Recebi uma mensagem de " + msg.getSender().getLocalName() + ".");
                    try {
                        RequestSearch req = (RequestSearch) msg.getContentObject();
                        
                        req.getRestaurantes().stream().map(r -> {
                            paneResult.add(new RestaurantCard(r));
                            return r;
                        }).map(_item -> {
                            paneResult.revalidate();
                            return _item;
                        }).forEachOrdered(_item -> {
                            paneResult.repaint();
                        });
                        
                        System.out.println(getLocalName() + ": Entreguei as informações requisitadas pelo Usuário.\n");
                        
                    } catch (UnreadableException e) {}
                } else {
                    block();
                }
            }
        });*/
        
    }
    
    private void btnSendActionPerformed(java.awt.event.ActionEvent evt) {
        
        String type = cbType.getSelectedItem().toString();
        String score = cbScore.getSelectedItem().toString();
        String price = cbPrice.getSelectedItem().toString();

        float distanceFrom;
        try { distanceFrom = Float.parseFloat(txtDistanceFrom.toString()); }
        catch (NumberFormatException e) { distanceFrom = 0; }
        
        float distanceTo;
        try { distanceTo = Float.parseFloat(txtDistanceTo.toString()); }
        catch (NumberFormatException e) { distanceTo = 0; }
        
        try {
            ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
            msg.setSender(new AID("Usuário", AID.ISLOCALNAME));
            msg.addReceiver(new AID("GuiAgent", AID.ISLOCALNAME));
            msg.setContentObject(new Request(type, price, distanceFrom, distanceTo, score));
            send(msg);
        } catch (IOException e) {
            System.out.println("Erro ao enviar mensagem: Usuário -> GuiAgent");
        }
    }
    
    private void createInterface(){

        btnFind = new JButton();
        lblType = new JLabel();
        lblDistance = new JLabel();
        lblPrice = new JLabel();
        lblScore = new JLabel();
        lblTo2 = new JLabel();
        cbType = new JComboBox<>();
        cbScore = new JComboBox<>();
        cbPrice = new JComboBox<>();
        txtDistanceFrom = new JTextField();
        txtDistanceTo = new JTextField();
        scroll = new JScrollPane();
        paneResult = new JPanel();
        paneFilter = new JPanel();
        Container framePane = frame.getContentPane();
        
        // pane filtros
        paneFilter.setBorder(new TitledBorder("Selecione as opções para encontrar um restaurante"));
        paneFilter.setLayout(null);
        paneFilter.setBounds(10, 10, 330, 135);
        
        // comida
        lblType.setText("Tipo de comida");
        lblType.setBounds(10, 20, 150, 15);
        paneFilter.add(lblType);
        
        cbType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Árabe", "Brasileira", "Japonesa", "Hambúrguer", "Pizza", "Pastel", "Padaria" }));
        cbType.setBounds(10, 40, 150, 25);
        paneFilter.add(cbType);
        
        // cassificação
        lblScore.setText("Classificação");
        lblScore.setBounds(170, 20, 150, 15);
        paneFilter.add(lblScore);
        
        cbScore.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "⋆⋆⋆⋆⋆", "⋆⋆⋆⋆", "⋆⋆⋆", "⋆⋆", "⋆" }));
        cbScore.setBounds(170, 40, 150, 25);
        paneFilter.add(cbScore);
        
        // price
        lblPrice.setText("Preço");
        lblPrice.setBounds(10, 70, 150, 15);
        paneFilter.add(lblPrice);
        
        cbPrice.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "$$$", "$$", "$" }));
        cbPrice.setBounds(10, 90, 150, 25);
        paneFilter.add(cbPrice);
        
        // distance
        lblDistance.setText("Distância");
        lblDistance.setBounds(170, 70, 150, 15);
        paneFilter.add(lblDistance);
        
        txtDistanceFrom.setBounds(170, 90, 60, 25);
        txtDistanceFrom.setText("5");
        paneFilter.add(txtDistanceFrom);
        
        lblTo2.setText("Até");
        lblTo2.setBounds(235, 90, 30, 25);
        paneFilter.add(lblTo2);
        
        txtDistanceTo.setText("10");
        txtDistanceTo.setBounds(260, 90, 60, 25);
        paneFilter.add(txtDistanceTo);

        framePane.add(paneFilter);
        
        // botão procurar
        btnFind.setText("Procurar");
        btnFind.addActionListener((java.awt.event.ActionEvent evt) -> {
            btnSendActionPerformed(evt);
        });
        btnFind.setBounds(10, 155, 330, 25);
        framePane.add(btnFind);
        
        // pane resultado
        paneResult.setLayout(new BoxLayout(paneResult, BoxLayout.Y_AXIS));
        scroll.setViewportView(paneResult);
        scroll.setBounds(10, 190, 330, 270);
        framePane.add(scroll);
      
    }

}

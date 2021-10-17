package the.project.core;

import the.project.core.objects.Restaurant;

public class RestaurantCard extends javax.swing.JPanel {

    public RestaurantCard() {
        initComponents();
    }
    
    public RestaurantCard(Restaurant restaurant) {
        initComponents();
        
        lblName.setText(restaurant.getName());
        lblDistance.setText(restaurant.getDistance() + "Km");
        lblPrice.setText("R$ " + restaurant.getPrice());
        lblScore.setText(restaurant.getScore());
        lblType.setText(restaurant.getType());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblName = new javax.swing.JLabel();
        lblScore = new javax.swing.JLabel();
        lblPrice = new javax.swing.JLabel();
        lblDistance = new javax.swing.JLabel();
        lblType = new javax.swing.JLabel();

        setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblName.setText("Nome");

        lblScore.setText("⋆⋆⋆⋆");

        lblPrice.setText("$$$");

        lblDistance.setText("8,7km");

        lblType.setText("Japonesa");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblScore)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 112, Short.MAX_VALUE)
                        .addComponent(lblDistance))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblType)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblName)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblPrice)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblName)
                    .addComponent(lblPrice))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblScore)
                    .addComponent(lblDistance))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblType)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lblDistance;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblPrice;
    private javax.swing.JLabel lblScore;
    private javax.swing.JLabel lblType;
    // End of variables declaration//GEN-END:variables
}

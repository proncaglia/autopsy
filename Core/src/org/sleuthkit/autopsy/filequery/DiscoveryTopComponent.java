/*
 * Autopsy
 *
 * Copyright 2019 Basis Technology Corp.
 * Contact: carrier <at> sleuthkit <dot> org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.sleuthkit.autopsy.filequery;

import com.google.common.eventbus.Subscribe;
import java.awt.Color;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.openide.util.NbBundle;
import org.openide.windows.Mode;
import org.openide.windows.RetainLocation;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;
import org.sleuthkit.autopsy.corecomponents.DataContentPanel;
import org.sleuthkit.autopsy.corecomponents.TableFilterNode;
import org.sleuthkit.autopsy.coreutils.ThreadConfined;
import org.sleuthkit.autopsy.datamodel.FileNode;
import org.sleuthkit.datamodel.AbstractFile;

/**
 * Create a dialog for displaying the file discovery tool
 */
@TopComponent.Description(preferredID = "DiscoveryTopComponent", persistenceType = TopComponent.PERSISTENCE_NEVER)
@TopComponent.Registration(mode = "discovery", openAtStartup = false)
@RetainLocation("discovery")
@NbBundle.Messages("DiscoveryTopComponent.name= File Discovery")
public final class DiscoveryTopComponent extends TopComponent {

    private static final long serialVersionUID = 1L;
    private static final String PREFERRED_ID = "DiscoveryTopComponent"; // NON-NLS
    private static final Color SELECTED_COLOR = new Color(216, 230, 242);
    private static final Color UNSELECTED_COLOR = new Color(240, 240, 240);
    private final FileSearchPanel fileSearchPanel;
    private final GroupListPanel groupListPanel;
    private final DataContentPanel dataContentPanel;
    private final ResultsPanel resultsPanel;

    /**
     * Creates new form FileDiscoveryDialog
     */
    @ThreadConfined(type = ThreadConfined.ThreadType.AWT)
    public DiscoveryTopComponent() {
        initComponents();
        setName(Bundle.DiscoveryTopComponent_name());
        fileSearchPanel = new FileSearchPanel();
        dataContentPanel = DataContentPanel.createInstance();
        resultsPanel = new ResultsPanel();
        groupListPanel = new GroupListPanel();
        leftSplitPane.setLeftComponent(fileSearchPanel);
        leftSplitPane.setRightComponent(groupListPanel);
        rightSplitPane.setTopComponent(resultsPanel);
        rightSplitPane.setBottomComponent(dataContentPanel);
        //add list selection listener so the content viewer will be updated with the selected file
        //when a file is selected in the results panel
        resultsPanel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    SwingUtilities.invokeLater(() -> {
                        AbstractFile file = resultsPanel.getSelectedFile();
                        if (file != null) {
                            dataContentPanel.setNode(new TableFilterNode(new FileNode(file), false));
                        } else {
                            dataContentPanel.setNode(null);
                        }
                    });
                }
            }
        });

    }

    /**
     * Get the current DiscoveryTopComponent if it is open.
     *
     * @return The open DiscoveryTopComponent or null if it has not been opened.
     */
    public static DiscoveryTopComponent getTopComponent() {
        return (DiscoveryTopComponent) WindowManager.getDefault().findTopComponent(PREFERRED_ID);
    }

    /**
     * Reset the top component so it isn't displaying any results.
     */
    public void resetTopComponent() {
        resultsPanel.resetResultViewer();
        groupListPanel.resetGroupList();
    }

    /**
     * Update the search settings to a default state.
     */
    void updateSearchSettings() {
        resetTopComponent();
        fileSearchPanel.resetPanel();
        imagesButton.setSelected(true);
        imagesButton.setEnabled(false);
        imagesButton.setBackground(SELECTED_COLOR);
        imagesButton.setForeground(Color.BLACK);
        videosButton.setSelected(false);
        videosButton.setEnabled(true);
        videosButton.setBackground(UNSELECTED_COLOR);
        documentsButton.setSelected(false);
        documentsButton.setEnabled(true);
        documentsButton.setBackground(UNSELECTED_COLOR);
        fileSearchPanel.setSelectedType(FileSearchData.FileType.IMAGE);
    }

    @Override
    public void componentOpened() {
        super.componentOpened();
        WindowManager.getDefault().setTopComponentFloating(this, true);
        DiscoveryEventUtils.getDiscoveryEventBus().register(this);
        DiscoveryEventUtils.getDiscoveryEventBus().register(resultsPanel);
        DiscoveryEventUtils.getDiscoveryEventBus().register(groupListPanel);
        DiscoveryEventUtils.getDiscoveryEventBus().register(fileSearchPanel);
    }

    @Override
    protected void componentClosed() {
        fileSearchPanel.cancelSearch();
        DiscoveryEventUtils.getDiscoveryEventBus().unregister(this);
        DiscoveryEventUtils.getDiscoveryEventBus().unregister(fileSearchPanel);
        DiscoveryEventUtils.getDiscoveryEventBus().unregister(groupListPanel);
        DiscoveryEventUtils.getDiscoveryEventBus().unregister(resultsPanel);
        super.componentClosed();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.JSplitPane mainSplitPane = new javax.swing.JSplitPane();
        leftSplitPane = new javax.swing.JSplitPane();
        rightSplitPane = new javax.swing.JSplitPane();
        javax.swing.JPanel toolBarPanel = new javax.swing.JPanel();
        imagesButton = new javax.swing.JButton();
        javax.swing.JLabel stepOneLabel = new javax.swing.JLabel();
        videosButton = new javax.swing.JButton();
        javax.swing.Box.Filler filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(119, 0), new java.awt.Dimension(119, 0), new java.awt.Dimension(119, 32767));
        documentsButton = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(1400, 900));
        setLayout(new java.awt.BorderLayout());

        mainSplitPane.setDividerLocation(450);
        mainSplitPane.setPreferredSize(new java.awt.Dimension(1400, 828));

        leftSplitPane.setDividerLocation(325);
        leftSplitPane.setToolTipText("");
        leftSplitPane.setPreferredSize(new java.awt.Dimension(400, 828));
        mainSplitPane.setLeftComponent(leftSplitPane);

        rightSplitPane.setDividerLocation(475);
        rightSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        rightSplitPane.setResizeWeight(0.5);
        rightSplitPane.setPreferredSize(new java.awt.Dimension(1000, 828));
        mainSplitPane.setRightComponent(rightSplitPane);

        add(mainSplitPane, java.awt.BorderLayout.CENTER);

        imagesButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/sleuthkit/autopsy/images/pictures-icon.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(imagesButton, org.openide.util.NbBundle.getMessage(DiscoveryTopComponent.class, "DiscoveryTopComponent.imagesButton.text")); // NOI18N
        imagesButton.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/org/sleuthkit/autopsy/images/pictures-icon.png"))); // NOI18N
        imagesButton.setFocusable(false);
        imagesButton.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        imagesButton.setMaximumSize(new java.awt.Dimension(90, 43));
        imagesButton.setMinimumSize(new java.awt.Dimension(90, 43));
        imagesButton.setPreferredSize(new java.awt.Dimension(90, 43));
        imagesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                imagesButtonActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(stepOneLabel, org.openide.util.NbBundle.getMessage(DiscoveryTopComponent.class, "DiscoveryTopComponent.stepOneLabel.text")); // NOI18N

        videosButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/sleuthkit/autopsy/images/video-icon.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(videosButton, org.openide.util.NbBundle.getMessage(DiscoveryTopComponent.class, "DiscoveryTopComponent.videosButton.text")); // NOI18N
        videosButton.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/org/sleuthkit/autopsy/images/video-icon.png"))); // NOI18N
        videosButton.setDisabledSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/org/sleuthkit/autopsy/images/video-icon.png"))); // NOI18N
        videosButton.setFocusable(false);
        videosButton.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        videosButton.setMaximumSize(new java.awt.Dimension(90, 43));
        videosButton.setMinimumSize(new java.awt.Dimension(90, 43));
        videosButton.setPreferredSize(new java.awt.Dimension(90, 43));
        videosButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                videosButtonActionPerformed(evt);
            }
        });

        documentsButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/sleuthkit/autopsy/images/documents-icon.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(documentsButton, org.openide.util.NbBundle.getMessage(DiscoveryTopComponent.class, "DiscoveryTopComponent.documentsButton.text")); // NOI18N
        documentsButton.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/org/sleuthkit/autopsy/images/documents-icon.png"))); // NOI18N
        documentsButton.setDisabledSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/org/sleuthkit/autopsy/images/documents-icon.png"))); // NOI18N
        documentsButton.setFocusable(false);
        documentsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                documentsButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout toolBarPanelLayout = new javax.swing.GroupLayout(toolBarPanel);
        toolBarPanel.setLayout(toolBarPanelLayout);
        toolBarPanelLayout.setHorizontalGroup(
            toolBarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(toolBarPanelLayout.createSequentialGroup()
                .addContainerGap(414, Short.MAX_VALUE)
                .addGroup(toolBarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(toolBarPanelLayout.createSequentialGroup()
                        .addComponent(imagesButton, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(videosButton, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(documentsButton))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, toolBarPanelLayout.createSequentialGroup()
                        .addComponent(filler1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(stepOneLabel)
                        .addGap(119, 119, 119)))
                .addContainerGap(413, Short.MAX_VALUE))
        );

        toolBarPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {documentsButton, imagesButton, videosButton});

        toolBarPanelLayout.setVerticalGroup(
            toolBarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(toolBarPanelLayout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(toolBarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(stepOneLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(filler1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(toolBarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(videosButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(imagesButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(documentsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5))
        );

        toolBarPanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {documentsButton, imagesButton, videosButton});

        add(toolBarPanel, java.awt.BorderLayout.PAGE_START);
    }// </editor-fold>//GEN-END:initComponents

    private void imagesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_imagesButtonActionPerformed
        resetTopComponent();
        imagesButton.setSelected(true);
        imagesButton.setEnabled(false);
        imagesButton.setBackground(SELECTED_COLOR);
        imagesButton.setForeground(Color.BLACK);
        videosButton.setSelected(false);
        videosButton.setEnabled(true);
        videosButton.setBackground(UNSELECTED_COLOR);
        documentsButton.setSelected(false);
        documentsButton.setEnabled(true);
        documentsButton.setBackground(UNSELECTED_COLOR);
        fileSearchPanel.setSelectedType(FileSearchData.FileType.IMAGE);
    }//GEN-LAST:event_imagesButtonActionPerformed

    private void videosButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_videosButtonActionPerformed
        resetTopComponent();
        imagesButton.setSelected(false);
        imagesButton.setEnabled(true);
        imagesButton.setBackground(UNSELECTED_COLOR);
        videosButton.setSelected(true);
        videosButton.setEnabled(false);
        videosButton.setBackground(SELECTED_COLOR);
        videosButton.setForeground(Color.BLACK);
        documentsButton.setSelected(false);
        documentsButton.setEnabled(true);
        documentsButton.setBackground(UNSELECTED_COLOR);
        fileSearchPanel.setSelectedType(FileSearchData.FileType.VIDEO);
    }//GEN-LAST:event_videosButtonActionPerformed

    private void documentsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_documentsButtonActionPerformed
        resetTopComponent();
        documentsButton.setSelected(true);
        documentsButton.setEnabled(false);
        documentsButton.setBackground(SELECTED_COLOR);
        documentsButton.setForeground(Color.BLACK);
        videosButton.setSelected(false);
        videosButton.setEnabled(true);
        videosButton.setBackground(UNSELECTED_COLOR);
        imagesButton.setSelected(false);
        imagesButton.setEnabled(true);
        imagesButton.setBackground(UNSELECTED_COLOR);
        fileSearchPanel.setSelectedType(FileSearchData.FileType.DOCUMENTS);
    }//GEN-LAST:event_documentsButtonActionPerformed

    /**
     * Update the user interface in response to a search being cancelled.
     *
     * @param searchCancelledEvent The SearchCancelledEvent received.
     */
    @Subscribe
    void handleSearchCancelledEvent(DiscoveryEventUtils.SearchCancelledEvent searchCancelledEvent) {
        SwingUtilities.invokeLater(() -> {
            if (null != fileSearchPanel.getSelectedType()) {
                switch (fileSearchPanel.getSelectedType()) {
                    case VIDEO:
                        imagesButton.setEnabled(true);
                        documentsButton.setEnabled(true);
                        break;
                    case IMAGE:
                        videosButton.setEnabled(true);
                        documentsButton.setEnabled(true);
                        break;
                    case DOCUMENTS:
                        videosButton.setEnabled(true);
                        imagesButton.setEnabled(true);
                        break;
                    default:
                        break;
                }
            }

        });
    }

    /**
     * Update the user interface in response to a search being completed.
     *
     * @param searchCompletedEvent The SearchCompletedEvent received.
     */
    @Subscribe
    void handleSearchCompletedEvent(DiscoveryEventUtils.SearchCompleteEvent searchCompletedEvent) {
        SwingUtilities.invokeLater(() -> {
            if (null != fileSearchPanel.getSelectedType()) {
                switch (fileSearchPanel.getSelectedType()) {
                    case VIDEO:
                        imagesButton.setEnabled(true);
                        documentsButton.setEnabled(true);
                        break;
                    case IMAGE:
                        videosButton.setEnabled(true);
                        documentsButton.setEnabled(true);
                        break;
                    case DOCUMENTS:
                        videosButton.setEnabled(true);
                        imagesButton.setEnabled(true);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    public List<Mode> availableModes(List<Mode> modes) {
        /*
         * This looks like the right thing to do, but online discussions seems
         * to indicate this method is effectively deprecated. A break point
         * placed here was never hit.
         */
        return modes.stream().filter(mode -> mode.getName().equals("discovery"))
                .collect(Collectors.toList());
    }

    /**
     * Update the user interface in response to a search being started.
     *
     * @param searchStartedEvent The SearchStartedEvent received.
     */
    @Subscribe
    void handleSearchStartedEvent(DiscoveryEventUtils.SearchStartedEvent searchStartedEvent) {
        SwingUtilities.invokeLater(() -> {
            imagesButton.setEnabled(false);
            videosButton.setEnabled(false);
            documentsButton.setEnabled(false);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton documentsButton;
    private javax.swing.JButton imagesButton;
    private javax.swing.JSplitPane leftSplitPane;
    private javax.swing.JSplitPane rightSplitPane;
    private javax.swing.JButton videosButton;
    // End of variables declaration//GEN-END:variables

}

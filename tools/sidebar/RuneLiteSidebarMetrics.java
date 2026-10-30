import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;
import net.runelite.client.ui.PluginPanel;
import net.runelite.client.ui.laf.RuneLiteLAF;
import net.runelite.client.util.ImageUtil;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.components.ToggleButton;
import net.runelite.client.ui.components.materialtabs.MaterialTab;
import net.runelite.client.ui.components.materialtabs.MaterialTabGroup;
import javax.swing.border.EmptyBorder;

/** Diagnostic against the pinned shell classes, never the gamepack or live account. */
public final class RuneLiteSidebarMetrics {
    public static void main(String[] args) throws Exception {
        UIManager.setLookAndFeel(new RuneLiteLAF());
        SwingUtilities.invokeAndWait(() -> {
            try {
                PluginPanel content = new PluginPanel() {};
                content.add(new JLabel("Configuration"));
                content.add(new JTextField("Search plugins"));
                content.add(new JLabel("GPU"));
                JTabbedPane tabs = new JTabbedPane(JTabbedPane.RIGHT);
                tabs.putClientProperty("FlatLaf.style", "tabInsets: 2,5,2,5; variableSize: true; deselectable: true; tabHeight: 26");
                tabs.addTab(null, new ImageIcon(ImageIO.read(RuneLiteSidebarMetrics.class.getClassLoader().getResource("net/runelite/client/plugins/config/config_icon.png"))), content.getWrappedPanel());
                tabs.setSelectedIndex(-1);
                Dimension closed = tabs.getPreferredSize();
                tabs.setSelectedIndex(0);
                Dimension opened = tabs.getPreferredSize();
                tabs.setSize(opened.width, 503);
                tabs.doLayout();
                Rectangle tab = tabs.getBoundsAt(0);
                MaterialTabGroup header = new MaterialTabGroup();
                header.setLayout(new GridLayout(1,0,7,7));
                header.setBorder(new EmptyBorder(10,10,0,10));
                MaterialTab first = null;
                for(String icon : new String[]{"config_icon_lg.png", "profile_icon.png", "plugin_hub_icon.png"}) {
                    MaterialTab item = new MaterialTab(new ImageIcon(ImageIO.read(RuneLiteSidebarMetrics.class.getClassLoader().getResource("net/runelite/client/plugins/config/"+icon))),header,null);
                    header.addTab(item); if(first==null) first=item;
                }
                header.select(first); header.setSize(242,header.getPreferredSize().height); header.doLayout();
                Rectangle headerTab = first.getBounds();
                Font font = UIManager.getFont("Label.font");
                FontMetrics fm = tabs.getFontMetrics(font);
                System.out.printf(java.util.Locale.ROOT,
                    "{\"rail\":%d,\"open\":%d,\"panel\":%d,\"innerPanel\":%d,\"scrollAllowance\":%d,\"scrollbar\":%s,\"tab\":[%d,%d,%d,%d],\"font\":\"%s\",\"fontSize\":%d,\"fontHeight\":%d,\"configurationWidth\":%d,\"header\":[%d,%d,%d,%d]}%n",
                    closed.width, opened.width, opened.width-closed.width, PluginPanel.PANEL_WIDTH, PluginPanel.SCROLLBAR_WIDTH,
                    UIManager.get("ScrollBar.width"), tab.x,tab.y,tab.width,tab.height,font.getFamily(),font.getSize(),fm.getHeight(),fm.stringWidth("Configuration"),headerTab.x,headerTab.y,headerTab.width,headerTab.height);
                if (args.length > 0) {
                    content.getWrappedPanel().setSize(242,503);
                    layout(content.getWrappedPanel());
                    BufferedImage image=new BufferedImage(opened.width,503,BufferedImage.TYPE_INT_ARGB);
                    Graphics2D g=image.createGraphics(); tabs.paint(g);g.dispose();
                    ImageIO.write(image,"png",new File(args[0]));
                }
                if (args.length > 1) {
                    File directory = new File(args[1]); directory.mkdirs();
                    BufferedImage toggle = ImageIO.read(ToggleButton.class.getResource("switcher_on.png"));
                    ImageIO.write(ImageUtil.recolorImage(toggle, ColorScheme.BRAND_ORANGE), "png", new File(directory,"switch_on.png"));
                    ImageIO.write(ImageUtil.flipImage(ImageUtil.luminanceScale(ImageUtil.grayscaleImage(toggle),.61f),true,false), "png",new File(directory,"switch_off.png"));
                    BufferedImage star=ImageIO.read(RuneLiteSidebarMetrics.class.getClassLoader().getResource("net/runelite/client/plugins/config/star_on.png"));
                    ImageIO.write(ImageUtil.recolorImage(star, ColorScheme.GREEN),"png",new File(directory,"star_on.png"));
                    ImageIO.write(ImageUtil.luminanceScale(ImageUtil.grayscaleImage(star),.77f),"png",new File(directory,"star_off.png"));
                }
            } catch(Exception e) { throw new RuntimeException(e); }
        });
    }
    private static void layout(Container component) {
        component.doLayout();
        for (Component child : component.getComponents()) if(child instanceof Container) layout((Container)child);
    }
}

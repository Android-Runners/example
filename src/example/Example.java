package example;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Example {

    // Okay, if I see this, it will mean it works

    private static void println(Object o) {
        System.out.println(o);
    }

    private static void println() {
        System.out.println();
    }

    private static String path = null;

    private static String getPath() {

        if(path == null) {
            ClassLoader loader = Example.class.getClassLoader();
            String ans = (loader.getResource("example/Example.class")).toString();

            return path = ans.substring(6, ans.length() - 14);
        }

        return path;
    }

    private static boolean flag = true;
    private static int i = 0;

    public static void main(String[] args) throws AWTException, IOException {

        Thread thread = new Thread(new Recorder());
        thread.setPriority(Thread.MAX_PRIORITY);

        thread.start();
    }

    private static class Recorder implements Runnable {

        @Override
        public void run() {
            for(int i = 0; i < 12; ++i) {
                try {
                    getScreen();
                } catch (AWTException | IOException e) {
                    e.printStackTrace();
                }
            }

            flag = false;
        }
    }

    private static Integer screenCount = null;

    private static int getScreenCount() throws IOException {

        if (screenCount == null) {
            return screenCount = (int) Files.list(Paths.get(getPath())).count();
        }

        return ++screenCount;
    }

    public static void getScreen() throws AWTException, IOException {
        Rectangle screenRectangle = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        BufferedImage capture = new Robot().createScreenCapture(screenRectangle);
        ImageIO.write(capture, "jpg", new File(getPath() + "/screen" + getScreenCount() + ".jpg"));
    }
}

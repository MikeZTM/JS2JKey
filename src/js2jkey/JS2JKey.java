/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package js2jkey;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mikelee
 */
public class JS2JKey {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/test", new MyHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
    }

    static class MyHandler implements HttpHandler {

        public void handle(HttpExchange t) throws IOException {
            try {
                Map<String, String> params;
                params = queryToMap(t.getRequestURI().getQuery());
                String response = params.get("key");
                t.sendResponseHeaders(200, response.length());
                OutputStream os = t.getResponseBody();
                os.write(response.getBytes());
                os.close();
                int vk = 0;
                switch (Integer.parseInt(response)) {
                    case 0:
                        vk = KeyEvent.VK_UP;
                        break;
                    case 1:
                        vk = KeyEvent.VK_DOWN;
                        break;
                    case 2:
                        vk = KeyEvent.VK_LEFT;
                        break;
                    case 3:
                        vk = KeyEvent.VK_RIGHT;
                        break;
                    case 4:
                        vk = KeyEvent.VK_Z;
                        break;
                    case 5:
                        vk = KeyEvent.VK_X;
                        break;
                    case 6:
                        vk = KeyEvent.VK_C;
                        break;
                    case 7:
                        vk = KeyEvent.VK_V;
                        break;
                    case 8:
                        vk = KeyEvent.VK_A;
                        break;
                    case 9:
                        vk = KeyEvent.VK_S;
                        break;
                }
                Robot r = new Robot();
                Runtime rt;
                rt = Runtime.getRuntime();
                rt.exec("cmd /c act.vbs");
//                rt.exec("open /Applications/TextEdit.app");
                
//                r.keyPress(KeyEvent.VK_ALT);
//                r.keyPress(KeyEvent.VK_TAB);
//                r.delay(10); //set the delay
//                r.keyRelease(KeyEvent.VK_ALT);
//                r.keyRelease(KeyEvent.VK_TAB);
                r.delay(500); //set the delsay
                r.keyPress(vk);
                r.delay(10); //set the delsay
                r.keyRelease(vk);
            } catch (AWTException ex) {
                Logger.getLogger(JS2JKey.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static Map<String, String> queryToMap(String query) {
        Map<String, String> result = new HashMap<String, String>();
        for (String param : query.split("&")) {
            String pair[] = param.split("=");
            if (pair.length > 1) {
                result.put(pair[0], pair[1]);
            } else {
                result.put(pair[0], "");
            }
        }
        return result;
    }
}

